package com.example.petfriend.Activity.Menu;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.petfriend.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class myprofile_editActivity extends AppCompatActivity {

    private static int PICK_IMAGE_REQUEST = 1;
    private static int PICK_IMAGE_REQUEST1 = 2;
    ImageView imgView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myprofile_edit);


        //버튼에대한 선언들
        Button button_nick = (Button)findViewById(R.id.button_nick);
        Button button_self = (Button)findViewById(R.id.button_self);
        Button button_profile = (Button)findViewById(R.id.button_profile);

        final EditText editText_self=(EditText)findViewById(R.id.editText_self);
        final EditText editText_nick=(EditText)findViewById(R.id.editText_nick);

        try { // 현재 작성중인 닉네임 글을 불러와주는 역할
            // 파일에서 읽은 데이터를 저장하기 위해서 만든 변수
            StringBuffer data1 = new StringBuffer();
            FileInputStream fis = openFileInput("nick_pause.txt");//파일명
            BufferedReader buffer = new BufferedReader
                    (new InputStreamReader(fis));
            String str = buffer.readLine(); // 파일에서 한줄을 읽어옴
            while (str != null) {
                data1.append(str + "");
                str = buffer.readLine();
            }
            editText_nick.setText(data1);
            buffer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try { // 현재 작성중인 자기소개 글을 불러와주는 역할
            // 파일에서 읽은 데이터를 저장하기 위해서 만든 변수
            StringBuffer data2 = new StringBuffer();
            FileInputStream fis = openFileInput("self_pause.txt");//파일명
            BufferedReader buffer = new BufferedReader
                    (new InputStreamReader(fis));
            String str2 = buffer.readLine(); // 파일에서 한줄을 읽어옴
            while (str2 != null) {
                data2.append(str2 + "");
                str2 = buffer.readLine();
            }
            editText_self.setText(data2);
            buffer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 사진에 저장 된 이미지 불러오기
        try{
            ImageView imgview = (ImageView)findViewById(R.id.imageView_profile);
            String imgpath = "data/data/com.example.petfriend/files/profile_back.png";
            Bitmap bm = BitmapFactory.decodeFile(imgpath);
            Bitmap resize_bitmap = Bitmap.createScaledBitmap(bm, 405, 267, true);
            imgview.setImageBitmap(resize_bitmap);

        }catch(Exception e){
            e.printStackTrace();
        }

        //뒤로가기 버튼 처리
        ImageButton button_back = (ImageButton) findViewById(R.id.imageButton_back); //뒤로가기 버튼
        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // 프로필 사진 이미지 설정
        button_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent 생성
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT); //ACTION_PIC과 차이점?
                intent.setType("image/*"); //이미지만 보이게
                //Intent 시작 - 갤러리앱을 열어서 원하는 이미지를 선택할 수 있다.
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
            }
        });

        //닉네임 텍스트 수정 버튼을 누르면 텍스트에 입력되고 있는 문자를 파일에 저장을 해주는 역할이다.
        button_nick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String data1 = editText_nick.getText().toString();

                try { // 작성한 텍스트를 파일에 저장해주는 역할
                    FileOutputStream fos = openFileOutput
                            ("nick.txt", // 파일명 지정
                                    Context.MODE_PRIVATE);// 저장모드
                    PrintWriter out = new PrintWriter(fos);
                    out.println(data1);
                    editText_nick.setText(data1);
                    out.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        //자기소개 텍스트 수정 버튼을 누르면 텍스트에 입력되고 있는 문자를 파일에 저장을 해주는 역할이다.
        button_self.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String data = editText_self.getText().toString();

                try { // 작성한 텍스트를 파일에 저장해주는 역할
                    FileOutputStream fos = openFileOutput
                            ("self.txt", // 파일명 지정
                                    Context.MODE_PRIVATE);// 저장모드
                    PrintWriter out = new PrintWriter(fos);
                    out.println(data);
                    editText_self.setText(data);
                    out.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });



    }


    //프로필 배경 사진 선택작업을 후의 결과 처리
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            //이미지를 하나 골랐을때
            if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && null != data) {
                //data에서 절대경로로 이미지를 가져옴
                Uri uri = data.getData();

                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
                //이미지가 한계이상(?) 크면 불러 오지 못하므로 사이즈를 줄여 준다.
                int nh = (int) (bitmap.getHeight() * (1024.0 / bitmap.getWidth()));
                Bitmap scaled = Bitmap.createScaledBitmap(bitmap, 1024, nh, true);
                Bitmap resize_bitmap = Bitmap.createScaledBitmap(scaled, 405, 267, true);

                //  불러온 이미지 저장
                try{

                    ImageView imgview = (ImageView)findViewById(R.id.imageView_profile);
                    imgview.setImageBitmap(resize_bitmap);

                    File file = new File("profile_back.png");
                    FileOutputStream fos = openFileOutput("profile_back.png" , 0);
                    scaled.compress(Bitmap.CompressFormat.PNG, 100 , fos);
                    fos.flush();
                    fos.close();


                }catch(Exception e) {
                    e.printStackTrace();
                }

            }
            else {
                Toast.makeText(this, "취소 되었습니다.", Toast.LENGTH_LONG).show();
            }

        } catch (Exception e) {
            Toast.makeText(this, "Oops! 로딩에 오류가 있습니다.", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }

    }
    protected void onPause(){
        super.onPause();
        // 자기소개 수정 도중 나갔을때 저장 시켜주는 역할을 한다.
        final EditText et1 = (EditText) findViewById(R.id.editText_self);
        String data2 = et1.getText().toString();

        try { // 글 작성중에 나갔을때 저장해주는 역할
            FileOutputStream fos = openFileOutput
                    ("self_pause.txt", // 파일명 지정
                            Context.MODE_PRIVATE);// 저장모드
            PrintWriter out = new PrintWriter(fos);
            out.println(data2);
            out.close();
            et1.setText(data2);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //닉네임 수정 도중 나갔을때 저장 시켜주는 역할
        final EditText et = (EditText) findViewById(R.id.editText_nick);
        String data1 = et.getText().toString();

        try { // 글 작성중에 나갔을때 저장해주는 역할
            FileOutputStream fos = openFileOutput
                    ("nick_pause.txt", // 파일명 지정
                            Context.MODE_PRIVATE);// 저장모드
            PrintWriter out = new PrintWriter(fos);
            out.println(data1);
            out.close();
            et.setText(data1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}