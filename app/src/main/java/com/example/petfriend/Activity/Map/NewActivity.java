package com.example.petfriend.Activity.Map;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.petfriend.R;

public class NewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);
        double latitude ;
        double longitude ;

        Bundle extras = getIntent().getExtras();

        latitude = extras.getDouble("latitude");
        longitude = extras.getDouble("longt");


        TextView textView = (TextView) findViewById(R.id.textView_newActivity_contentString);

        String str = ""+latitude + '\n' + longitude + '\n';
        textView.setText(str);

    }
}
