package com.example.petfriend.Activity.Idea;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.petfriend.Model.Place;
import com.example.petfriend.Model.PlaceDBHelper;
import com.example.petfriend.R;

public class Idea_PlaceUpdate_Activity extends AppCompatActivity {

    private EditText et_name;
    private EditText et_category;
    private EditText et_location;
    private EditText et_photo;
    private EditText et_link;
    private Button bt_update;

    private PlaceDBHelper dbHelper;
    private long receivedPlaceId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_idea_place_update);

        et_name = (EditText)findViewById(R.id.et_update_name);
        et_category = (EditText)findViewById(R.id.et_update_category);
        et_photo = (EditText)findViewById(R.id.et_update_photo);
        et_link = (EditText)findViewById(R.id.et_update_link);
        et_location = (EditText)findViewById(R.id.et_update_location);
        bt_update = (Button)findViewById(R.id.bt_update);

        dbHelper = new PlaceDBHelper(this);

        try {
            //get intent to get person id
            receivedPlaceId = getIntent().getLongExtra("USER_ID", 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        /***populate user data before update***/
        Place queriedPlace = dbHelper.getPlace(receivedPlaceId);
        //set field to this user data
        et_name.setText(queriedPlace.getName());
        et_category.setText(queriedPlace.getCategory());
        et_photo.setText(queriedPlace.getPhoto());
        et_link.setText(queriedPlace.getUrl());
        et_location.setText(queriedPlace.getLocation());

        //listen to add button click
        bt_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //call the save person method
                updatePlace();
            }
        });
    }
    private void updatePlace(){
        String name = et_name.getText().toString().trim();
        String category = et_category.getText().toString().trim();
        String photo = et_photo.getText().toString().trim();
        String link = et_link.getText().toString().trim();
        String location = et_location.getText().toString().trim();

        if(name.isEmpty() && category.isEmpty() && photo.isEmpty() && link.isEmpty() && location.isEmpty()){
            //error name is empty
            Toast.makeText(this, "빈칸 없이 채워주세요.", Toast.LENGTH_SHORT).show();
        }else{
            //create new person
            Place updatedplace = new Place(name, category, photo, location,link);
            dbHelper.updatePlaceRecord(receivedPlaceId, this, updatedplace);

            //finally redirect back home
            // NOTE you can implement an sqlite callback then redirect on success delete
            goBackHome();
        }
    }

    private void goBackHome(){
        startActivity(new Intent(Idea_PlaceUpdate_Activity.this, Idea_place_set_Activity.class));
    }
}
