package com.example.petfriend.Activity.Idea;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.petfriend.Activity.MainActivity;
import com.example.petfriend.Model.Pet;
import com.example.petfriend.Model.PetDBHelper;
import com.example.petfriend.R;

public class Idea_PetAddActivity extends AppCompatActivity {

    private EditText et_name;
    private EditText et_elevation;
    private EditText et_location;
    private EditText et_photo;
    private EditText et_description;
    private Button bt_add;

    private PetDBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_idea_pet_add);

        et_name = (EditText)findViewById(R.id.et_add_name);
        et_elevation = (EditText)findViewById(R.id.et_add_elevation);
        et_photo = (EditText)findViewById(R.id.et_add_photo);
        et_description = (EditText)findViewById(R.id.et_add_description);
        et_location = (EditText)findViewById(R.id.et_add_location);
        bt_add = (Button)findViewById(R.id.bt_add);

        //listen to add button click
        bt_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //call the save person method
                saveMountain();
            }
        });
    }
    private void saveMountain(){
        String name = et_name.getText().toString().trim();
        String elevation = et_elevation.getText().toString().trim();
        String photo = et_photo.getText().toString().trim();
        String description = et_description.getText().toString().trim();
        String location = et_location.getText().toString().trim();
        dbHelper = new PetDBHelper(this);

        if(name.isEmpty()){
            //error name is empty
            Toast.makeText(this, "You must enter a name", Toast.LENGTH_SHORT).show();
        }

        if(elevation.isEmpty()){
            //error name is empty
            Toast.makeText(this, "You must enter an elevation", Toast.LENGTH_SHORT).show();
        }

        if(photo.isEmpty()){
            //error name is empty
            Toast.makeText(this, "You must enter an image link", Toast.LENGTH_SHORT).show();
        }

        if(description.isEmpty()){
            //error name is empty
            Toast.makeText(this, "You must enter a description", Toast.LENGTH_SHORT).show();
        }
        if(location.isEmpty()){
            //error name is empty
            Toast.makeText(this, "You must enter a location", Toast.LENGTH_SHORT).show();
        }

        //create new person
        Pet pet = new Pet(name, elevation, photo, description,location);
        dbHelper.saveNewMountain(pet);

        //finally redirect back home
        // NOTE you can implement an sqlite callback then redirect on success delete
        goBackHome();

    }

    private void goBackHome(){
        startActivity(new Intent(Idea_PetAddActivity.this, Idea_pet_Activity.class));
    }
}
