package com.example.petfriend.Activity.Idea;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.petfriend.Adapter.Place.ItemPlaceSetAdapter;
import com.example.petfriend.Model.Place;
import com.example.petfriend.Model.PlaceDBHelper;
import com.example.petfriend.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class Idea_place_set_Activity extends AppCompatActivity {

    private RecyclerView recyclerView ;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<Place> placeList;
    private ItemPlaceSetAdapter placeAdapter;
    private String filter = "";
    private PlaceDBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_idea_place_set);

        //initialize the variables
        recyclerView = (RecyclerView)findViewById(R.id.recycler_view_place);
        recyclerView.setHasFixedSize(true);
        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);

        //populate recyclerview
        populaterecyclerView(filter);

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
    private void populaterecyclerView(String filter){
        dbHelper = new PlaceDBHelper(this);
        placeAdapter = new ItemPlaceSetAdapter(this, dbHelper.placeList(filter), recyclerView);
        recyclerView.setAdapter(placeAdapter);
    }
    @Override
    protected void onResume() {
        super.onResume();
        placeAdapter.notifyDataSetChanged();
    }
}
