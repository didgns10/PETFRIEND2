package com.example.petfriend.Activity.Game;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.petfriend.Adapter.GameAdapter;
import com.example.petfriend.Model.GameScore;
import com.example.petfriend.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class GameActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private GameAdapter gameAdapter;
    private List<GameScore> gameScoreList;
    private GameView gameView;
    private Handler handler = new Handler();
    private final static long TIMET_INTERVAL = 30;

    FirebaseUser firebaseUser;
    public static Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        activity = GameActivity.this;


        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        gameScoreList = new ArrayList<>();
        gameAdapter = new GameAdapter(this, gameScoreList);
        recyclerView.setAdapter(gameAdapter);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        readScore();

        ImageButton button_back = (ImageButton) findViewById(R.id.imageButton_back);
        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        String restart;

        restart = getIntent().getStringExtra("restart");

        if(restart != null){
            gameView = new GameView(GameActivity.this);
            setContentView(gameView);

            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            gameView.invalidate();
                        }
                    });
                }
            },0,TIMET_INTERVAL);
        }else {
            Button start_bt = (Button) findViewById(R.id.bt_start);
            start_bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    gameView = new GameView(GameActivity.this);
                    setContentView(gameView);

                    Timer timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    gameView.invalidate();
                                }
                            });
                        }
                    }, 0, TIMET_INTERVAL);
                }
            });
        }
    }
    private void readScore(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("score");

        Query query = reference.orderByChild("score");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                gameScoreList.clear();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    GameScore gameScore = snapshot.getValue(GameScore.class);
                    gameScoreList.add(gameScore);
                }
                gameAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
