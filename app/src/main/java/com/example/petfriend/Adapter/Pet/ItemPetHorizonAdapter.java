package com.example.petfriend.Adapter.Pet;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.petfriend.Activity.Idea.Idea_pet_detail_Activity;
import com.example.petfriend.Model.Pet;
import com.example.petfriend.R;

import java.util.ArrayList;
import java.util.List;

public class ItemPetHorizonAdapter extends RecyclerView.Adapter<ItemPetHorizonAdapter.HorViewHolder> {

    private Context context;
    private List<Pet> listMountain;
    private RecyclerView mRecyclerV;

    public ItemPetHorizonAdapter(Context context, List<Pet> listMountain, RecyclerView mRecyclerV) {
        this.context = context;
        this.listMountain = listMountain;
        this.mRecyclerV = mRecyclerV;
    }

    public List<Pet> getListMountain() {
        return listMountain;
    }

    public void setListMountain(List<Pet> listMountain) {
        this.listMountain = listMountain;
    }



    @NonNull
    @Override
    public HorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_idea_pet_list_hor,parent,false);
        return new HorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HorViewHolder holder, final int position) {

        holder.tvName.setText(getListMountain().get(position).getName());
        holder.tvElevation.setText(getListMountain().get(position).getElevation());
        holder.tvCountry.setText(getListMountain().get(position).getLocation());

        Glide.with(context).load(getListMountain().get(position).getPhoto()).into(holder.imgPhoto);

        holder.imgPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent detailActivity = new Intent(context, Idea_pet_detail_Activity.class);
                detailActivity.putExtra(Idea_pet_detail_Activity.EXTRA_MOUNTAIN, listMountain.get(position));
                context.startActivity(detailActivity);
            }
        });
    }

    @Override
    public int getItemCount() {
        return getListMountain().size();
    }

    public class HorViewHolder extends RecyclerView.ViewHolder {

        ImageView imgPhoto;
        TextView tvName,tvCountry,tvElevation;
        RelativeLayout relativeLayout;


        public HorViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPhoto = itemView.findViewById(R.id.img_hor);
            tvName = itemView.findViewById(R.id.tv_hor_name);
            tvCountry = itemView.findViewById(R.id.tv_hor_country);
            tvElevation = itemView.findViewById(R.id.tv_hor_elevation);


        }
    }
}