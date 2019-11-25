package com.example.petfriend.Adapter.Place;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.petfriend.Activity.Idea.Idea_PlaceUpdate_Activity;
import com.example.petfriend.Model.Place;
import com.example.petfriend.Model.PlaceFireDBHelper;
import com.example.petfriend.R;

import java.util.ArrayList;

public class ItemPlaceSetAdapter  {
    private Context mContext;
    private ItemPlaceAdapter itemPlaceAdapter;

    public void setConfig(RecyclerView recyclerView, Context context, ArrayList<Place> places, ArrayList<String> keys){
        mContext = context;
        itemPlaceAdapter = new ItemPlaceAdapter(places,keys);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(itemPlaceAdapter);
    }

    class PlaceItemView extends RecyclerView.ViewHolder{
        TextView title;
        TextView location;
        TextView category;
        TextView img;
        ImageView photo;
        TextView link;
        private String key;

        public PlaceItemView(ViewGroup parent){
            super(LayoutInflater.from(mContext).
                    inflate(R.layout.item_idea_place_set,parent,false));

            title = itemView.findViewById(R.id.tv_name_list);
            location = itemView.findViewById(R.id.tv_location_list);
            category = itemView.findViewById(R.id.tv_category_list);
            photo = itemView.findViewById(R.id.img_list);
            img = itemView.findViewById(R.id.tv_img);
            link = itemView.findViewById(R.id.tv_link_list);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    builder.setTitle("옵션을 선택하세요.");
                    builder.setMessage("업데이트 또는 데이터 삭제를 하시겠습니까?");
                    builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(mContext, Idea_PlaceUpdate_Activity.class);
                            intent.putExtra("key",key);
                            intent.putExtra("title",title.getText().toString());
                            intent.putExtra("location",location.getText().toString());
                            intent.putExtra("category",category.getText().toString());
                            intent.putExtra("photo",img.getText().toString());
                            intent.putExtra("link", link.getText().toString());
                            mContext.startActivity(intent);

                        }
                    });
                    builder.setNeutralButton("Delete", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            new PlaceFireDBHelper().deletePet(key, new PlaceFireDBHelper.DataStatus() {
                                @Override
                                public void DataIsLoaded(ArrayList<Place> places, ArrayList<String> keys) {

                                }

                                @Override
                                public void DataIsInserted() {

                                }

                                @Override
                                public void DataIsUpdated() {

                                }

                                @Override
                                public void DataIsDeleted() {

                                }
                            });

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
        public void bind(Place place, String key){
            title.setText(place.getName());
            location.setText(place.getLocation());
            category.setText(place.getCategory());
            img.setText(place.getPhoto());
            link.setText(place.getUrl());
            Glide.with(mContext).load(place.getPhoto()).into(photo);
            this.key = key;
        }
    }
    class  ItemPlaceAdapter extends  RecyclerView.Adapter<PlaceItemView>{
        private ArrayList<Place>  listplace;
        private ArrayList<String> mkeys;

        public ItemPlaceAdapter(ArrayList<Place> listplace, ArrayList<String> mkeys) {
            this.listplace = listplace;
            this.mkeys = mkeys;
        }

        @NonNull
        @Override
        public PlaceItemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new PlaceItemView(parent);
        }

        @Override
        public void onBindViewHolder(@NonNull PlaceItemView holder, int position) {
            holder.bind(listplace.get(position), mkeys.get(position));
        }

        @Override
        public int getItemCount() {
            return listplace.size();
        }
    }
}
