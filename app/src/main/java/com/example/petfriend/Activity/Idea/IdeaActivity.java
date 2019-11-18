package com.example.petfriend.Activity.Idea;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.petfriend.R;

public class IdeaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_idea);

        CardView cardView_place = (CardView)findViewById(R.id.cardView_place);
        cardView_place.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IdeaActivity.this, Idea_place_Acitivity.class);
                startActivity(intent);
            }
        });

        CardView cardView_pet = (CardView)findViewById(R.id.cardView_pet);
        cardView_pet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IdeaActivity.this, Idea_pet_Activity.class);
                startActivity(intent);
            }
        });
    }
}
