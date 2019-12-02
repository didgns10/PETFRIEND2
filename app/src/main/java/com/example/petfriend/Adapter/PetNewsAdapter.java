package com.example.petfriend.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.petfriend.Model.Newsdata;
import com.example.petfriend.R;

import java.util.ArrayList;

public class PetNewsAdapter  extends RecyclerView.Adapter<PetNewsAdapter.ViewHolder> {

    //데이터 배열 선언
    private ArrayList<Newsdata> mList;
    private Context mcontext;

    public  class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView_img;
        private TextView textView_title;
        private CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);

            imageView_img = (ImageView) itemView.findViewById(R.id.ImageView_title);
            textView_title = (TextView) itemView.findViewById(R.id.TextView_title);
            cardView = (CardView)itemView.findViewById(R.id.card_view);
        }
    }

    //생성자
    public PetNewsAdapter(ArrayList<Newsdata> list, Context context) {
        this.mList = list;
        this.mcontext = context;
    }

    @NonNull
    @Override
    public PetNewsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PetNewsAdapter.ViewHolder holder, final int position) {

        holder.textView_title.setText(String.valueOf(mList.get(position).getTitle()));
        //다 해줬는데도 GlideApp 에러가 나면 rebuild project를 해주자.
        Glide.with(mcontext).load(mList.get(position).getUrlToImage()).into(holder.imageView_img);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = mList.get(position).getUrl();
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url) );
                mcontext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}