package com.example.petfriend.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.petfriend.R;

public class newspeedActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newspeed);

        //뒤로가기 화면 전환
        ImageButton button_home = (ImageButton) findViewById(R.id.imageButton_home);
        button_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(newspeedActivity.this, MainActivity.class);
                intent.addFlags (Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);

            }
        });
    }
}
