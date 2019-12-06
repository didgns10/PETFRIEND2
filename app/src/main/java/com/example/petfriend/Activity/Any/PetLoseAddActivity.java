package com.example.petfriend.Activity.Any;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.petfriend.Model.PetLose;
import com.example.petfriend.Model.PetLoseFireDBHelper;
import com.example.petfriend.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class PetLoseAddActivity extends AppCompatActivity {

    private EditText et_name;
    private EditText et_location;
    private EditText et_photo;
    private EditText et_link;
    private Button bt_add;
    private FirebaseUser user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_lose_add);

        user = FirebaseAuth.getInstance().getCurrentUser();

        et_name = (EditText)findViewById(R.id.et_add_name);
        et_photo = (EditText)findViewById(R.id.et_add_photo);
        et_link = (EditText)findViewById(R.id.et_add_link);
        et_location = (EditText)findViewById(R.id.et_add_location);
        bt_add = (Button)findViewById(R.id.bt_add);

        //listen to add button click
        bt_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //call the save person method
                //saveMountain();
                PetLose pet = new PetLose();
                pet.setTitle(et_name.getText().toString().trim());
                pet.setUrlToImage(et_photo.getText().toString().trim());
                pet.setUrl(et_link.getText().toString().trim());
                pet.setPlace(et_location.getText().toString().trim());
                new PetLoseFireDBHelper().addPetLose(pet, new PetLoseFireDBHelper.DataStatus() {
                    @Override
                    public void DataIsLoaded(ArrayList<PetLose> petlist, ArrayList<String> keys) {

                    }

                    @Override
                    public void DataIsInserted() {
                        Toast.makeText(PetLoseAddActivity.this,"유기견이 추가 되었습니다.",Toast.LENGTH_LONG).show();

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
        startActivity(new Intent(PetLoseAddActivity.this, PetLoseActivity.class));
        finish();
    }
}
