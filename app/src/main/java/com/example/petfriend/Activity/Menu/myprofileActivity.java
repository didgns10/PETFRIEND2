package com.example.petfriend.Activity.Menu;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.petfriend.Activity.Login.Signup_MemberActivity;
import com.example.petfriend.Activity.MainActivity;
import com.example.petfriend.Model.MemberInfo;
import com.example.petfriend.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class myprofileActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mReferncePlace;
    private FirebaseUser user;
    private TextView textView_nick;
    private ImageView imageView_profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myprofile);
         textView_nick = (TextView)findViewById(R.id.textView_nick);
         imageView_profile = (ImageView)findViewById(R.id.imageView_profile);

        mAuth = FirebaseAuth.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
/*
        final DocumentReference docRef = FirebaseFirestore.getInstance().collection("users").document(user.getUid());
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if(document != null) {
                        if (document.exists()) {
                            if(document.getData().get("photoUrl") != null){

                                Glide.with(myprofileActivity.this).load(document.getData().get("photoUrl")).centerCrop().override(500).into(imageView_profile);
                            }
                            Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                            textView_nick.setText(document.getData().get("nickname").toString());
                        } else {
                            Log.d(TAG, "No such document");
                        }
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }

            }
        });*/

        mDatabase = FirebaseDatabase.getInstance();
        mReferncePlace = mDatabase.getReference("users");
        mDatabase.getReference().child("users").child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot != null){
                    if(dataSnapshot.exists()){
                        Log.d(TAG, "such document");
                        MemberInfo memberInfo = dataSnapshot.getValue(MemberInfo.class);
                        textView_nick.setText(memberInfo.getNickname());
                        Glide.with(myprofileActivity.this).load(memberInfo.getPhotoUrl()).centerCrop().override(500).into(imageView_profile);

                    }else{
                        Log.d(TAG, "No such document");
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        //자기소개 글 저장 하기
        TextView textView=(TextView)findViewById(R.id.textView_self);
        try {
            // 파일에서 읽은 데이터를 저장하기 위해서 만든 변수
            StringBuffer data = new StringBuffer();
            FileInputStream fis = openFileInput("self.txt");//파일명
            BufferedReader buffer = new BufferedReader
                    (new InputStreamReader(fis));
            String str = buffer.readLine(); // 파일에서 한줄을 읽어옴
            while (str != null) {
                data.append(str + "");
                str = buffer.readLine();
            }
            textView.setText(data);
            buffer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


        //뒤로가기 화면 전환
        ImageButton button_back = (ImageButton) findViewById(R.id.imageButton_back);
        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(myprofileActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        // 프로필 수정 하기 화면전환 버튼
        View view = findViewById(R.id.layout_myprofile_edit);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(myprofileActivity.this, myprofile_editActivity.class);
                startActivity(intent);
            }
        });
        // 공유하기 버튼
        ImageButton button_send = (ImageButton) findViewById(R.id.imageButton_send);
        button_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    Intent share = new Intent(Intent.ACTION_SEND);
                share.setType("image/*");

                String imagePath =  "sdcard/Android/data/com.example.petfriend/files/profileImage.jpg";;
                File imageFileToShare = new File(imagePath);
                Uri uri = Uri.fromFile(imageFileToShare);
                share.putExtra(Intent.EXTRA_STREAM, uri);

                startActivity(Intent.createChooser(share, "Share image to..."));
                }

        });


    }
}