package com.example.petfriend.Activity.Menu;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.petfriend.Activity.MainActivity;
import com.example.petfriend.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class myprofileActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myprofile);

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
        // 닉네임 저장하기
        TextView textView1=(TextView)findViewById(R.id.textView_nick);
        try {
            // 파일에서 읽은 데이터를 저장하기 위해서 만든 변수
            StringBuffer data1 = new StringBuffer();
            FileInputStream fis = openFileInput("nick.txt");//파일명
            BufferedReader buffer = new BufferedReader
                    (new InputStreamReader(fis));
            String str1 = buffer.readLine(); // 파일에서 한줄을 읽어옴
            while (str1 != null) {
                data1.append(str1 + "");
                str1 = buffer.readLine();
            }
            textView1.setText(data1);
            buffer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 프로필 배경 사진 저장하기
        try{
            ImageView imgview = (ImageView)findViewById(R.id.imageView_profile_back);
            String imgpath = "data/data/com.example.petfriend/files/profile_back.png";
            Bitmap bm = BitmapFactory.decodeFile(imgpath);
            Bitmap resize_bitmap = Bitmap.createScaledBitmap(bm, 405, 227, true);
            imgview.setImageBitmap(resize_bitmap);

        }catch(Exception e){
            e.printStackTrace();
        }
        // 프로필 사진 저장하기
        try{
            ImageView imgview = (ImageView)findViewById(R.id.imageView_profile);
            String imgpath = "data/data/com.example.petfriend/files/profile.png";
            Bitmap bm = BitmapFactory.decodeFile(imgpath);
            Bitmap resize_bitmap = Bitmap.createScaledBitmap(bm, 98, 94, true);
            imgview.setImageBitmap(resize_bitmap);

        }catch(Exception e){
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
                /*
                Intent intent = new Intent(Intent.ACTION_SEND);
                String imagePath = "data/data/com.example.petfriend/files/profile_back.png";
                File imageFileToShare = new File(imagePath);
                Uri uri = Uri.fromFile(imageFileToShare);
                intent.putExtra(Intent.EXTRA_STREAM, uri);*/

                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);
                intent.setType("image/*");

                Uri uri = Uri.fromFile(new File("data/data/com.example.petfriend/files/profile_back.png"));
                intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "");
                intent.putExtra(android.content.Intent.EXTRA_TEXT, "");
                intent.putExtra(Intent.EXTRA_STREAM, uri);
                startActivity(Intent.createChooser(intent, "Share Screenshot"));


            }
        });


    }
}