package com.example.petfriend.Adapter.Post;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.petfriend.Activity.MainActivity;
import com.example.petfriend.Activity.Post.CommentsActivity;
import com.example.petfriend.Model.Comment;
import com.example.petfriend.Model.MemberInfo;
import com.example.petfriend.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder>{

    private Context mContext;
    private List<Comment> mComment;

    private FirebaseUser firebaseUser;

    public CommentAdapter(Context mContext, List<Comment> mComment){
        this.mComment = mComment;
        this.mContext = mContext;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_comment, parent,false);

        return new CommentAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        final Comment comment = mComment.get(position);

        holder.comment.setText(comment.getComment());
        getUserInfo(holder.image_pofile, holder.username, comment.getPublisher());

        holder.time.setText("- "+formatTimeString(comment.getTime()));

        holder.comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mContext, MainActivity.class);
                intent.putExtra("publisherid", comment.getPublisher());
                mContext.startActivity(intent);
            }
        });

        holder.image_pofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mContext, MainActivity.class);
                intent.putExtra("publisherid", comment.getPublisher());
                mContext.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return mComment.size();
    }

    public class  ViewHolder extends RecyclerView.ViewHolder{

        public ImageView image_pofile;
        public TextView username, comment, time;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            image_pofile =itemView.findViewById(R.id.image_profile);
            username =itemView.findViewById(R.id.username);
            comment =itemView.findViewById(R.id.comment);
            time =itemView.findViewById(R.id.time);

        }
    }

    private void getUserInfo(final ImageView imageView, final TextView username, String publisherid){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("users").child(publisherid);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                MemberInfo user = dataSnapshot.getValue(MemberInfo.class);
                Glide.with(mContext).load(user.getPhotoUrl()).into(imageView);
                username.setText(user.getNickname());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public class TIME_MAXIMUM{
        public static final int SEC = 60;
        public static final int MIN = 60;
        public static final int HOUR = 24;
        public static final int DAY = 30;
        public static final int MONTH = 12;
    }
    public  String formatTimeString(long regTime) {
        long curTime = System.currentTimeMillis();
        long diffTime = (curTime - regTime) / 1000;
        String msg = null;
        if (diffTime < TIME_MAXIMUM.SEC) {
            msg = "방금 전";
        } else if ((diffTime /= TIME_MAXIMUM.SEC) < TIME_MAXIMUM.MIN) {
            msg = diffTime + "분 전";
        } else if ((diffTime /= TIME_MAXIMUM.MIN) < TIME_MAXIMUM.HOUR) {
            msg = (diffTime) + "시간 전";
        } else if ((diffTime /= TIME_MAXIMUM.HOUR) < TIME_MAXIMUM.DAY) {
            msg = (diffTime) + "일 전";
        } else if ((diffTime /= TIME_MAXIMUM.DAY) < TIME_MAXIMUM.MONTH) {
            msg = (diffTime) + "달 전";
        } else {
            msg = (diffTime) + "년 전";
        }
        return msg;
    }

}
