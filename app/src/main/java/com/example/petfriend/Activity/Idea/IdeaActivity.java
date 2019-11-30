package com.example.petfriend.Activity.Idea;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.example.petfriend.Activity.MainActivity;
import com.example.petfriend.Adapter.Pet.ItemPetListAdapter;
import com.example.petfriend.Adapter.Place.ItemPlaceSetAdapter;
import com.example.petfriend.Model.Pet;
import com.example.petfriend.Model.PetFireDBHelper;
import com.example.petfriend.Model.Place;
import com.example.petfriend.Model.PlaceFireDBHelper;
import com.example.petfriend.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class IdeaActivity extends AppCompatActivity {

    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private FirebaseUser firebaseUser;
    private ImageView imageView_pet;
    private ImageView imageView_place;
    public RequestManager mGlideRequestManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_idea);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        ImageButton button_back = (ImageButton) findViewById(R.id.imageButton_back);

         imageView_pet = (ImageView) findViewById(R.id.ImageView_title);
         imageView_place = (ImageView) findViewById(R.id.ImageView_title_place);

        mGlideRequestManager = Glide.with(IdeaActivity.this);
        showPet();
        showplace();

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
    private void showPet(){
        new PetFireDBHelper().readPet(new PetFireDBHelper.DataStatus() {
            @Override
            public void DataIsLoaded(final ArrayList<Pet> petlist, ArrayList<String> keys) {
                final int[] i = {0};
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while(true){
                            try {
                                if(IdeaActivity.this != null) {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            imageView_pet.setVisibility(View.VISIBLE);
                                            AnimationSet set1 = new AnimationSet(true);
                                            set1.setInterpolator(new AccelerateInterpolator());

                                            Animation ani012 = new AlphaAnimation(0.0f, 1.0f);
                                            ani012.setDuration(1000);
                                            set1.addAnimation(ani012);

                                            mGlideRequestManager.load(petlist.get(i[0]).getPhoto()).into(imageView_pet);
                                            imageView_pet.setAnimation(set1);
                                            i[0]++;
                                            if (petlist.size() == i[0]) {
                                                i[0] = 0;
                                            }
                                        }
                                    });
                                }
                                Thread.sleep(2500);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }).start();

            }

            @Override
            public void DataIsInserted() {

            }

            @Override
            public void DataIsUpdated() {

            }

            @Override
            public void DataIsDeleted() {

            }
        });
    }
    private void showplace(){
        new PlaceFireDBHelper().readPlace(new PlaceFireDBHelper.DataStatus() {
            @Override
            public void DataIsLoaded(final ArrayList<Place> placelist, ArrayList<String> keys) {
                final int[] i = {0};
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while(true){
                            try {
                                if(IdeaActivity.this != null) {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            imageView_place.setVisibility(View.VISIBLE);
                                            AnimationSet set = new AnimationSet(true);
                                            set.setInterpolator(new AccelerateInterpolator());

                                            Animation ani01 = new AlphaAnimation(0.0f, 1.0f);
                                            ani01.setDuration(1000);
                                            set.addAnimation(ani01);

                                            mGlideRequestManager.load(placelist.get(i[0]).getPhoto()).into(imageView_place);
                                            imageView_place.setAnimation(set);
                                            i[0]++;
                                            if (placelist.size() == i[0]) {
                                                i[0] = 0;
                                            }
                                        }
                                    });
                                }
                                Thread.sleep(2500);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }).start();

            }

            @Override
            public void DataIsInserted() {

            }

            @Override
            public void DataIsUpdated() {

            }

            @Override
            public void DataIsDeleted() {

            }
        });

    }

}
