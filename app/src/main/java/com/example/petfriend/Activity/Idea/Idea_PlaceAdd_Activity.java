package com.example.petfriend.Activity.Idea;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.petfriend.Model.Place;
import com.example.petfriend.Model.PlaceFireDBHelper;
import com.example.petfriend.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class Idea_PlaceAdd_Activity extends AppCompatActivity {

    private EditText et_name;
    private EditText et_category;
    private EditText et_location;
    private EditText et_photo;
    private EditText et_link;
    private Button bt_add;
    private FirebaseUser user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_idea_place_add);

         user = FirebaseAuth.getInstance().getCurrentUser();

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
                //saveMountain();
                Place place = new Place();
                place.setName(et_name.getText().toString().trim());
                place.setCategory(et_category.getText().toString().trim());
                place.setPhoto(et_photo.getText().toString().trim());
                place.setUrl(et_link.getText().toString().trim());
                place.setLocation(et_location.getText().toString().trim());
                place.setUser(user.getUid());
                new PlaceFireDBHelper().addPlace(place, new PlaceFireDBHelper.DataStatus() {
                    @Override
                    public void DataIsLoaded(ArrayList<Place> places, ArrayList<String> keys) {

                    }

                    @Override
                    public void DataIsInserted() {
                        Toast.makeText(Idea_PlaceAdd_Activity.this,"핫플레이스가 추가 되었습니다.",Toast.LENGTH_LONG).show();

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
        startActivity(new Intent(Idea_PlaceAdd_Activity.this, Idea_place_set_Activity.class));
    }
}
