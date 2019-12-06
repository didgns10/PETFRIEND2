package com.example.petfriend.Activity.Any;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.example.petfriend.Activity.MainActivity;
import com.example.petfriend.Adapter.PetLoseAdpater;
import com.example.petfriend.Model.PetLose;
import com.example.petfriend.Model.PetLoseFireDBHelper;
import com.example.petfriend.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class PetLoseActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<PetLose> listPet;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private FloatingActionButton bt_plus;
    private RelativeLayout loaderlayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_lose);

        loaderlayout = findViewById(R.id.loaderlayout);

        ImageButton bt_back = (ImageButton)findViewById(R.id.imageButton_back) ;
        bt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PetLoseActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        bt_plus = (FloatingActionButton)findViewById(R.id.bt_plus);
        bt_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToAddUserActivity();
            }
        });

        recyclerView = findViewById(R.id.recycler_view);
        loaderlayout.setVisibility(View.VISIBLE);
        showRecyclerViewPet();

    }

    private void showRecyclerViewPet(){
        new PetLoseFireDBHelper().readPetLose(new PetLoseFireDBHelper.DataStatus() {
            @Override
            public void DataIsLoaded(ArrayList<PetLose> petlist, ArrayList<String> keys) {
                new PetLoseAdpater().setConfig(recyclerView, PetLoseActivity.this,petlist,keys);
                bt_plus.show();
                loaderlayout.setVisibility(View.GONE);
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


    private void goToAddUserActivity(){
        Intent intent = new Intent(PetLoseActivity.this, PetLoseAddActivity.class);
        startActivity(intent);
    }

}