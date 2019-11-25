package com.example.petfriend.Activity.Newspeed;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.petfriend.Activity.Camera.GalleryActivity;
import com.example.petfriend.Activity.Login.IdpwActivity;
import com.example.petfriend.Activity.Login.LoginActivity;
import com.example.petfriend.Activity.Login.Signup_MemberActivity;
import com.example.petfriend.Activity.MainActivity;
import com.example.petfriend.Model.MemberInfo;
import com.example.petfriend.Model.Upload_newspeed_info;
import com.example.petfriend.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;

public class AddnewspeedActivity extends AppCompatActivity {
    private static final String TAG = "upload_newspeed";
    private FirebaseUser user;
    private ArrayList<String> pathList = new ArrayList<>();
    private LinearLayout parent;
    private int pathCount ;
    private int successCount ;
    private RelativeLayout bt_background_layout;
    private ImageView selectedImageview;
    private EditText selectedEditText;
    private RelativeLayout loaderlayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newspeed_uplode);

        loaderlayout = findViewById(R.id.loaderlayout);
        loaderlayout.setVisibility(View.GONE);

        parent = findViewById(R.id.contnet_layout);
        bt_background_layout = findViewById(R.id.bt_background_layout);

        bt_background_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(bt_background_layout.getVisibility() == View.VISIBLE){
                    bt_background_layout.setVisibility(View.GONE);
                }

            }
        });
        EditText editText_newspeed_upload_content = findViewById(R.id.editText_newspeed_upload_content);
        editText_newspeed_upload_content.setOnFocusChangeListener(onFocusChangeListener);
        EditText editText_newspeed_upload_title = findViewById(R.id.editText_newspeed_upload_title);
        editText_newspeed_upload_title.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {
                    selectedEditText = null;
                }
            }
        });

        ImageButton button_back = (ImageButton) findViewById(R.id.imageButton_back);
        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddnewspeedActivity.this, newspeedActivity.class);
                startActivity(intent);
                finish();
            }
        });

        Button bt_ok = (Button)findViewById(R.id.button_upload);
        bt_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                storageUpload();
            }
        });

       //이미지 버튼
        FloatingActionButton ft_bt_image = (FloatingActionButton) findViewById(R.id.ft_bt_image);
        ft_bt_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myStartActivity(GalleryActivity.class,"image",0);
            }
        });
        // 동영상 버튼
        FloatingActionButton ft_bt_video = (FloatingActionButton) findViewById(R.id.ft_bt_video);
        ft_bt_video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myStartActivity(GalleryActivity.class,"video",0);
            }
        });

        Button bt_image_modify = findViewById(R.id.bt_image_modify);
        bt_image_modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myStartActivity(GalleryActivity.class,"image",1);
                bt_background_layout.setVisibility(View.GONE);
            }
        });
        Button bt_video_modify = findViewById(R.id.bt_video_modify);
        bt_video_modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myStartActivity(GalleryActivity.class,"video",1);
                bt_background_layout.setVisibility(View.GONE);
            }
        });
        Button bt_delete = findViewById(R.id.bt_delete);
        bt_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parent.removeView((View)selectedImageview.getParent());
                bt_background_layout.setVisibility(View.GONE);
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 0: {
                if (resultCode == Activity.RESULT_OK) {
                    String profilePath = data.getStringExtra("profilePath");
                    pathList.add(profilePath);
                    ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                    LinearLayout linearLayout = new LinearLayout(AddnewspeedActivity.this);
                    linearLayout.setLayoutParams(layoutParams);
                    linearLayout.setOrientation(LinearLayout.VERTICAL);

                    if(selectedEditText == null){
                        parent.addView(linearLayout);
                    }else {
                        for (int i = 0; i < parent.getChildCount(); i++) {
                            if (parent.getChildAt(i) == selectedEditText.getParent()) {
                                parent.addView(linearLayout, i + 1);
                                break;
                            }
                        }
                    }


                    ImageView imageView = new ImageView(AddnewspeedActivity.this);
                    imageView.setLayoutParams(layoutParams);
                    imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            bt_background_layout.setVisibility(View.VISIBLE);
                            selectedImageview = (ImageView) v;
                        }
                    });
                    //레이아웃에 이미지 넣는 작업
                    linearLayout.addView(imageView);
                    Glide.with(this).load(profilePath).override(700).into(imageView);

                    EditText editText = new EditText(AddnewspeedActivity.this);
                    editText.setLayoutParams(layoutParams);
                    editText.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE | InputType.TYPE_CLASS_TEXT);
                    editText.setHint("내용");
                    editText.setOnFocusChangeListener(onFocusChangeListener);
                    editText.setHintTextColor(Color.parseColor("#000000"));
                    editText.setTextColor(Color.parseColor("#000000"));
                    linearLayout.addView(editText);

                }
                break;
            }
            case 1:
                if (resultCode == Activity.RESULT_OK) {
                    String profilePath = data.getStringExtra("profilePath");
                    Glide.with(this).load(profilePath).override(700).into(selectedImageview);
                }
                break;
        }
    }

    View.OnFocusChangeListener onFocusChangeListener = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if(hasFocus){
                selectedEditText= (EditText) v;
            }
        }
    };
    //이미지나 텍스트를 파이어베이스에 저장해주는 함수이다.
    private void storageUpload() {
        final String title = ((EditText) findViewById(R.id.editText_newspeed_upload_title)).getText().toString();

        if (title.length() > 0 ) {
            loaderlayout.setVisibility(View.VISIBLE);
            final ArrayList<String> contentsList = new ArrayList<>();
            user = FirebaseAuth.getInstance().getCurrentUser();
            FirebaseStorage storage = FirebaseStorage.getInstance();
            StorageReference storageRef = storage.getReference();
            FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
            final DocumentReference documentReference = firebaseFirestore.collection("newspeed").document();

            for(int i=0; i<parent.getChildCount(); i++){
                LinearLayout linearLayout =(LinearLayout) parent.getChildAt(i);
                for(int ii = 0; ii<linearLayout.getChildCount(); ii++){
                    View view = linearLayout.getChildAt(ii);
                    if(view instanceof EditText){
                        String text = ((EditText)view).getText().toString();
                        if(text.length() > 0) {
                            contentsList.add(text);
                        }
                    }else{
                        contentsList.add(pathList.get(pathCount));
                        final StorageReference mountainImagesRef = storageRef.child("newspeed/ "+documentReference.getId()+" /"+pathCount+".jpg");
                        try{
                            InputStream stream = new FileInputStream(new File(pathList.get(pathCount)));
                            StorageMetadata metadata = new StorageMetadata.Builder().setCustomMetadata("index",""+(contentsList.size()-1)).build();
                            UploadTask uploadTask = mountainImagesRef.putStream(stream,metadata);
                            uploadTask.addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception exception) {
                                    // Handle unsuccessful uploads
                                }
                            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    final int index =Integer.parseInt(taskSnapshot.getMetadata().getCustomMetadata("index"));
                                    mountainImagesRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            contentsList.set(index, uri.toString());
                                            successCount++;
                                            if(pathList.size() == successCount){
                                                //완료
                                                Upload_newspeed_info newspeed_info = new Upload_newspeed_info(title,contentsList,user.getUid(),new Date());
                                                newspeedUpload(documentReference,newspeed_info);
                                            }
                                        }
                                    });
                                }
                            });
                        }catch (FileNotFoundException e){
                            Log.e("로그","에러:"+e.toString());
                        }
                        pathCount++;
                    }

                }
            }
            if(pathList.size() ==0){
                Upload_newspeed_info newspeed_info = new Upload_newspeed_info(title,contentsList,user.getUid(),new Date());
                newspeedUpload(documentReference,newspeed_info);
            }
        } else {
            startToast("제목을 입력해주세요.");
        }
    }
    private void newspeedUpload(DocumentReference documentReference,Upload_newspeed_info newspeed_info){
        documentReference.set(newspeed_info)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        loaderlayout.setVisibility(View.GONE);
                        startToast("게시물 등록이 완료되었습니다.");
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        loaderlayout.setVisibility(View.GONE);
                        startToast("게시물 등록이 실패하였습니다.");
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
