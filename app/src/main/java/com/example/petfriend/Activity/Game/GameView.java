package com.example.petfriend.Activity.Game;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.example.petfriend.Model.GameScore;
import com.example.petfriend.R;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class GameView extends View {

    GameActivity MA = (GameActivity) GameActivity.activity;
    private List<GameScore> gameScoreList;

    //canvas
    private int canvasWidth;
    private int canvasHeight;

    FirebaseUser firebaseUser;

    //Bird
    private Bitmap bird[] = new Bitmap[2];
    private int birdX = 10;
    private int birdY;
    private int birdSpeed;

    //Blue Ball
    private int blueX;
    private int blueY;
    private int blueSpeed = 15;
    // private Paint bluePaint = new Paint();
    private  Bitmap bluePaint;

    //Black Ball
    private int blackX;
    private int blackY;
    private  int blackSpeed = 20;
    //  private  Paint blackPaint = new Paint();
    private  Bitmap blackPaint;

    //Background
    private Bitmap bgImage;

    //score
    private Paint scorePaint = new Paint();
    private int score;

    //level
    private Paint levelPaint = new Paint();

    //life
    private Bitmap life[] = new Bitmap[2];
    private int life_count;

    private String username;
    private int highscore;
    private String uid;

    //Status Chedck
    private boolean touch_flg = false;

    public GameView(Context context) {
        super(context);

        bird[0] = BitmapFactory.decodeResource(getResources(), R.drawable.dog_g);
        bird[1] = BitmapFactory.decodeResource(getResources(),R.drawable.dog_g1);

        bgImage = BitmapFactory.decodeResource(getResources(),R.drawable.bg1);


        bluePaint = BitmapFactory.decodeResource(getResources(),R.drawable.bone);

        blackPaint = BitmapFactory.decodeResource(getResources(),R.drawable.chocolate);

        scorePaint.setColor(Color.BLACK);
        scorePaint.setTextSize(50);
        scorePaint.setAntiAlias(true);

        levelPaint.setColor(Color.DKGRAY);
        levelPaint.setTextSize(50);
        levelPaint.setTypeface(Typeface.DEFAULT_BOLD);
        levelPaint.setTextAlign(Paint.Align.CENTER);
        levelPaint.setAntiAlias(true);

        life[0] = BitmapFactory.decodeResource(getResources(),R.drawable.heart_i);
        life[1] = BitmapFactory.decodeResource(getResources(),R.drawable.heart_g);

        birdY = 500;
        score = 0 ;
        life_count = 3;
    }

    @Override
    protected void onDraw(Canvas canvas) {

        canvasWidth = canvas.getWidth();
        canvasHeight = canvas.getHeight();

        canvas.drawBitmap(bgImage,0,0,null);

        int minBirdY = bird[0].getHeight();
        int maxBirdY = canvasHeight - bird[0].getHeight();

        birdY += birdSpeed;
        if(birdY < minBirdY) birdY = minBirdY;
        if(birdY > maxBirdY) birdY = maxBirdY;
        birdSpeed +=4;

        if(touch_flg){
            //Flap wings
            canvas.drawBitmap(bird[1], birdX,birdY,null);
            touch_flg = false;
        }else{
            canvas.drawBitmap(bird[0], birdX, birdY, null);
        }

        //Blue
        blueX -= blueSpeed;
        if(hitCheck(blueX,blueY)){
            score += 10;
            blueX = - 100;
        }
        if(blueX <0){
            blueX = canvasWidth + 20;
            blueY = (int) Math.floor(Math.random() * (maxBirdY - minBirdY)) + minBirdY;
        }
        canvas.drawBitmap(bluePaint,blueX, blueY ,null);

        //Black
        blackX -= blackSpeed;
        if(hitCheck(blackX,blackY)){
            blackX = -100;
            life_count--;
            if(life_count == 0){
                //GAME OVER
                Log.v("MESSAGE","GAME OVER");
                Intent intent = new Intent(getContext(), GameoverActivity.class);
                intent.putExtra("score", score);
                Log.v("score","점수 "+score);
                getContext().startActivity(intent);
                MA.finish();
            }
        }
        if(blackX < 0){
            blackX = canvasWidth + 200;
            blackY = (int) Math.floor(Math.random() * (maxBirdY - minBirdY)) + minBirdY;
        }
        canvas.drawBitmap(blackPaint,blackX, blackY ,null);

        canvas.drawText("Score : " + score,20,60,scorePaint);
      //  canvas.drawText(username, canvasWidth / 2, 60, levelPaint);

        //life
        for(int i= 0; i <3; i ++){
            int x =(int) (canvasWidth -300 + life[0].getWidth() * 1.5 * i);
            int y = 30;

            if(i < life_count){
                canvas.drawBitmap(life[0],x,y,null);
            }else{
                canvas.drawBitmap(life[1],x,y,null);
            }
        }


    }

    public  boolean hitCheck(int x, int y){
        if(birdX < x &&  x < (birdX + bird[0].getWidth()) &&
                birdY < y &&  y < (birdY + bird[0].getHeight())){
            return  true;
        }
        return false;
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN){
            touch_flg = true;
            birdSpeed= -50;
        }
        return true;
    }

}
