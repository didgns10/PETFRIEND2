package com.example.petfriend.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.petfriend.Activity.Idea.Idea_pet_detail_Activity;
import com.example.petfriend.Model.Pet;
import com.example.petfriend.R;

import java.util.ArrayList;

public class ItemPetListAdapter extends RecyclerView.Adapter<ItemPetListAdapter.ListViewHolder> {

    private Context context;
    private ArrayList<Pet> listMountain;

    public class ListViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvElevation;
        ImageView imgList;
        RelativeLayout relativeLayout;

        public ListViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name_list);
            tvElevation = itemView.findViewById(R.id.tv_elevation_list);
            imgList = itemView.findViewById(R.id.img_list);
            relativeLayout = itemView.findViewById(R.id.relative_layout);
        }
    }

    public ItemPetListAdapter(Context context) {
        this.context = context;
    }

    public ArrayList<Pet> getListMountain() {
        return listMountain;
    }

    public void setListMountain(ArrayList<Pet> listMountain) {
        this.listMountain = listMountain;
    }


    @Override
    public ItemPetListAdapter.ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemList = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_idea_pet_list, parent, false);

        return new ListViewHolder(itemList);
    }

    @Override
    public void onBindViewHolder(ListViewHolder holder, final int position) {

        holder.tvName.setText(getListMountain().get(position).getName());
        holder.tvElevation.setText(getListMountain().get(position).getElevation());

        Glide.with(context).load(getListMountain().get(position).getPhoto()).into(holder.imgList);
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
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

}

