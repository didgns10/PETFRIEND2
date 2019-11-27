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

import com.example.petfriend.Adapter.Pet.ItemPetCardViewAdapter;
import com.example.petfriend.Adapter.Pet.ItemPetAdapter;
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

public class Idea_pet_Activity extends AppCompatActivity {

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
        setContentView(R.layout.activity_idea_pet);

        loaderlayout = findViewById(R.id.loaderlayout);

        ImageButton bt_back = (ImageButton)findViewById(R.id.imageButton_back) ;
        bt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Idea_pet_Activity.this, IdeaActivity.class);
                startActivity(intent);
                finish();
            }
        });

        recyclerView = findViewById(R.id.recycler_view_pet);
        loaderlayout.setVisibility(View.VISIBLE);
        showRecyclerViewList();

        ImageButton bt_view = (ImageButton)findViewById(R.id.imageButton_view);
        bt_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup= new PopupMenu(getApplicationContext(), v);//v는 클릭된 뷰를 의미

                getMenuInflater().inflate(R.menu.item_pet_view, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.action_list:
                                showRecyclerViewList();
                                break;
                            case R.id.action_list_visible:
                                showRecyclerViewListVisible();
                                break;
                            case R.id.action_grid:
                                showRecyclerViewGrid();
                                break;
                            case R.id.action_cardview:
                                showRecyclerViewCardView();
                                break;
                            case R.id.action_hor:
                                showRecyclerViewListHor();
                                break;
                        }
                        return false;
                    }
                });
                popup.show();//Popup Menu 보이기
            }
        });

    }

    private void showRecyclerViewList(){
        new PetFireDBHelper().readPet(new PetFireDBHelper.DataStatus() {
            @Override
            public void DataIsLoaded(ArrayList<Pet> petlist, ArrayList<String> keys) {
                recyclerView.setLayoutManager(new LinearLayoutManager(Idea_pet_Activity.this));
                ItemPetListAdapter listAdapter = new ItemPetListAdapter(Idea_pet_Activity.this,petlist);
                recyclerView.setAdapter(listAdapter);
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
    private void showRecyclerViewCardView(){
        new PetFireDBHelper().readPet(new PetFireDBHelper.DataStatus() {
            @Override
            public void DataIsLoaded(ArrayList<Pet> petlist, ArrayList<String> keys) {
                recyclerView.setLayoutManager(new LinearLayoutManager(Idea_pet_Activity.this));
                ItemPetCardViewAdapter cardViewAdapter = new ItemPetCardViewAdapter(Idea_pet_Activity.this,petlist);
                recyclerView.setAdapter(cardViewAdapter);
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
    private void showRecyclerViewListVisible(){
        new PetFireDBHelper().readPet(new PetFireDBHelper.DataStatus() {
            @Override
            public void DataIsLoaded(ArrayList<Pet> petlist, ArrayList<String> keys) {
                recyclerView.setLayoutManager(new LinearLayoutManager(Idea_pet_Activity.this));
                ItemPetListVisibleAdapter listAdapter = new ItemPetListVisibleAdapter(Idea_pet_Activity.this,petlist);
                recyclerView.setAdapter(listAdapter);
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
    private void showRecyclerViewGrid(){
        new PetFireDBHelper().readPet(new PetFireDBHelper.DataStatus() {
            @Override
            public void DataIsLoaded(ArrayList<Pet> petlist, ArrayList<String> keys) {
                recyclerView.setLayoutManager(new GridLayoutManager(Idea_pet_Activity.this,2));
                ItemPetGridAdapter gridAdapter = new ItemPetGridAdapter(Idea_pet_Activity.this,petlist);
                recyclerView.setAdapter(gridAdapter);
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
    private void showRecyclerViewListHor(){
        new PetFireDBHelper().readPet(new PetFireDBHelper.DataStatus() {
            @Override
            public void DataIsLoaded(ArrayList<Pet> petlist, ArrayList<String> keys) {
                GridLayoutManager gridLayoutManager = new GridLayoutManager(Idea_pet_Activity.this,2,GridLayoutManager.HORIZONTAL,false);
                recyclerView.setLayoutManager(gridLayoutManager);
                ItemPetHorizonAdapter horizonAdapter = new ItemPetHorizonAdapter(Idea_pet_Activity.this,petlist);
                recyclerView.setAdapter(horizonAdapter);
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
        Intent intent = new Intent(Idea_pet_Activity.this, Idea_PetAddActivity.class);
        startActivity(intent);
    }

}
