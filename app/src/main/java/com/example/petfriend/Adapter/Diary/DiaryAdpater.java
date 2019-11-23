package com.example.petfriend.Adapter.Diary;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.petfriend.Activity.Idea.Idea_PlaceUpdate_Activity;
import com.example.petfriend.Activity.Idea.Idea_pet_detail_Activity;
import com.example.petfriend.Activity.Menu.Diary_Update_Activity;
import com.example.petfriend.Activity.Menu.Diary_detail_Activity;
import com.example.petfriend.Model.Diary;
import com.example.petfriend.Model.DiaryDBHelper;
import com.example.petfriend.Model.Place;
import com.example.petfriend.Model.PlaceDBHelper;
import com.example.petfriend.R;

import java.util.List;

public class DiaryAdpater extends RecyclerView.Adapter<DiaryAdpater.diaryHolder> {
    private Context context;
    private List<Diary> diaryList;
    private RecyclerView mRecyclerV;

    public class diaryHolder extends RecyclerView.ViewHolder {
        public TextView di_title;
        public TextView di_time;
        public TextView di_content;
        public ImageView di_photo;
        public ImageButton bt_set;

        public View layout;

        public diaryHolder(@NonNull View v) {
            super(v);
            layout = v;
            di_title = (TextView) v.findViewById(R.id.tv_diary_title);
            di_time = (TextView) v.findViewById(R.id.tv_diary_time);
            di_content = (TextView) v.findViewById(R.id.tv_diary_content);
            di_photo = (ImageView) v.findViewById(R.id.img_card);
            bt_set = (ImageButton) v.findViewById(R.id.bt_diary_set);
        }
    }

    public void add(int position, Diary diary) {
        diaryList.add(position, diary);
        notifyItemInserted(position);
    }

    public void remove(int position) {
        diaryList.remove(position);
        notifyItemRemoved(position);
    }

    public DiaryAdpater(Context context, List<Diary> diary, RecyclerView mRecyclerV) {
        this.diaryList = diary;
        this.context = context;
        this.mRecyclerV = mRecyclerV;
    }

    @NonNull
    @Override
    public DiaryAdpater.diaryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(
                parent.getContext());
        View v =
                inflater.inflate(R.layout.item_diary, parent, false);
        // set the view's size, margins, paddings and layout parameters
        diaryHolder vh = new diaryHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(diaryHolder holder,final int position) {

        final Diary diary = diaryList.get(position);
        holder.di_title.setText(diaryList.get(position).getTitle());
        holder.di_time.setText(diaryList.get(position).getTime());
        holder.di_content.setText(diaryList.get(position).getContent());

        Glide.with(context).load(diaryList.get(position).getImage()).into(holder.di_photo);

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent detailActivity = new Intent(context, Diary_detail_Activity.class);
                detailActivity.putExtra(Diary_detail_Activity.EXTRA_DIARY, diaryList.get(position));
                context.startActivity(detailActivity);
            }
        });

        holder.bt_set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("옵션을 선택하세요.");
                builder.setMessage("업데이트 또는 데이터 삭제를 하시겠습니까??");
                builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        //go to update activity
                        goToUpdateActivity(diary.getId());

                    }
                });
                builder.setNeutralButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DiaryDBHelper dbHelper = new DiaryDBHelper(context);
                        dbHelper.deleteDiaryRecord(diary.getId(), context);

                        diaryList.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, diaryList.size());
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

    private void goToUpdateActivity(long diaryId){
        Intent goToUpdate = new Intent(context, Diary_Update_Activity.class);
        goToUpdate.putExtra("USER_ID", diaryId);
        context.startActivity(goToUpdate);
    }
    @Override
    public int getItemCount() {
            return (null != diaryList ? diaryList.size() : 0);
    }

}
