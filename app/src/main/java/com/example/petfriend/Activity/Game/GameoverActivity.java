package com.example.petfriend.Activity.Game;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.petfriend.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class GameoverActivity extends AppCompatActivity {

    private TextView score;
    private TextView highscore;
    private Button back_re;
    private Button back_home;
    private int highscore_save;

    private int getScore;

    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gameover);


        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        score = findViewById(R.id.score);
        highscore = findViewById(R.id.highscore);
        back_re = findViewById(R.id.restart_bt);
        back_home = findViewById(R.id.back_home_bt);


        getScore = getIntent().getIntExtra("score", 0);
        score.setText(getScore + "");

        SharedPreferences settings = getSharedPreferences("GAME_DATA", Context.MODE_PRIVATE);
        highscore_save = settings.getInt("HIGH_SCORE" +firebaseUser.getUid(),0);

        if (getScore > highscore_save) {
            highscore.setText(getScore + "");

            SharedPreferences.Editor editor = settings.edit();
            editor.putInt("HIGH_SCORE"+firebaseUser.getUid(), getScore);
            editor.commit();
            addScore();
        } else {
            highscore.setText(highscore_save + "");
        }

        back_re.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GameoverActivity.this, GameActivity.class);
                intent.putExtra("restart", "restart");
                startActivity(intent);
                finish();
            }
        });
        back_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GameoverActivity.this, GameActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    private void addScore() {

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("score").child(firebaseUser.getUid());

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("score", getScore);
        hashMap.put("publisher",firebaseUser.getUid());

        reference.setValue(hashMap);
    }

}
