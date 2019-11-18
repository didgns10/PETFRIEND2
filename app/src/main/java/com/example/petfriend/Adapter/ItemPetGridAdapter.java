package com.example.petfriend.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.petfriend.Activity.Idea_pet_detail_Activity;
import com.example.petfriend.Model.Pet;
import com.example.petfriend.R;

import java.util.ArrayList;

public class ItemPetGridAdapter extends RecyclerView.Adapter<ItemPetGridAdapter.GridViewHolder> {

    private Context context;
    private ArrayList<Pet> listMountain;

    public ItemPetGridAdapter(Context context) {
        this.context = context;
    }

    public ArrayList<Pet> getListMountain() {
        return listMountain;
    }

    public void setListMountain(ArrayList<Pet> listMountain) {
        this.listMountain = listMountain;
    }

    @NonNull
    @Override
    public GridViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_idea_pet_grid,parent,false);
        return new GridViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GridViewHolder holder, final int position) {

        Glide.with(context).load(getListMountain().get(position).getPhoto()).into(holder.imgGrid);

        holder.imgGrid.setOnClickListener(new View.OnClickListener() {
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

    public class GridViewHolder extends RecyclerView.ViewHolder {

        ImageView imgGrid;

        public GridViewHolder(@NonNull View itemView) {
            super(itemView);
            imgGrid = itemView.findViewById(R.id.img_grid);
        }
    }
}
