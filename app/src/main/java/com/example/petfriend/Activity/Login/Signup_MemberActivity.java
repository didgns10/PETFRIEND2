package com.example.petfriend.Activity.Login;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.petfriend.Activity.Camera.CameraActivity;
import com.example.petfriend.Activity.Camera.GalleryActivity;
import com.example.petfriend.Activity.MainActivity;
import com.example.petfriend.Model.MemberInfo;
import com.example.petfriend.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class Signup_MemberActivity extends AppCompatActivity {
    private static final String TAG = "memberinfo";
    private FirebaseAuth mAuth;
    private ImageView profile_image;
    private String profilePath;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_member);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // 돌아가기 화면
        ImageButton button_back = (ImageButton) findViewById(R.id.imageButton_back);

        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Signup_MemberActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        Button bt_signup = (Button) findViewById(R.id.button_info);
        bt_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                memberinfo();
            }
        });

        profile_image = (ImageView)findViewById(R.id.imageView_profile);
        profile_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CardView cardView = (CardView)findViewById(R.id.cardView_button);
                if(cardView.getVisibility() == View.VISIBLE){
                    cardView.setVisibility(View.GONE);
                }else{
                    cardView.setVisibility(View.VISIBLE);
                }
            }
        });

        Button bt_picture = (Button)findViewById(R.id.bt_sign_picture);
        Button bt_gallery = (Button)findViewById(R.id.bt_sign_gallery);

        bt_picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Signup_MemberActivity.this, CameraActivity.class);
                startActivityForResult(intent,1);
            }
        });

        bt_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ContextCompat.checkSelfPermission(Signup_MemberActivity.this,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {

                    ActivityCompat.requestPermissions(Signup_MemberActivity.this,
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);

                    if (ActivityCompat.shouldShowRequestPermissionRationale(Signup_MemberActivity.this,
                            Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    } else {
                        startToast("권한을 허용해 주세요.");
                    }
                } else {
                    Intent intent = new Intent(Signup_MemberActivity.this, GalleryActivity.class);
                    startActivityForResult(intent,0);
                }
            }
        });
    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 0: {
                if (resultCode == Activity.RESULT_OK) {
                    profilePath = data.getStringExtra("profilePath");
                    Bitmap bmp = BitmapFactory.decodeFile(profilePath);
                    profile_image.setImageBitmap(bmp);
                }
            }
                break;

            case 1: {
                if (resultCode == Activity.RESULT_OK) {
                    profilePath = data.getStringExtra("profilePath");
                    Bitmap bmp = BitmapFactory.decodeFile(profilePath);
                    //카메라 회전 시켜주는 작업
                    Matrix matrix = new Matrix();
                    matrix.setRotate(90);
                    Bitmap bmp1 = Bitmap.createBitmap(bmp,0,0,bmp.getWidth(),bmp.getHeight(),matrix,true);
                    profile_image.setImageBitmap(bmp1);
                }
                break;
            }
        }
    }


    private void memberinfo() {
        final String phone_num = ((EditText)findViewById(R.id.editText_signup_member_phone)).getText().toString();
        final String nickname = ((EditText)findViewById(R.id.editText_signup_member_nick)).getText().toString();
        final String address = ((EditText)findViewById(R.id.editText_signup_member_address)).getText().toString();


        if(address.length() > 0 && nickname.length() >0 && phone_num.length() >9){
            FirebaseStorage storage = FirebaseStorage.getInstance();
            // Create a storage reference from our app
            StorageReference storageRef = storage.getReference();

            user = FirebaseAuth.getInstance().getCurrentUser();
            final StorageReference mountainImagesRef = storageRef.child("images/ "+user.getUid()+" /profile.jpg");

            if(profilePath == null){
                MemberInfo memberInfo = new MemberInfo(nickname, phone_num, address);
                uploader(memberInfo);
            }else{
                try{
                    InputStream stream = new FileInputStream(new File(profilePath));

                    UploadTask uploadTask = mountainImagesRef.putStream(stream);


                    uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                        @Override
                        public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                            if (!task.isSuccessful()) {
                                throw task.getException();
                            }
                            return mountainImagesRef.getDownloadUrl();
                        }
                    }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            if (task.isSuccessful()) {
                                Uri downloadUri = task.getResult();

                                MemberInfo memberInfo = new MemberInfo(nickname, phone_num, address,downloadUri.toString());
                                uploader(memberInfo);

                            } else {
                                startToast("회원정보를 보내는데 실패하였습니다.");
                                Log.e("로그","실패");
                            }
                        }
                    });
                }catch (FileNotFoundException e){
                    Log.e("로그","에러:"+e.toString());
                }
            }

        }else{
            startToast("빈칸 없이 채워주세요.");
        }

    }

    private void uploader(MemberInfo memberInfo){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("users").document(user.getUid()).set(memberInfo)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully written!");
                        startToast("회원정보 등록을 완료하였습니다.");
                        Intent intent = new Intent(Signup_MemberActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error writing document", e);
                        startToast("회원정보 등록을 실패하였습니다.");
                    }
                });
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                } else {
                    startToast("권한을 허용해 주세요.");
                }
                return;
            }
        }
    }

    private void startToast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

}