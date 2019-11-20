package com.example.petfriend.Activity.Login;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.example.petfriend.Activity.MainActivity;
import com.example.petfriend.R;


public class LodingActivity extends AppCompatActivity {
    Handler handler = new Handler();
    Runnable r = new Runnable() {
        @Override
                public void run(){
                        Intent intent = new Intent(LodingActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loding);

        ProgressBar progressBar;
        int c = getResources().getColor(R.color.colorPupple);

        progressBar = findViewById(R.id.progressBar);
        progressBar.setIndeterminate(true);
        progressBar.getIndeterminateDrawable().setColorFilter(c, PorterDuff.Mode.MULTIPLY);

    }
    @Override
    protected  void onResume(){
        super.onResume();
        handler.postDelayed(r,4000);
    }
    @Override
    protected  void onPause(){
        super.onPause();
        handler.removeCallbacks(r);
    }
}
