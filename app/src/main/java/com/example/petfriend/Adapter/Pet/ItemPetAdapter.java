package com.example.petfriend.Adapter.Pet;

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
import com.example.petfriend.Activity.Idea.Idea_PetUpdateActivity;
import com.example.petfriend.Model.Pet;
import com.example.petfriend.Model.PetFireDBHelper;
import com.example.petfriend.R;

import java.util.ArrayList;

public class ItemPetAdapter {
    private Context mContext;
    private PetItemDBAdaper petItemDBAdaper;

    public void setConfig(RecyclerView recyclerView, Context context, ArrayList<Pet> pets, ArrayList<String> keys){
        mContext = context;
        petItemDBAdaper = new PetItemDBAdaper(pets,keys);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(petItemDBAdaper);
    }

    class PetItemView extends RecyclerView.ViewHolder{
        TextView tvName, tvElevation, tvLocation, tvDescription;
        //이미지 텍스트인데 임시로 링크를 담기 위해 만들었다.
        TextView tvImg;

        ImageView imgList;
        private  String key;

        public PetItemView(ViewGroup parent){
            super(LayoutInflater.from(mContext).
                    inflate(R.layout.item_idea_pet,parent,false));

            tvName = itemView.findViewById(R.id.tv_name);
            tvElevation = itemView.findViewById(R.id.tv_elevation);
            tvLocation = itemView.findViewById(R.id.tv_location);
            tvDescription = itemView.findViewById(R.id.tv_description);
            tvImg = itemView.findViewById(R.id.tv_img);
            imgList = itemView.findViewById(R.id.img_list);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    builder.setTitle("옵션을 선택하세요.");
                    builder.setMessage("업데이트 또는 데이터 삭제를 하시겠습니까?");
                    builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(mContext, Idea_PetUpdateActivity.class);
                            intent.putExtra("key",key);
                            intent.putExtra("name",tvName.getText().toString());
                            intent.putExtra("elevation",tvElevation.getText().toString());
                            intent.putExtra("location",tvLocation.getText().toString());
                            intent.putExtra("description",tvDescription.getText().toString());
                            intent.putExtra("img", tvImg.getText().toString());
                            mContext.startActivity(intent);

                        }
                    });
                    builder.setNeutralButton("Delete", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            new PetFireDBHelper().deletePet(key, new PetFireDBHelper.DataStatus() {
                                @Override
                                public void DataIsLoaded(ArrayList<Pet> petlist, ArrayList<String> keys) {

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
        public void bind(Pet pet, String key){
            tvName.setText(pet.getName());
            tvElevation.setText(pet.getElevation());
            tvLocation.setText(pet.getLocation());
            tvDescription.setText(pet.getDescription());
            tvImg.setText(pet.getPhoto());
            Glide.with(mContext).load(pet.getPhoto()).into(imgList);
            this.key = key;
        }
    }
    class  PetItemDBAdaper extends  RecyclerView.Adapter<PetItemView>{
        private ArrayList<Pet>  listpet;
        private ArrayList<String> mkeys;

        public PetItemDBAdaper(ArrayList<Pet> listpet, ArrayList<String> mkeys) {
            this.listpet = listpet;
            this.mkeys = mkeys;
        }

        @NonNull
        @Override
        public PetItemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new PetItemView(parent);
        }

        @Override
        public void onBindViewHolder(@NonNull PetItemView holder, int position) {
            holder.bind(listpet.get(position), mkeys.get(position));
        }

        @Override
        public int getItemCount() {
            return listpet.size();
        }
    }
}
