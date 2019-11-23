package com.example.petfriend.Activity.Idea;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.petfriend.Model.Pet;
import com.example.petfriend.Model.PetDBHelper;
import com.example.petfriend.Model.Place;
import com.example.petfriend.Model.PlaceDBHelper;
import com.example.petfriend.R;

public class Idea_PlaceAdd_Activity extends AppCompatActivity {

    private EditText et_name;
    private EditText et_category;
    private EditText et_location;
    private EditText et_photo;
    private EditText et_link;
    private Button bt_add;

    private PlaceDBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_idea_place_add);

        et_name = (EditText)findViewById(R.id.et_add_name);
        et_category = (EditText)findViewById(R.id.et_add_category);
        et_photo = (EditText)findViewById(R.id.et_add_photo);
        et_link = (EditText)findViewById(R.id.et_add_link);
        et_location = (EditText)findViewById(R.id.et_add_location);
        bt_add = (Button)findViewById(R.id.bt_add);

        //listen to add button click
        bt_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //call the save person method
                savePlace();
            }
        });
    }
    private void savePlace(){
        String name = et_name.getText().toString().trim();
        String category = et_category.getText().toString().trim();
        String photo = et_photo.getText().toString().trim();
        String link = et_link.getText().toString().trim();
        String location = et_location.getText().toString().trim();
        dbHelper = new PlaceDBHelper(this);

        if(name.isEmpty() && category.isEmpty() && photo.isEmpty() && link.isEmpty() && location.isEmpty()){
            //error name is empty
            Toast.makeText(this, "빈칸 없이 채워주세요.", Toast.LENGTH_SHORT).show();
        }else{
            //create new person
            Place place = new Place(name, category, photo, location,link);
            dbHelper.saveNewPlace(place);

            //finally redirect back home
            // NOTE you can implement an sqlite callback then redirect on success delete
            goBackHome();
        }
    }

    private void goBackHome(){
        startActivity(new Intent(Idea_PlaceAdd_Activity.this, Idea_place_set_Activity.class));
    }
}
