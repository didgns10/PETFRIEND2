package com.example.petfriend.Activity.Menu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.petfriend.Activity.Camera.GalleryActivity;
import com.example.petfriend.Model.Diary;
import com.example.petfriend.Model.DiaryDBHelper;
import com.example.petfriend.R;

public class Diary_Add_Activity extends AppCompatActivity {

    private EditText di_title;
    private EditText di_content;
    private DatePicker dp;
    private Button bt_img;
    private Button bt_add;
    public String di_time;
    private String imgPath;
    private ImageView di_photo;

    private DiaryDBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary_add);

        dp = (DatePicker)findViewById(R.id.datePicker);
        di_title = (EditText)findViewById(R.id.et_add_title);
        di_content = (EditText)findViewById(R.id.et_add_content);
        bt_img = (Button)findViewById(R.id.bt_img);
        bt_add = (Button)findViewById(R.id.bt_add);
        di_photo = (ImageView)findViewById(R.id.diary_img);

        dp.init(dp.getYear(), dp.getMonth(), dp.getDayOfMonth()+1, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                if(dp.getMonth()+1 < 10){
                    if(dp.getDayOfMonth()<10) {
                        di_time = String.format(dp.getYear() + "년 " + "0" + (dp.getMonth() + 1) + "월 " + "0" + dp.getDayOfMonth() + "일");
                    }else{
                        di_time = String.format(dp.getYear() + "년 " + "0" + (dp.getMonth() + 1) + "월 "+ dp.getDayOfMonth() + "일");
                    }
                }else {
                    if(dp.getDayOfMonth()<10) {
                        di_time = String.format(dp.getYear() + "년 " + (dp.getMonth() + 1) + "월 "+ "0" + dp.getDayOfMonth() + "일");
                    }else{
                        di_time = String.format(dp.getYear() + "년 " + (dp.getMonth() + 1) + "월 " + dp.getDayOfMonth() + "일");
                    }
                }
            }
        });
        if(di_time==null){
            di_time=String.format(dp.getYear()+"년 "+(dp.getMonth()+1)+"월 "+dp.getDayOfMonth()+"일");
        }
        bt_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(imgPath==null){
                    startToast("이미지를 선택해주세요.");
                }else {
                    saveDiary();
                }
            }
        });

        bt_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(Diary_Add_Activity.this,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {

                    ActivityCompat.requestPermissions(Diary_Add_Activity.this,
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);

                    if (ActivityCompat.shouldShowRequestPermissionRationale(Diary_Add_Activity.this,
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 0: {
                if (resultCode == Activity.RESULT_OK) {
                    imgPath = data.getStringExtra("profilePath");
                    Glide.with(this).load(imgPath).centerCrop().override(300).into(di_photo);
                }
            }
            break;
        }
    }
    private void saveDiary(){
        String title = di_title.getText().toString().trim();
        String time = di_time.trim();
        String photo = imgPath.trim();
        String content = di_content.getText().toString().trim();
        dbHelper = new DiaryDBHelper(this);

        if(title.length() > 0 && content.length() > 0){
            //create new person
            Diary diary = new Diary(title, time, photo, content);
            dbHelper.saveNewDiary(diary);

            goBackHome();
        }else if(title.length() > 20){
            Toast.makeText(this, "제목은 20자 이내로 입력하세요.", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(this, "빈칸 없이 채워주세요.", Toast.LENGTH_SHORT).show();
        }
    }

    private void myStartActivity(Class c, String media, int requestCode){
        Intent intent = new Intent(this,c);
        intent.putExtra("media", media);
        startActivityForResult(intent,requestCode);
    }

    private void goBackHome(){
        startActivity(new Intent(Diary_Add_Activity.this, Diary_Activity.class));
        finish();
    }

    private void startToast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
