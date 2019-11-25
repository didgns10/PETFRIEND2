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
import com.example.petfriend.R;

import java.util.ArrayList;

public class Idea_PetUpdateActivity extends AppCompatActivity {

    private EditText et_name;
    private EditText et_elevation;
    private EditText et_location;
    private EditText et_photo;
    private EditText et_description;
    private Button bt_update;
    private long receivedPetId;

    private String key;
    private String name;
    private String elevation;
    private String location;
    private String description;
    private String img;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_idea_pet_update);

        key = getIntent().getStringExtra("key");
        name = getIntent().getStringExtra("name");
        elevation = getIntent().getStringExtra("elevation");
        location = getIntent().getStringExtra("location");
        description = getIntent().getStringExtra("description");
        img = getIntent().getStringExtra("img");

        et_name = (EditText)findViewById(R.id.et_update_name);
        et_elevation = (EditText)findViewById(R.id.et_update_elevation);
        et_photo = (EditText)findViewById(R.id.et_update_photo);
        et_description = (EditText)findViewById(R.id.et_update_description);
        et_location = (EditText)findViewById(R.id.et_update_location);
        bt_update = (Button)findViewById(R.id.bt_update);

        et_name.setText(name);
        et_elevation.setText(elevation);
        et_photo.setText(img);
        et_description.setText(description);
        et_location.setText(location);

        //listen to add button click
        bt_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //call the save person method
               // updatePet();
                Pet pet = new Pet();
                pet.setName(et_name.getText().toString().trim());
                pet.setElevation(et_elevation.getText().toString().trim());
                pet.setPhoto(et_photo.getText().toString().trim());
                pet.setDescription(et_description.getText().toString().trim());
                pet.setLocation(et_location.getText().toString().trim());

                new PetFireDBHelper().updatePet(key, pet, new PetFireDBHelper.DataStatus() {
                    @Override
                    public void DataIsLoaded(ArrayList<Pet> petlist, ArrayList<String> keys) {

                    }

                    @Override
                    public void DataIsInserted() {

                    }

                    @Override
                    public void DataIsUpdated() {
                        Toast.makeText(Idea_PetUpdateActivity.this,"업데이트가 완료되었습니다.",Toast.LENGTH_LONG).show();
                        finish();

                    }

                    @Override
                    public void DataIsDeleted() {

                    }
                });


            }
        });

    }


    private void goBackHome(){
        startActivity(new Intent(Idea_PetUpdateActivity.this, Idea_pet_Activity.class));
    }

}
