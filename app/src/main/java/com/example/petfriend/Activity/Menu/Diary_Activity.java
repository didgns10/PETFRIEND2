package com.example.petfriend.Activity.Menu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.petfriend.Activity.Login.LoginActivity;
import com.example.petfriend.Activity.Login.Signup_MemberActivity;
import com.example.petfriend.Activity.MainActivity;
import com.example.petfriend.Adapter.Diary.DiaryAdpater;
import com.example.petfriend.Model.Diary;
import com.example.petfriend.Model.DiaryDBHelper;
import com.example.petfriend.Model.Pet;
import com.example.petfriend.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.LinkedList;
import java.util.List;

public class Diary_Activity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private List<Diary> diaryList;
    private RecyclerView.LayoutManager mLayoutManager;
    private DiaryDBHelper dbHelper;
    private DiaryAdpater adapter;
    private String filter = "";
    private PopupMenu popup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary);

        //initialize the variables
        mRecyclerView = (RecyclerView)findViewById(R.id.recycler_view_diary);
        mRecyclerView.setHasFixedSize(true);
        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        //populate recyclerview
        diaryList = new LinkedList<>();
        populaterecyclerView(filter);


        FloatingActionButton bt_plus = (FloatingActionButton)findViewById(R.id.bt_plus);
        bt_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToAddUserActivity();
            }
        });
        ImageButton button_back = (ImageButton) findViewById(R.id.imageButton_back);
        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Diary_Activity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
    private void populaterecyclerView(String filter){
        dbHelper = new DiaryDBHelper(this);
        adapter = new DiaryAdpater(this,dbHelper.diaryList(filter),  mRecyclerView);
        mRecyclerView.setAdapter(adapter);

    }

    private void goToAddUserActivity(){
        Intent intent = new Intent(Diary_Activity.this, Diary_Add_Activity.class);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }

}
