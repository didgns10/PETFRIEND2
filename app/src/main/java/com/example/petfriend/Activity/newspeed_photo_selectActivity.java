package com.example.petfriend.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.petfriend.R;

public class newspeed_photo_selectActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newspeed_photo_select);

        //뒤로가기 화면 전환
        ImageButton button_back = (ImageButton) findViewById(R.id.imageButton_back);
        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(newspeed_photo_selectActivity.this, newspeed_photoActivity.class);
                startActivity(intent);
            }
        });

    }
}
