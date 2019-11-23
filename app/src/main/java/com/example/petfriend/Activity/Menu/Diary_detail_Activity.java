package com.example.petfriend.Activity.Menu;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.petfriend.Model.Diary;
import com.example.petfriend.Model.Pet;
import com.example.petfriend.R;

public class Diary_detail_Activity extends AppCompatActivity {

    public static final String EXTRA_DIARY = "extra_diary";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary_detail);

        ImageView imageView = findViewById(R.id.img_detail);
        TextView title = findViewById(R.id.tv_title_detail);
        TextView time = findViewById(R.id.tv_time_detail);
        TextView content = findViewById(R.id.tv_content_detail);

        Diary diary = getIntent().getParcelableExtra(EXTRA_DIARY);

        Glide.with(this).load(diary.getImage()).into(imageView);
        title.setText(diary.getTitle());
        time.setText(diary.getTime());
        content.setText(diary.getContent());

    }
}
