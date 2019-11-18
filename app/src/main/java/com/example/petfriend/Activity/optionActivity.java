package com.example.petfriend.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.petfriend.R;
import com.google.firebase.auth.FirebaseAuth;

public class optionActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option);

        ImageButton button_back = (ImageButton) findViewById(R.id.imageButton_back);

        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(optionActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        
    }
    public void layout_logout(View view){
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(optionActivity.this, LoginActivity.class);
        startActivity(intent);
        Toast.makeText(this, "로그아웃 되었습니다.", Toast.LENGTH_SHORT).show();
        finish();
    }

    public void layout_email(View view) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("plain/text");
        intent.putExtra(Intent.EXTRA_EMAIL, new String[] { "didgns10@naver.com" });
        intent.putExtra(Intent.EXTRA_SUBJECT, "제목을 적으세요.");
        intent.putExtra(Intent.EXTRA_TEXT, "내용을 적으세요.");
        startActivity(Intent.createChooser(intent, ""));
    }
}
