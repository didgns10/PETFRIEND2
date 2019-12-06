package com.example.petfriend.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.petfriend.Model.GameScore;
import com.example.petfriend.Model.MemberInfo;
import com.example.petfriend.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class GameAdapter extends RecyclerView.Adapter<GameAdapter.ViewHolder> {

    private Context mContext;
    private List<GameScore> mGameScore;

    private FirebaseUser firebaseUser;

    public GameAdapter(Context mContext, List<GameScore> mGameScore) {
        this.mContext = mContext;
        this.mGameScore = mGameScore;
    }

    @NonNull
    @Override
    public GameAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.item_gamerank,parent,false);

        return new GameAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GameAdapter.ViewHolder holder, int position) {

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        final GameScore gameScore = mGameScore.get(position);

       getUserInfo(holder.imageView_profile,holder.username, gameScore.getPublisher());

       //전체 사이즈에서 현재 포지션을 빼서 순위를 매겼다 왜냐하면 엑티비티에서 데이터 쿼리가 오름차순이여서 이거를 역순으로 하고 싶어서
        //리사이클러뷰를 역순으로 바꿨다. 그렇기 때문에 이작업이 필요하다.
        holder.tv_rank.setText(getItemCount()- position+"");

       holder.score.setText(gameScore.getScore()+"");

    }

    @Override
    public int getItemCount() {
        return mGameScore.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView imageView_profile;
        public TextView username, score , tv_rank;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView_profile = itemView.findViewById(R.id.img_list);
            username = itemView.findViewById(R.id.tv_name);
            score = itemView.findViewById(R.id.tv_score);
            tv_rank = itemView.findViewById(R.id.tv_rank);
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
}
