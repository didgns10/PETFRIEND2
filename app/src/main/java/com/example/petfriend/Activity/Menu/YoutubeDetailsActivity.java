package com.example.petfriend.Activity.Menu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.petfriend.Activity.MainActivity;
import com.example.petfriend.R;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class YoutubeDetailsActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener
,YouTubePlayer.PlaybackEventListener,YouTubePlayer.PlayerStateChangeListener{

    private YouTubePlayerView playerView;
    private YouTubePlayer player;
    private String API_KEY = "AIzaSyDaAUIsNyhBmAPw2Ss6gVgFaRbA2Rk7gI8";
    private String VIDEO_ID ;
    private int i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube_details);
        playerView = findViewById(R.id.palyerview);
        playerView.initialize(API_KEY,this);

        Button bt_back = findViewById(R.id.button_back);
        bt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(YoutubeDetailsActivity.this,YoutubeActivity.class);
                startActivity(intent);
                finish();
            }
        });

        Intent intent= getIntent();
        VIDEO_ID = intent.getStringExtra("videoId");

        // 해당 비디오의 시간을 저장해서 그 시간에 플레이를 하게 도와준다.
        SharedPreferences pref = getSharedPreferences("time"+VIDEO_ID, MODE_PRIVATE);
        i = pref.getInt("hi",0);

    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {

        this.player = youTubePlayer;

        youTubePlayer.setPlayerStateChangeListener(this);
        youTubePlayer.setPlaybackEventListener(this);

        if(!b){
            youTubePlayer.cueVideo(VIDEO_ID,i);
        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

    }

    @Override
    public void onPlaying() {

    }

    @Override
    public void onPaused() {
        i = player.getCurrentTimeMillis();
        SharedPreferences pref = getSharedPreferences("time"+VIDEO_ID, MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt("hi", i);
        editor.commit();

    }

    @Override
    public void onStopped() {

    }

    @Override
    public void onBuffering(boolean b) {

    }

    @Override
    public void onSeekTo(int i) {

    }

    @Override
    public void onLoading() {

    }

    @Override
    public void onLoaded(String s) {

    }

    @Override
    public void onAdStarted() {

    }

    @Override
    public void onVideoStarted() {

    }

    @Override
    public void onVideoEnded() {

    }

    @Override
    public void onError(YouTubePlayer.ErrorReason errorReason) {

    }
}
