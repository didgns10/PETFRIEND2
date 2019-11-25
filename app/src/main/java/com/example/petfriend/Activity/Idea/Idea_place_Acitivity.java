package com.example.petfriend.Activity.Idea;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.petfriend.Adapter.Place.ItemPlaceAdapter;
import com.example.petfriend.Model.Place;
import com.example.petfriend.Model.PlaceFireDBHelper;
import com.example.petfriend.R;

import java.util.ArrayList;

public class Idea_place_Acitivity extends AppCompatActivity {

    private RecyclerView recyclerView ;
    private ArrayList<Place> placeList;
    private ItemPlaceAdapter placeAdapter;
    private String filter = "";
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_idea_place);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_place);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        new PlaceFireDBHelper().readPlace(new PlaceFireDBHelper.DataStatus() {
            @Override
            public void DataIsLoaded(ArrayList<Place> places, ArrayList<String> keys) {
                final ItemPlaceAdapter itemPlaceAdapter = new ItemPlaceAdapter(Idea_place_Acitivity.this,places);
                mRecyclerView.setAdapter(itemPlaceAdapter);

             //   placeList = new ArrayList<>();

                DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(), mLinearLayoutManager.getOrientation());
                mRecyclerView.addItemDecoration(dividerItemDecoration);

                ImageButton bt_back = (ImageButton)findViewById(R.id.imageButton_back) ;
                bt_back.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Idea_place_Acitivity.this, IdeaActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });

                Button All_bt = (Button)findViewById(R.id.button_All);
                All_bt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String All = "";
                        itemPlaceAdapter.getFilter().filter(All);
                    }
                });

                Button Cafe_bt = (Button)findViewById(R.id.button_cafe);
                Cafe_bt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String Cafe = "카페";
                        itemPlaceAdapter.getFilter().filter(Cafe);
                    }
                });
                Button Hos_bt = (Button)findViewById(R.id.button_hospital);
                Hos_bt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String Cafe = "병원";
                        itemPlaceAdapter.getFilter().filter(Cafe);
                    }
                });
                Button Park_bt = (Button)findViewById(R.id.button_park);
                Park_bt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String Cafe = "공원";
                        itemPlaceAdapter.getFilter().filter(Cafe);
                    }
                });
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
