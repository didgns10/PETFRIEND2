package com.example.petfriend.Activity.Idea;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.PopupMenu;

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
import com.example.petfriend.Model.PetDBHelper;
import com.example.petfriend.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.LinkedList;
import java.util.List;

public class Idea_pet_Activity extends AppCompatActivity {

    private RecyclerView recyclerView ,recyclerView_Hor;
    private List<Pet> list;
    private String filter = "";
    private PetDBHelper dbHelper;
    private ItemPetAdapter itemPetAdapter;
    private FloatingActionButton bt_plus;

    // 화면 전환해도 데이터 유지 하게 해주는 세팅
    final String STATE_TITLE = "state_title";
    final String STATE_LIST = "state_list";
    final String STATE_MODE = "state_mode";

    int mode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_idea_pet);

        recyclerView = findViewById(R.id.recycler_view_pet);
        recyclerView.setHasFixedSize(true);

        ImageButton bt_back = (ImageButton)findViewById(R.id.imageButton_back) ;
        bt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Idea_pet_Activity.this, IdeaActivity.class);
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

        list = new LinkedList<>();
        showRecyclerViewPet();

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
                            case R.id.action_pet:
                                showRecyclerViewPet();
                                break;
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

    private void showRecyclerViewPet() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        dbHelper = new PetDBHelper(this);
        itemPetAdapter = new ItemPetAdapter(this,dbHelper.petList(filter),recyclerView);
        recyclerView.setAdapter(itemPetAdapter);
        bt_plus.show();
    }

    private void showRecyclerViewListVisible() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        dbHelper = new PetDBHelper(this);
        ItemPetListVisibleAdapter listAdapter = new ItemPetListVisibleAdapter(this,dbHelper.petList(filter),recyclerView);
        recyclerView.setAdapter(listAdapter);
        bt_plus.hide();
    }

    private void showRecyclerViewListHor() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2,GridLayoutManager.HORIZONTAL,false);
        recyclerView.setLayoutManager(gridLayoutManager);
        dbHelper = new PetDBHelper(this);
        ItemPetHorizonAdapter horizonAdapter = new ItemPetHorizonAdapter(this,dbHelper.petList(filter),recyclerView);
        recyclerView.setAdapter(horizonAdapter);
        bt_plus.hide();

    }

    private void showRecyclerViewCardView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        dbHelper = new PetDBHelper(this);
        ItemPetCardViewAdapter cardViewAdapter = new ItemPetCardViewAdapter(this,dbHelper.petList(filter),recyclerView);
        recyclerView.setAdapter(cardViewAdapter);
        bt_plus.hide();
    }

    private void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }

    private void showRecyclerViewGrid() {

        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        dbHelper = new PetDBHelper(this);
        ItemPetGridAdapter gridAdapter = new ItemPetGridAdapter(this,dbHelper.petList(filter),recyclerView);
        recyclerView.setAdapter(gridAdapter);
        bt_plus.hide();
    }

    private void showRecyclerViewList() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        dbHelper = new PetDBHelper(this);
        ItemPetListAdapter listAdapter = new ItemPetListAdapter(this,dbHelper.petList(filter),recyclerView);
        recyclerView.setAdapter(listAdapter);
        bt_plus.hide();
    }
    private void goToAddUserActivity(){
        Intent intent = new Intent(Idea_pet_Activity.this, Idea_PetAddActivity.class);
        startActivity(intent);
    }
    @Override
    protected void onResume() {
        super.onResume();
        itemPetAdapter.notifyDataSetChanged();
    }

}
