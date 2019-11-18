package com.example.petfriend.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.petfriend.Model.Pet;
import com.example.petfriend.R;

public class Idea_pet_detail_Activity extends AppCompatActivity {


    public static final String EXTRA_MOUNTAIN = "extra_mountain";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_idea_pet_detail);

        ImageView imageView = findViewById(R.id.img_detail);
        TextView name = findViewById(R.id.tv_name_detail);
        TextView description = findViewById(R.id.tv_desc_detail);
        TextView elevation = findViewById(R.id.tv_elevation_detail);
        TextView country = findViewById(R.id.tv_country_detail);

        Pet mountain = getIntent().getParcelableExtra(EXTRA_MOUNTAIN);


        Glide.with(this).load(mountain.getPhoto()).into(imageView);
        name.setText(mountain.getName());
        description.setText(mountain.getDescription());
        elevation.setText(mountain.getElevation());
        country.setText(mountain.getLocation());

    }
}
