package com.example.petfriend.Activity.Idea;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.PopupMenu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.petfriend.Adapter.ItemPetCardViewAdapter;
import com.example.petfriend.Adapter.ItemPetGridAdapter;
import com.example.petfriend.Adapter.ItemPetHorizonAdapter;
import com.example.petfriend.Adapter.ItemPetListAdapter;
import com.example.petfriend.Adapter.ItemPetListVisibleAdapter;
import com.example.petfriend.Model.Pet;
import com.example.petfriend.Model.PetData;
import com.example.petfriend.R;

import java.util.ArrayList;

public class Idea_pet_Activity extends AppCompatActivity {

    private RecyclerView recyclerView ,recyclerView_Hor;
    private ArrayList<Pet> list;

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

        list = new ArrayList<>();
        //    list.addAll(MountainData.getListData());
        //    showRecyclerViewList();

        if(savedInstanceState == null){
        //    setActionBarTitle("List Mode");
            list.addAll(PetData.getListData());
            showRecyclerViewList();
            mode = R.id.action_list;
        } else{
            String stateTitle = savedInstanceState.getString(STATE_TITLE);
            ArrayList<Pet> stateList = savedInstanceState.getParcelableArrayList(STATE_LIST);
            int stateMode = savedInstanceState.getInt(STATE_MODE);
            setActionBarTitle(stateTitle);
            list.addAll(stateList);
        }
        ImageButton bt_back = (ImageButton)findViewById(R.id.imageButton_back) ;
        bt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Idea_pet_Activity.this, IdeaActivity.class);
                startActivity(intent);
                finish();
            }
        });

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
        //                        setActionBarTitle("List Mode");
                                showRecyclerViewList();
                                break;
                            case R.id.action_list_visible:
       //                         setActionBarTitle("List Visible Mode");
                                showRecyclerViewListVisible();
                                break;
                            case R.id.action_grid:
         //                       setActionBarTitle("Grid Mode");
                                showRecyclerViewGrid();
                                break;
                            case R.id.action_cardview:
                             //   setActionBarTitle("Card Mode");
                                showRecyclerViewCardView();
                                break;
                            case R.id.action_hor:
                           //     setActionBarTitle("Horizon Mode");
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



    private void showRecyclerViewListVisible() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ItemPetListVisibleAdapter listAdapter = new ItemPetListVisibleAdapter(this);
        listAdapter.setListMountain(list);
        recyclerView.setAdapter(listAdapter);
    }

    private void showRecyclerViewListHor() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2,GridLayoutManager.HORIZONTAL,false);
        recyclerView.setLayoutManager(gridLayoutManager);

        ItemPetHorizonAdapter horizonAdapter = new ItemPetHorizonAdapter(this);
        horizonAdapter.setListMountain(list);
        recyclerView.setAdapter(horizonAdapter);
    }

    private void showRecyclerViewCardView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ItemPetCardViewAdapter cardViewAdapter = new ItemPetCardViewAdapter(this);
        cardViewAdapter.setListMountain(list);
        recyclerView.setAdapter(cardViewAdapter);
    }

    private void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }

    private void showRecyclerViewGrid() {

        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        ItemPetGridAdapter gridAdapter = new ItemPetGridAdapter(this);
        gridAdapter.setListMountain(list);
        recyclerView.setAdapter(gridAdapter);

    }

    private void showRecyclerViewList() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ItemPetListAdapter listAdapter = new ItemPetListAdapter(this);
        listAdapter.setListMountain(list);
        recyclerView.setAdapter(listAdapter);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
   //     outState.putString(STATE_TITLE, getSupportActionBar().getTitle().toString());
        outState.putParcelableArrayList(STATE_LIST, list);
        outState.putInt(STATE_MODE, mode);
    }
}
