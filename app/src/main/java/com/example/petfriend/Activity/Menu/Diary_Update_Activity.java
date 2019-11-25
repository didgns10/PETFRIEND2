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
import com.example.petfriend.Activity.Idea.Idea_PlaceUpdate_Activity;
import com.example.petfriend.Activity.Idea.Idea_place_set_Activity;
import com.example.petfriend.Model.Diary;
import com.example.petfriend.Model.DiaryDBHelper;
import com.example.petfriend.Model.Place;
import com.example.petfriend.Model.PlaceDBHelper;
import com.example.petfriend.R;

public class Diary_Update_Activity extends AppCompatActivity {

    private EditText di_title;
    private EditText di_content;
    private DatePicker dp;
    private Button bt_update_img;
    private Button bt_update;
    private String di_time;
    private String imgPath;
    private ImageView di_photo;

    private DiaryDBHelper dbHelper;
    private long receivedDiaryId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary_update);

        dp = (DatePicker)findViewById(R.id.datePicker_update);
        di_title = (EditText)findViewById(R.id.et_update_title);
        di_content = (EditText)findViewById(R.id.et_update_content);
        bt_update_img = (Button)findViewById(R.id.bt_update_img);
        bt_update = (Button)findViewById(R.id.bt_update);
        di_photo = (ImageView)findViewById(R.id.diary_update_img);

        dp.init(dp.getYear(), dp.getMonth(), dp.getDayOfMonth(), new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                di_time=String.format(dp.getYear()+"년 "+(dp.getMonth()+1)+"월 "+dp.getDayOfMonth()+"일");
            }
        });
        if(di_time==null){
            di_time=String.format(dp.getYear()+"년 "+(dp.getMonth()+1)+"월 "+dp.getDayOfMonth()+"일");
        }
        dbHelper = new DiaryDBHelper(this);
        try {
            //get intent to get person id
            receivedDiaryId = getIntent().getLongExtra("USER_ID", 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        /***populate user data before update***/
        Diary queriedDiary = dbHelper.getDiary(receivedDiaryId);
        //set field to this user data
        di_title.setText(queriedDiary.getTitle());
        di_time= (queriedDiary.getTime());
        imgPath=(queriedDiary.getImage());
        Glide.with(this).load(imgPath).centerCrop().override(300).into(di_photo);
        di_content.setText(queriedDiary.getContent());

        //listen to add button click
        bt_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //call the save person method
                updateDiary();
            }
        });

        bt_update_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(Diary_Update_Activity.this,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {

                    ActivityCompat.requestPermissions(Diary_Update_Activity.this,
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);

                    if (ActivityCompat.shouldShowRequestPermissionRationale(Diary_Update_Activity.this,
                            Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    } else {
                        startToast("권한을 허용해 주세요.");
                    }
                } else {
                    Intent intent = new Intent(Diary_Update_Activity.this, GalleryActivity.class);
                    startActivityForResult(intent,0);
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

    private void updateDiary(){
        String title = di_title.getText().toString().trim();
        String time = di_time.trim();
        String photo = imgPath.trim();
        String content = di_content.getText().toString().trim();

        if(title.isEmpty() && content.isEmpty()){
            //error name is empty
            Toast.makeText(this, "빈칸 없이 채워주세요.", Toast.LENGTH_SHORT).show();
        }else{
            //create new person
            Diary updateddiary = new Diary(title, time, photo, content);
            dbHelper.updateDiaryRecord(receivedDiaryId, this, updateddiary);

            //finally redirect back home
            // NOTE you can implement an sqlite callback then redirect on success delete
            goBackHome();
        }
    }

    private void goBackHome(){
        startActivity(new Intent(Diary_Update_Activity.this, Diary_Activity.class));
    }
    private void startToast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
