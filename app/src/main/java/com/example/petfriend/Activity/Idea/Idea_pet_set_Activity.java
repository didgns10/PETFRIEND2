package com.example.petfriend.Activity.Idea;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.petfriend.Adapter.Pet.ItemPetAdapter;
import com.example.petfriend.Adapter.Pet.ItemPetCardViewAdapter;
import com.example.petfriend.Adapter.Pet.ItemPetGridAdapter;
import com.example.petfriend.Adapter.Pet.ItemPetHorizonAdapter;
import com.example.petfriend.Adapter.Pet.ItemPetListAdapter;
import com.example.petfriend.Adapter.Pet.ItemPetListVisibleAdapter;
import com.example.petfriend.Model.Pet;
import com.example.petfriend.Model.PetFireDBHelper;
import com.example.petfriend.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Idea_pet_set_Activity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Pet> listPet;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private FloatingActionButton bt_plus;
    private RelativeLayout loaderlayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_idea_pet_set);

        loaderlayout = findViewById(R.id.loaderlayout);

        ImageButton bt_back = (ImageButton)findViewById(R.id.imageButton_back) ;
        bt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Idea_pet_set_Activity.this, IdeaActivity.class);
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

        recyclerView = findViewById(R.id.recycler_view_pet);
        loaderlayout.setVisibility(View.VISIBLE);
        showRecyclerViewPet();

        ImageButton bt_view = (ImageButton)findViewById(R.id.imageButton_view);
        bt_view.setVisibility(View.GONE);
    }

    private void showRecyclerViewPet(){
        new PetFireDBHelper().readPet(new PetFireDBHelper.DataStatus() {
            @Override
            public void DataIsLoaded(ArrayList<Pet> petlist, ArrayList<String> keys) {
                new ItemPetAdapter().setConfig(recyclerView, Idea_pet_set_Activity.this,petlist,keys);
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
        Intent intent = new Intent(Idea_pet_set_Activity.this, Idea_PetAddActivity.class);
        startActivity(intent);
    }

}