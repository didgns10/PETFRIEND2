package com.example.petfriend.Activity.Idea;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.example.petfriend.Activity.MainActivity;
import com.example.petfriend.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class IdeaActivity extends AppCompatActivity {

    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_idea);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        ImageButton button_back = (ImageButton) findViewById(R.id.imageButton_back);

        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent4 = new Intent(IdeaActivity.this, MainActivity.class);
                intent4.addFlags (Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent4);
                finish();
            }
        });

        CardView cardView_place = (CardView)findViewById(R.id.cardView_place);
        cardView_place.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(firebaseUser.getUid().equals("o7WI1MVBkufUvCzoRuu4nynK4ou2")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(IdeaActivity.this);
                    builder.setTitle("화면을 선택하세요.");
                    builder.setMessage("관리자모드 또는 일반모드");
                    builder.setPositiveButton("관리자모드", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(IdeaActivity.this, Idea_place_set_Activity.class);
                            startActivity(intent);
                        }
                    });
                    builder.setNeutralButton("취소", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    builder.setNegativeButton("일반모드", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(IdeaActivity.this, Idea_place_Activity.class);
                            startActivity(intent);
                        }
                    });
                    builder.create().show();
                }else{
                    Intent intent = new Intent(IdeaActivity.this, Idea_place_Activity.class);
                    startActivity(intent);
                    Log.e("유저",firebaseUser.getUid());
                }
            }
        });

        CardView cardView_pet = (CardView)findViewById(R.id.cardView_pet);
        cardView_pet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(firebaseUser.getUid().equals("o7WI1MVBkufUvCzoRuu4nynK4ou2")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(IdeaActivity.this);
                    builder.setTitle("화면을 선택하세요.");
                    builder.setMessage("관리자모드 또는 일반모드");
                    builder.setPositiveButton("관리자모드", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(IdeaActivity.this, Idea_pet_set_Activity.class);
                            startActivity(intent);
                        }
                    });
                    builder.setNeutralButton("취소", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    builder.setNegativeButton("일반모드", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(IdeaActivity.this, Idea_pet_Activity.class);
                            startActivity(intent);
                        }
                    });
                    builder.create().show();
                }else{
                    Intent intent = new Intent(IdeaActivity.this, Idea_pet_Activity.class);
                    startActivity(intent);
                    Log.e("유저",firebaseUser.getUid());
                }
            }
        });
    }
}
