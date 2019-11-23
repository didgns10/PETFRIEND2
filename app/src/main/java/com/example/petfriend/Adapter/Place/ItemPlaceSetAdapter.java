package com.example.petfriend.Adapter.Place;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.petfriend.Activity.Idea.Idea_PlaceUpdate_Activity;
import com.example.petfriend.Model.Place;
import com.example.petfriend.Model.PlaceDBHelper;
import com.example.petfriend.R;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ItemPlaceSetAdapter extends RecyclerView.Adapter<ItemPlaceSetAdapter.PlaceViewHolder> {

    private List<Place> placeList;
    private RecyclerView mRecyclerV;
    private Context context;

    public ItemPlaceSetAdapter(Context context, List<Place> petlist, RecyclerView mRecyclerV) {
        this.placeList = petlist;
        this.context = context;
        this.mRecyclerV = mRecyclerV;
    }
    public void add(int position, Place place) {
        placeList.add(position, place);
        notifyItemInserted(position);
    }

    public List<Place> getPlaceList() {
        return placeList;
    }

    public void setPlaceList(List<Place> placeList) {
        this.placeList = placeList;
    }

    public void remove(int position) {
        placeList.remove(position);
        notifyItemRemoved(position);
    }


    @NonNull
    @Override
    public PlaceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_idea_place_set,parent,false);

        return  new PlaceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlaceViewHolder holder,final int position) {
        final Place place = placeList.get(position);
        holder.title.setText(getPlaceList().get(position).getName());
        holder.location.setText(getPlaceList().get(position).getLocation());
        holder.category.setText(getPlaceList().get(position).getCategory());
        holder.link.setText(getPlaceList().get(position).getUrl());

        Glide.with(context).load(getPlaceList().get(position).getPhoto()).into(holder.photo);

        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("옵션을 선택하세요.");
                builder.setMessage("업데이트 또는 데이터 삭제를 하시겠습니까??");
                builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        //go to update activity
                        goToUpdateActivity(place.getId());

                    }
                });
                builder.setNeutralButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        PlaceDBHelper dbHelper = new PlaceDBHelper(context);
                        dbHelper.deletePlaceRecord(place.getId(), context);

                        placeList.remove(position);
                   //     mRecyclerV.removeViewAt(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, placeList.size());
                        notifyDataSetChanged();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.create().show();
            }
        });

    }




    private void goToUpdateActivity(long personId){
        Intent goToUpdate = new Intent(context, Idea_PlaceUpdate_Activity.class);
        goToUpdate.putExtra("USER_ID", personId);
        context.startActivity(goToUpdate);
    }

    @Override
    public int getItemCount() {
        return (null != placeList ? placeList.size() : 0);
    }

    public class PlaceViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView location;
        TextView category;
        ImageView photo;
        TextView link;
        RelativeLayout relativeLayout;

        public PlaceViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tv_name_list);
            location = itemView.findViewById(R.id.tv_location_list);
            category = itemView.findViewById(R.id.tv_category_list);
            photo = itemView.findViewById(R.id.img_list);
            link = itemView.findViewById(R.id.tv_link_list);
            relativeLayout = itemView.findViewById(R.id.relative_layout);
        }
    }
}
