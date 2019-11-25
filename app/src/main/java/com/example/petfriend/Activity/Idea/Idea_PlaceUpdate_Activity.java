package com.example.petfriend.Activity.Idea;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.petfriend.Model.Pet;
import com.example.petfriend.Model.PetFireDBHelper;
import com.example.petfriend.Model.Place;
import com.example.petfriend.Model.PlaceDBHelper;
import com.example.petfriend.Model.PlaceFireDBHelper;
import com.example.petfriend.R;

import java.util.ArrayList;

public class Idea_PlaceUpdate_Activity extends AppCompatActivity {

    private EditText et_name;
    private EditText et_category;
    private EditText et_location;
    private EditText et_photo;
    private EditText et_link;
    private Button bt_update;

    private String key;
    private String name;
    private String cetegory;
    private String location;
    private String photo;
    private String link;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_idea_place_update);

        key = getIntent().getStringExtra("key");
        name = getIntent().getStringExtra("title");
        cetegory = getIntent().getStringExtra("category");
        location = getIntent().getStringExtra("location");
        photo = getIntent().getStringExtra("photo");
        link = getIntent().getStringExtra("link");

        et_name = (EditText) findViewById(R.id.et_update_name);
        et_category = (EditText) findViewById(R.id.et_update_category);
        et_photo = (EditText) findViewById(R.id.et_update_photo);
        et_link = (EditText) findViewById(R.id.et_update_link);
        et_location = (EditText) findViewById(R.id.et_update_location);
        bt_update = (Button) findViewById(R.id.bt_update);

        et_name.setText(name);
        et_category.setText(cetegory);
        et_photo.setText(photo);
        et_link.setText(link);
        et_location.setText(location);

        bt_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Place place = new Place();
                place.setName(et_name.getText().toString().trim());
                place.setCategory(et_category.getText().toString().trim());
                place.setPhoto(et_photo.getText().toString().trim());
                place.setLocation(et_location.getText().toString().trim());
                place.setUrl(et_link.getText().toString().trim());

                new PlaceFireDBHelper().updatePet(key, place, new PlaceFireDBHelper.DataStatus() {
                    @Override
                    public void DataIsLoaded(ArrayList<Place> places, ArrayList<String> keys) {

                    }

                    @Override
                    public void DataIsInserted() {

                    }

                    @Override
                    public void DataIsUpdated() {
                        Toast.makeText(Idea_PlaceUpdate_Activity.this,"업데이트가 완료되었습니다.",Toast.LENGTH_LONG).show();
                        finish();

                    }

                    @Override
                    public void DataIsDeleted() {

                    }
                });
                finish();
            }
        });

    }

    private void goBackHome(){
        startActivity(new Intent(Idea_PlaceUpdate_Activity.this, Idea_place_set_Activity.class));
    }
}
