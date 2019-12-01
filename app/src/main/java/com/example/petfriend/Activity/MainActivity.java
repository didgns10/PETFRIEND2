package com.example.petfriend.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.RenderNode;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.petfriend.Activity.Idea.IdeaActivity;
import com.example.petfriend.Activity.Login.LoginActivity;
import com.example.petfriend.Activity.Login.Signup_MemberActivity;
import com.example.petfriend.Activity.Menu.Diary_Activity;
import com.example.petfriend.Activity.Menu.likeActivity;
import com.example.petfriend.Activity.Menu.myprofileActivity;
import com.example.petfriend.Activity.Menu.optionActivity;
import com.example.petfriend.Activity.Newspeed.newspeedActivity;
import com.example.petfriend.Activity.Newspeed.newspeed_photo_selectActivity;
import com.example.petfriend.Activity.Post.PostActivity;
import com.example.petfriend.Adapter.NewsAdpter;
import com.example.petfriend.Fragment.HomeFragment;
import com.example.petfriend.Fragment.NewspeedFragment;
import com.example.petfriend.Fragment.NotificationFragment;
import com.example.petfriend.Fragment.ProfileFragment;
import com.example.petfriend.Fragment.SearchFragment;
import com.example.petfriend.Model.Newsdata;
import com.example.petfriend.Model.Place;
import com.example.petfriend.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private FirebaseAuth mAuth;
    BottomNavigationView bottomNavigationView;
    Fragment selectedFragment = null;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mReferncePlace;
    private FirebaseUser user;
    private FragmentTransaction fragmentTransaction;

    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottom_navigaion);
        final ImageView imageView = findViewById(R.id.imageView_title);


        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        } else {
            mDatabase = FirebaseDatabase.getInstance();
            mReferncePlace = mDatabase.getReference("users");
            mDatabase.getReference().child("users").child(user.getUid()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot != null) {
                        if (dataSnapshot.exists()) {
                            Log.d(TAG, "such document");
                        } else {
                            Log.d(TAG, "No such document");
                            Intent intent = new Intent(MainActivity.this, Signup_MemberActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

        bottomNavigationView.setOnNavigationItemSelectedListener(navigationItemSelectedListener);

        Bundle intent = getIntent().getExtras();
        if (intent != null){
            String publisher = intent.getString("publisherid");

            SharedPreferences.Editor editor = getSharedPreferences("PREPS",MODE_PRIVATE).edit();
            editor.putString("profileid",publisher);
            editor.apply();

            fragmentTransaction = getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new ProfileFragment());
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }else{
            fragmentTransaction = getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new HomeFragment());
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }


        ImageButton button_menu = (ImageButton) findViewById(R.id.imageButton_menu);

        button_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popup = new PopupMenu(getApplicationContext(), view);//v는 클릭된 뷰를 의미

                getMenuInflater().inflate(R.menu.menu, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.pet_idea:
                                Intent intent4 = new Intent(MainActivity.this, IdeaActivity.class);
                                startActivity(intent4);
                                break;
                            case R.id.diary:
                                Intent intent = new Intent(MainActivity.this, Diary_Activity.class);
                                startActivity(intent);
                                break;
                            case R.id.like_baguni:
                                Intent intent3 = new Intent(MainActivity.this, likeActivity.class);
                                startActivity(intent3);
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
                        return true;
                    }
                });
                popup.show();//Popup Menu 보이기
            }

        });


    }

    private BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                    switch (menuItem.getItemId()) {
                        case R.id.nav_home:
                            selectedFragment = new HomeFragment();
                            break;
                        case R.id.nav_search:
                            selectedFragment = new SearchFragment();
                            break;
                        case R.id.nav_newspeed:
                            selectedFragment = new NewspeedFragment();
                            break;
                        case R.id.nav_heart:
                            selectedFragment = new NotificationFragment();
                            break;
                        case R.id.nav_profile:
                            SharedPreferences.Editor editor = getSharedPreferences("PREFS", MODE_PRIVATE).edit();
                            editor.putString("profileid", FirebaseAuth.getInstance().getCurrentUser().getUid());
                            editor.apply();
                            selectedFragment = new ProfileFragment();
                            break;
                    }
                    if (selectedFragment != null) {
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                selectedFragment).commit();
                    }

                    return true;
                }
            };

}

