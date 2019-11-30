package com.example.petfriend.Activity.Idea;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.example.petfriend.Adapter.Place.ItemPlaceSetAdapter;
import com.example.petfriend.Model.Place;
import com.example.petfriend.Model.PlaceFireDBHelper;
import com.example.petfriend.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Idea_place_set_Activity extends AppCompatActivity {

    private RecyclerView recyclerView ;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<Place> placeList;
    private ItemPlaceSetAdapter placeAdapter;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private String filter = "";
    private RelativeLayout loaderlayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_idea_place_set);

        loaderlayout = findViewById(R.id.loaderlayout);
        //initialize the variables
        recyclerView = (RecyclerView)findViewById(R.id.recycler_view_place);
        recyclerView.setHasFixedSize(true);
        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        ((LinearLayoutManager) mLayoutManager).setReverseLayout(true);
        ((LinearLayoutManager) mLayoutManager).setStackFromEnd(true);
        recyclerView.setLayoutManager(mLayoutManager);
        loaderlayout.setVisibility(View.VISIBLE);
        //populate recyclerview
        populaterecyclerView();

        FloatingActionButton bt_plus = (FloatingActionButton)findViewById(R.id.bt_plus);
        bt_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Idea_place_set_Activity.this, Idea_PlaceAdd_Activity.class);
                startActivity(intent);
            }
        });
        ImageButton bt_back = (ImageButton)findViewById(R.id.imageButton_back) ;
        bt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Idea_place_set_Activity.this, IdeaActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
    private void populaterecyclerView(){
        new PlaceFireDBHelper().readPlace(new PlaceFireDBHelper.DataStatus() {
            @Override
            public void DataIsLoaded(ArrayList<Place> placelist, ArrayList<String> keys) {
                new ItemPlaceSetAdapter().setConfig(recyclerView, Idea_place_set_Activity.this,placelist,keys);
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

}
