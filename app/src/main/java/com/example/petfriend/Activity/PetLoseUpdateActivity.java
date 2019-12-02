package com.example.petfriend.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.petfriend.Activity.Idea.Idea_PetUpdateActivity;
import com.example.petfriend.Activity.Idea.Idea_pet_Activity;
import com.example.petfriend.Model.Pet;
import com.example.petfriend.Model.PetFireDBHelper;
import com.example.petfriend.Model.PetLose;
import com.example.petfriend.Model.PetLoseFireDBHelper;
import com.example.petfriend.R;

import java.util.ArrayList;

public class PetLoseUpdateActivity extends AppCompatActivity {

    private EditText et_name;
    private EditText et_location;
    private EditText et_photo;
    private EditText et_link;
    private Button bt_update;
    private long receivedPetId;

    private String key;
    private String name;
    private String link;
    private String location;
    private String img;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_lose_update);

        key = getIntent().getStringExtra("key");
        name = getIntent().getStringExtra("name");
        link = getIntent().getStringExtra("link");
        location = getIntent().getStringExtra("location");
        img = getIntent().getStringExtra("img");

        et_name = (EditText)findViewById(R.id.et_update_name);
        et_photo = (EditText)findViewById(R.id.et_update_photo);
        et_link = (EditText)findViewById(R.id.et_update_link);
        et_location = (EditText)findViewById(R.id.et_update_location);
        bt_update = (Button)findViewById(R.id.bt_update);

        et_name.setText(name);
        et_photo.setText(img);
        et_link.setText(link);
        et_location.setText(location);

        //listen to add button click
        bt_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //call the save person method
                // updatePet();
                PetLose pet = new PetLose();
                pet.setTitle(et_name.getText().toString().trim());
                pet.setUrlToImage(et_photo.getText().toString().trim());
                pet.setUrl(et_link.getText().toString().trim());
                pet.setPlace(et_location.getText().toString().trim());

                new PetLoseFireDBHelper().updatePetLose(key, pet, new PetLoseFireDBHelper.DataStatus() {
                    @Override
                    public void DataIsLoaded(ArrayList<PetLose> petlist, ArrayList<String> keys) {

                    }

                    @Override
                    public void DataIsInserted() {

                    }

                    @Override
                    public void DataIsUpdated() {
                        Toast.makeText(PetLoseUpdateActivity.this,"업데이트가 완료되었습니다.",Toast.LENGTH_LONG).show();
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
        startActivity(new Intent(PetLoseUpdateActivity.this, Idea_pet_Activity.class));
    }

}
