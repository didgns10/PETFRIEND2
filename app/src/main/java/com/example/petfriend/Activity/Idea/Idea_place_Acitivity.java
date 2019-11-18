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

import com.example.petfriend.Adapter.ItemPlaceAdapter;
import com.example.petfriend.Model.Place;
import com.example.petfriend.Model.PlaceData;
import com.example.petfriend.R;

import java.util.ArrayList;

public class Idea_place_Acitivity extends AppCompatActivity {

    private ArrayList<Place> petlist;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_idea_place);

        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_place);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);


        petlist = new ArrayList<>();
        petlist.addAll(PlaceData.getListData());

        final ItemPlaceAdapter mAdapter = new ItemPlaceAdapter(this,petlist);
        mRecyclerView.setAdapter(mAdapter);

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
                mAdapter.getFilter().filter(All);
            }
        });

        Button Cafe_bt = (Button)findViewById(R.id.button_cafe);
        Cafe_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Cafe = "카페";
                mAdapter.getFilter().filter(Cafe);
            }
        });
        Button Hos_bt = (Button)findViewById(R.id.button_hospital);
        Hos_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Cafe = "병원";
                mAdapter.getFilter().filter(Cafe);
            }
        });
        Button Park_bt = (Button)findViewById(R.id.button_park);
        Park_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Cafe = "공원";
                mAdapter.getFilter().filter(Cafe);
            }
        });

    }
}
