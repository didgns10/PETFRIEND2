package com.example.petfriend.Activity.Post;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
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
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.example.petfriend.Activity.Camera.CameraActivity;
import com.example.petfriend.Activity.Camera.GalleryActivity;
import com.example.petfriend.Model.MemberInfo;
import com.example.petfriend.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class ProfileEditActivity extends AppCompatActivity {


    FirebaseUser firebaseUser;
    private FirebaseUser user;
    Context context;

    ImageView imageView_profile;
    EditText editText_signup_member_nick;
    EditText editText_signup_member_phone;
    EditText editText_signup_member_address;
    private RelativeLayout loaderlayout;

    public RequestManager mGlideRequestManager;

    private String profilePath;
    private DatabaseReference mDatabase;
    private static final String TAG = "memberinfo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit);


        loaderlayout = findViewById(R.id.loaderlayout);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        imageView_profile=findViewById(R.id.imageView_profile);
        editText_signup_member_nick=findViewById(R.id.editText_signup_member_nick);
        editText_signup_member_phone=findViewById(R.id.editText_signup_member_phone);
        editText_signup_member_address=findViewById(R.id.editText_signup_member_address);
        mGlideRequestManager = Glide.with(ProfileEditActivity.this);
        userInfo();

        ImageButton button_back = (ImageButton) findViewById(R.id.imageButton_back);
        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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

        imageView_profile.setOnClickListener(new View.OnClickListener() {
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
                Intent intent = new Intent(ProfileEditActivity.this, CameraActivity.class);
                startActivityForResult(intent,1);
            }
        });

        bt_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ContextCompat.checkSelfPermission(ProfileEditActivity.this,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {

                    ActivityCompat.requestPermissions(ProfileEditActivity.this,
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);

                    if (ActivityCompat.shouldShowRequestPermissionRationale(ProfileEditActivity.this,
                            Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    } else {
                        startToast("권한을 허용해 주세요.");
                    }
                } else {
                    myStartActivity(GalleryActivity.class,"image",0);
                }
            }
        });

    }

    private void userInfo(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users").child(firebaseUser.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                MemberInfo user = dataSnapshot.getValue(MemberInfo.class);

                mGlideRequestManager.load(user.getPhotoUrl()).into(imageView_profile);
                editText_signup_member_nick.setText(user.getNickname());
                editText_signup_member_phone.setText(user.getPhone_num());
                editText_signup_member_address.setText(user.getAddress());
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 0: {
                if (resultCode == Activity.RESULT_OK) {
                    profilePath = data.getStringExtra("profilePath");
                    Glide.with(this).load(profilePath).centerCrop().override(500).into(imageView_profile);
                    //  Bitmap bmp = BitmapFactory.decodeFile(profilePath);
                    //   profile_image.setImageBitmap(bmp);
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
                    imageView_profile.setImageBitmap(bmp1);
                }
                break;
            }
        }
    }
    private void memberinfo() {
        final String phone_num = ((EditText)findViewById(R.id.editText_signup_member_phone)).getText().toString();
        final String nickname = ((EditText)findViewById(R.id.editText_signup_member_nick)).getText().toString();
        final String address = ((EditText)findViewById(R.id.editText_signup_member_address)).getText().toString();



        if(address.length() > 0 && nickname.length() >0 && phone_num.length() >0){
            loaderlayout.setVisibility(View.VISIBLE);
            FirebaseStorage storage = FirebaseStorage.getInstance();
            // Create a storage reference from our app
            StorageReference storageRef = storage.getReference();

            user = FirebaseAuth.getInstance().getCurrentUser();
            final StorageReference mountainImagesRef = storageRef.child("images/ "+user.getUid()+" /profile.jpg");

            if(profilePath == null){
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users").child(firebaseUser.getUid());
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        MemberInfo user = dataSnapshot.getValue(MemberInfo.class);

                        MemberInfo memberInfo = new MemberInfo(nickname, phone_num, address, user.getPhotoUrl() ,firebaseUser.getUid());
                        MerberUploader(memberInfo);
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
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

                                MemberInfo memberInfo = new MemberInfo(nickname, phone_num, address,downloadUri.toString(),user.getUid());
                                MerberUploader(memberInfo);

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
    private void MerberUploader(MemberInfo memberInfo){
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("users").child(user.getUid()).setValue(memberInfo)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        loaderlayout.setVisibility(View.GONE);
                        startToast("회원정보 수정을 완료하였습니다.");
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        loaderlayout.setVisibility(View.GONE);
                        Log.w(TAG, "Error writing document", e);
                        startToast("회원정보 수정을 실패하였습니다.");
                    }
                });
    }
    private void myStartActivity(Class c, String media, int requestCode){
        Intent intent = new Intent(this,c);
        intent.putExtra("media", media);
        startActivityForResult(intent,requestCode);
    }
    private void startToast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
