package com.example.petfriend.Adapter.Pet;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.petfriend.Activity.Idea.Idea_pet_detail_Activity;
import com.example.petfriend.Model.Pet;
import com.example.petfriend.R;

import java.util.ArrayList;
import java.util.List;

public class ItemPetCardViewAdapter extends RecyclerView.Adapter<ItemPetCardViewAdapter.CardViewHolder> {

    private Context context;
    private ArrayList<Pet> listMountain;
    private RecyclerView mRecyclerV;

    public ItemPetCardViewAdapter(Context context, ArrayList<Pet> listMountain) {
        this.context = context;
        this.listMountain = listMountain;
    }

    public List<Pet> getListMountain() {
        return listMountain;
    }

    public void setListMountain(ArrayList<Pet> listMountain) {
        this.listMountain = listMountain;
    }

    @NonNull
    @Override
    public ItemPetCardViewAdapter.CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_idea_pet_cardview,parent,false);
        return new CardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemPetCardViewAdapter.CardViewHolder holder, final int position) {
        holder.tvName.setText(getListMountain().get(position).getName());
        holder.tvDescription.setText(getListMountain().get(position).getDescription());
        Glide.with(context).load(getListMountain().get(position).getPhoto()).into(holder.imgPhoto);

        // intent parcel able to detail
        holder.button.setOnClickListener(new View.OnClickListener() {
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

    public class CardViewHolder extends RecyclerView.ViewHolder {
        ImageView imgPhoto;
        TextView tvName,tvDescription;
        Button button;

        public CardViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPhoto = itemView.findViewById(R.id.img_card);
            tvName = itemView.findViewById(R.id.tv_name_card);
            tvDescription = itemView.findViewById(R.id.tv_desc_card);
            button = itemView.findViewById(R.id.button);
        }
    }
}


