package com.example.petfriend.Adapter.Any;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.petfriend.Activity.Menu.YoutubeDetailsActivity;
import com.example.petfriend.Model.VideoDetails;
import com.example.petfriend.R;

import java.util.ArrayList;

public class YoutubeAdapter extends RecyclerView.Adapter<YoutubeAdapter.ViewHolder> {

    private Context mCotext;
    private ArrayList<VideoDetails> videoDetailsArrayList;

    public YoutubeAdapter(Context mCotext, ArrayList<VideoDetails> videoDetailsArrayList) {
        this.mCotext = mCotext;
        this.videoDetailsArrayList = videoDetailsArrayList;
    }

    @NonNull
    @Override
    public YoutubeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mCotext).inflate(R.layout.item_youtube,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull YoutubeAdapter.ViewHolder holder, final int position) {

        holder.TextView_title.setText(videoDetailsArrayList.get(position).getTitle());
        Glide.with(mCotext).load(videoDetailsArrayList.get(position).getUrl()).into(holder.ImageView_title);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mCotext, YoutubeDetailsActivity.class);
                i.putExtra("videoId",videoDetailsArrayList.get(position).getVideoId());
                mCotext.startActivity(i);

            }
        });

    }

    @Override
    public int getItemCount() {
        return videoDetailsArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView TextView_title;
        ImageView ImageView_title;
        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            TextView_title=itemView.findViewById(R.id.TextView_title);
            ImageView_title = itemView.findViewById(R.id.ImageView_title);
            cardView =itemView.findViewById(R.id.card_view);
        }
    }
}
