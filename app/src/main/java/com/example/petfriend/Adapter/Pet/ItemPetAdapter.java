package com.example.petfriend.Adapter.Pet;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import com.example.petfriend.Activity.Idea.Idea_PetUpdateActivity;
import com.example.petfriend.Model.Pet;
import com.example.petfriend.Model.PetDBHelper;
import com.example.petfriend.R;

import java.util.List;

public class ItemPetAdapter extends RecyclerView.Adapter<ItemPetAdapter.PetViewHolder> {

    private Context context;
    private List<Pet> listPet;
    private RecyclerView mRecyclerV;

    public ItemPetAdapter(Context context, List<Pet> listPet, RecyclerView mRecyclerV) {
        this.context = context;
        this.listPet = listPet;
        this.mRecyclerV = mRecyclerV;
    }

    public List<Pet> getListPet() {
        return listPet;
    }

    public void setListPet(List<Pet> listPet) {
        this.listPet = listPet;
    }
    public void add(int position, Pet pet) {
        listPet.add(position, pet);
        notifyItemInserted(position);
    }

    public void remove(int position) {
        listPet.remove(position);
        notifyItemRemoved(position);
    }

    public class PetViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvElevation, tvLocation, tvDescription;
        ImageView imgList;
        RelativeLayout relativeLayout;

        public PetViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
            tvElevation = itemView.findViewById(R.id.tv_elevation);
            tvLocation = itemView.findViewById(R.id.tv_location);
            tvDescription = itemView.findViewById(R.id.tv_description);
            imgList = itemView.findViewById(R.id.img_list);
            relativeLayout = itemView.findViewById(R.id.relative_layout);
        }
    }

    @NonNull
    @Override
    public PetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemList = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_idea_pet, parent, false);

        return new PetViewHolder(itemList);
    }

    @Override
    public void onBindViewHolder(@NonNull PetViewHolder holder,final int position) {

        final Pet pet = listPet.get(position);
        holder.tvName.setText(getListPet().get(position).getName());
        holder.tvElevation.setText(getListPet().get(position).getElevation());
        holder.tvLocation.setText(getListPet().get(position).getLocation());
        holder.tvDescription.setText(getListPet().get(position).getDescription());

        Glide.with(context).load(getListPet().get(position).getPhoto()).into(holder.imgList);

        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("옵션을 선택하세요");
                builder.setMessage("업데이트나 데이터 삭제를 하시겠습니까?");
                builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        //go to update activity
                        goToUpdateActivity(pet.getId());

                    }
                });
                builder.setNeutralButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        PetDBHelper dbHelper = new PetDBHelper(context);
                        dbHelper.deletePetRecord(pet.getId(), context);

                        listPet.remove(position);
                        mRecyclerV.removeViewAt(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, listPet.size());
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
        Intent goToUpdate = new Intent(context, Idea_PetUpdateActivity.class);
        goToUpdate.putExtra("USER_ID", personId);
        context.startActivity(goToUpdate);
    }

    @Override
    public int getItemCount() {
        return getListPet().size();
    }


}
