package com.example.petfriend.Adapter.Place;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
import com.example.petfriend.Model.Place;
import com.example.petfriend.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ItemPlaceAdapter extends RecyclerView.Adapter<ItemPlaceAdapter.ListViewHolder> implements Filterable {

    private ArrayList<Place> petlist;
    private ArrayList<Place> petlistfull;
    private Context context;
    private RecyclerView mRecyclerV;

    public ItemPlaceAdapter( Context context,ArrayList<Place> petlist) {
            this.petlist = petlist;
            this.context = context;;
             petlistfull = new ArrayList<>(petlist);
            }

    public void add(int position, Place place) {
        petlist.add(position, place);
        notifyItemInserted(position);
    }

    public void remove(int position) {
        petlist.remove(position);
        notifyItemRemoved(position);
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.item_idea_place,parent,false);

            ListViewHolder viewHolder = new ListViewHolder(view);
            return viewHolder;
            }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, final int position) {

            holder.title.setText(petlist.get(position).getName());
            holder.location.setText(petlist.get(position).getLocation());
            holder.category.setText(petlist.get(position).getCategory());

            Glide.with(context).load(petlist.get(position).getPhoto()).into(holder.photo);

            holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                        String url = petlist.get(position).getUrl();
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url) );
                        context.startActivity(intent);
                        }
                    });

            }

    @Override
    public int getItemCount() {
            return (null != petlist ? petlist.size() : 0);
            }

    @Override
    public Filter getFilter() {
            return petlistFilter;
            }
    private Filter petlistFilter = new Filter() {
    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
                ArrayList<Place> filteredList = new ArrayList<>();

                if(constraint == null || constraint.length() == 0){
                filteredList.addAll(petlistfull);
                }
                else{
                String filterPattern = constraint.toString().toLowerCase().trim();

                    for(Place item : petlistfull){
                        if(item.getCategory().toLowerCase().contains(filterPattern)){
                        filteredList.add(item);
                        }
                    }
                }
                FilterResults results = new FilterResults();
                results.values = filteredList;
                return  results;
            }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {
                petlist.clear();
                petlist.addAll((List)results.values);
                notifyDataSetChanged();

                }
            };

    public class ListViewHolder extends RecyclerView.ViewHolder {
        protected TextView title;
        protected TextView location;
        protected TextView category;
        protected ImageView photo;
        protected RelativeLayout relativeLayout;

        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tv_name_list);
            location = itemView.findViewById(R.id.tv_location_list);
            category = itemView.findViewById(R.id.tv_category_list);
            photo = itemView.findViewById(R.id.img_list);
            relativeLayout = itemView.findViewById(R.id.relative_layout);
        }
    }
}
