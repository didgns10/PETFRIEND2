package com.example.petfriend.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.PopupMenu;

import com.example.petfriend.Activity.Idea.IdeaActivity;
import com.example.petfriend.Activity.Login.LoginActivity;
import com.example.petfriend.Activity.Login.Signup_MemberActivity;
import com.example.petfriend.Activity.Menu.likeActivity;
import com.example.petfriend.Activity.Menu.myprofileActivity;
import com.example.petfriend.Activity.Menu.optionActivity;
import com.example.petfriend.Activity.Newspeed.newspeedActivity;
import com.example.petfriend.Activity.Newspeed.newspeed_photo_selectActivity;
import com.example.petfriend.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

       if(FirebaseAuth.getInstance().getCurrentUser() == null) {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }else {
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            DocumentReference docRef = db.collection("users").document(user.getUid());
            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if(document != null) {
                            if (document.exists()) {
                                Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                            } else {
                                Log.d(TAG, "No such document");
                                Intent intent = new Intent(MainActivity.this, Signup_MemberActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }
                    } else {
                        Log.d(TAG, "get failed with ", task.getException());
                    }

                }
            });
        }
        ImageButton button_menu = (ImageButton) findViewById(R.id.imageButton_menu);
        ImageButton button_newspeed = (ImageButton) findViewById(R.id.imageButton_newspeed);
        ImageButton button_idea = (ImageButton) findViewById(R.id.imageButton_idea);
        ImageButton button_notice = (ImageButton) findViewById(R.id.imageButton_notice);
        ImageButton button_chat = (ImageButton) findViewById(R.id.imageButton_chat);
        ImageButton button_serach = (ImageButton) findViewById(R.id.imageButton_search);

        button_newspeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent4 = new Intent(MainActivity.this, newspeedActivity.class);
                intent4.addFlags (Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent4);

            }
        });
        // 정보주는 이미지 클릭했을때
        button_idea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent4 = new Intent(MainActivity.this, IdeaActivity.class);
                intent4.addFlags (Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent4);

            }
        });



        button_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popup= new PopupMenu(getApplicationContext(), view);//v는 클릭된 뷰를 의미

                getMenuInflater().inflate(R.menu.menu, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.like_baguni:
                                Intent intent = new Intent(MainActivity.this, likeActivity.class);
                                startActivity(intent);
                                break;
                            case R.id.my_profile:
                                Intent intent1 = new Intent(MainActivity.this, myprofileActivity.class);
                                startActivity(intent1);
                                break;
                            case R.id.option:
                                Intent intent2 = new Intent(MainActivity.this, optionActivity.class);
                                startActivity(intent2);
                            default:
                                break;
                        }
                        return false;
                    }
                });
                popup.show();//Popup Menu 보이기
            }

        });
    }


    public void image_like4(View view) {

                Intent intent = new Intent(MainActivity.this, newspeed_photo_selectActivity.class);
                startActivity(intent);
    }
}

