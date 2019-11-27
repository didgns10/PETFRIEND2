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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class Idea_PetAddActivity extends AppCompatActivity {

    private EditText et_name;
    private EditText et_elevation;
    private EditText et_location;
    private EditText et_photo;
    private EditText et_description;
    private Button bt_add;
    private FirebaseUser user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_idea_pet_add);

        user = FirebaseAuth.getInstance().getCurrentUser();

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
                //saveMountain();
                Pet pet = new Pet();
                pet.setName(et_name.getText().toString().trim());
                pet.setElevation(et_elevation.getText().toString().trim());
                pet.setPhoto(et_photo.getText().toString().trim());
                pet.setDescription(et_description.getText().toString().trim());
                pet.setLocation(et_location.getText().toString().trim());
                pet.setUser(user.getUid());
                new PetFireDBHelper().addPet(pet, new PetFireDBHelper.DataStatus() {
                    @Override
                    public void DataIsLoaded(ArrayList<Pet> petlist, ArrayList<String> keys) {

                    }

                    @Override
                    public void DataIsInserted() {
                        Toast.makeText(Idea_PetAddActivity.this,"펫이 추가 되었습니다.",Toast.LENGTH_LONG).show();

                    }

                    @Override
                    public void DataIsUpdated() {

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
        startActivity(new Intent(Idea_PetAddActivity.this, Idea_pet_Activity.class));
        finish();
    }
}
