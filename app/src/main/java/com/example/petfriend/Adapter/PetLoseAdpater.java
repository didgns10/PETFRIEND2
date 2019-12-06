package com.example.petfriend.Adapter;

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
import com.example.petfriend.Activity.Any.PetLoseUpdateActivity;
import com.example.petfriend.Model.PetLose;
import com.example.petfriend.Model.PetLoseFireDBHelper;
import com.example.petfriend.R;

import java.util.ArrayList;

public class PetLoseAdpater {
    private Context mContext;
    private PetLoseDBAdpater petItemDBAdaper;

    public void setConfig(RecyclerView recyclerView, Context context, ArrayList<PetLose> pets, ArrayList<String> keys){
        mContext = context;
        petItemDBAdaper = new PetLoseDBAdpater(pets,keys);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(petItemDBAdaper);
    }

    class PetLoseItemView extends RecyclerView.ViewHolder{
        TextView tvName, tvlink, tvLocation;
        //이미지 텍스트인데 임시로 링크를 담기 위해 만들었다.
        TextView tvImg;

        ImageView imgList;
        private  String key;

        public PetLoseItemView(ViewGroup parent){
            super(LayoutInflater.from(mContext).
                    inflate(R.layout.item_petlose,parent,false));

            tvName = itemView.findViewById(R.id.tv_name);
            tvLocation = itemView.findViewById(R.id.tv_location);
            tvImg = itemView.findViewById(R.id.tv_img); //이미지링크
            imgList = itemView.findViewById(R.id.img_list);
            tvlink = itemView.findViewById(R.id.tv_link);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    builder.setTitle("옵션을 선택하세요.");
                    builder.setMessage("업데이트 또는 데이터 삭제를 하시겠습니까?");
                    builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(mContext, PetLoseUpdateActivity.class);
                            intent.putExtra("key",key);
                            intent.putExtra("name",tvName.getText().toString());
                            intent.putExtra("location",tvLocation.getText().toString());
                            intent.putExtra("link",tvlink.getText().toString());
                            intent.putExtra("img", tvImg.getText().toString());
                            mContext.startActivity(intent);

                        }
                    });
                    builder.setNeutralButton("Delete", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            new PetLoseFireDBHelper().deletePetLose(key, new PetLoseFireDBHelper.DataStatus() {
                                @Override
                                public void DataIsLoaded(ArrayList<PetLose> petlist, ArrayList<String> keys) {

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
        public void bind(PetLose pet, String key){
            tvName.setText(pet.getTitle());
            tvLocation.setText(pet.getPlace());
            tvImg.setText(pet.getUrlToImage());
            tvlink.setText(pet.getUrl());
            Glide.with(mContext).load(pet.getUrlToImage()).into(imgList);
            this.key = key;
        }
    }
    class PetLoseDBAdpater extends  RecyclerView.Adapter<PetLoseItemView>{
        private ArrayList<PetLose>  listpet;
        private ArrayList<String> mkeys;

        public PetLoseDBAdpater(ArrayList<PetLose> listpet, ArrayList<String> mkeys) {
            this.listpet = listpet;
            this.mkeys = mkeys;
        }

        @NonNull
        @Override
        public PetLoseItemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new PetLoseItemView(parent);
        }

        @Override
        public void onBindViewHolder(@NonNull PetLoseItemView holder, int position) {
            holder.bind(listpet.get(position), mkeys.get(position));
        }

        @Override
        public int getItemCount() {
            return listpet.size();
        }
    }
}
