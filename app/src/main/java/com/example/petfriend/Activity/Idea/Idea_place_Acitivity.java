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
import com.example.petfriend.Adapter.Place.ItemPlaceSetAdapter;
import com.example.petfriend.Model.Place;
import com.example.petfriend.Model.PlaceDBHelper;
import com.example.petfriend.R;

import java.util.LinkedList;
import java.util.List;

public class Idea_place_Acitivity extends AppCompatActivity {

    private RecyclerView recyclerView ;
    private List<Place> placeList;
    private ItemPlaceAdapter placeAdapter;
    private String filter = "";
    private PlaceDBHelper dbHelper;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_idea_place);

        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_place);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);


        placeList = new LinkedList<>();
       // placeList.addAll(PlaceData.getListData());
        dbHelper = new PlaceDBHelper(this);
        placeAdapter = new ItemPlaceAdapter(this,dbHelper.placeList(filter),recyclerView);
        mRecyclerView.setAdapter(placeAdapter);

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
                placeAdapter.getFilter().filter(All);
            }
        });

        Button Cafe_bt = (Button)findViewById(R.id.button_cafe);
        Cafe_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Cafe = "카페";
                placeAdapter.getFilter().filter(Cafe);
            }
        });
        Button Hos_bt = (Button)findViewById(R.id.button_hospital);
        Hos_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Cafe = "병원";
                placeAdapter.getFilter().filter(Cafe);
            }
        });
        Button Park_bt = (Button)findViewById(R.id.button_park);
        Park_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Cafe = "공원";
                placeAdapter.getFilter().filter(Cafe);
            }
        });

    }
    @Override
    protected void onResume() {
        super.onResume();
        placeAdapter.notifyDataSetChanged();
    }
}
