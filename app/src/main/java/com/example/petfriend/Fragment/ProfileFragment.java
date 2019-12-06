package com.example.petfriend.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.petfriend.Adapter.Post.MyFotoAdapter;
import com.example.petfriend.Model.MemberInfo;
import com.example.petfriend.Model.Post;
import com.example.petfriend.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class ProfileFragment extends Fragment {

    ImageView image_profile, options;
    TextView posts, followers, following, fullname, bio, username;
    Button edit_profile;

    FirebaseUser firebaseUser;
    String profileid;

    ImageButton my_fotos, saved_fotos;

    RecyclerView recyclerView;
    MyFotoAdapter myFotoAdapter;
    List<Post> postList;

    public ProfileFragment(){}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        SharedPreferences prefs = getContext().getSharedPreferences("PREFS", Context.MODE_PRIVATE);
        profileid = prefs.getString("profileid","none");

        image_profile = view.findViewById(R.id.image_profile);
        options = view.findViewById(R.id.options);
        posts = view.findViewById(R.id.posts);
        followers = view.findViewById(R.id.followers);
        following = view.findViewById(R.id.following);
        fullname = view.findViewById(R.id.fullname);
        bio = view.findViewById(R.id.bio);
        username = view.findViewById(R.id.username);
        edit_profile = view.findViewById(R.id.edit_profile);
        my_fotos = view.findViewById(R.id.my_fotos);
        saved_fotos = view.findViewById(R.id.saved_fotos);

        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new GridLayoutManager(getContext(), 3);
        recyclerView.setLayoutManager(linearLayoutManager);
        postList = new ArrayList<>();
        myFotoAdapter = new MyFotoAdapter(getContext(), postList);
        recyclerView.setAdapter(myFotoAdapter);


        userInfo();
        getFollowers();
        getNrPosts();
        myFotos();

        if(profileid.equals(firebaseUser.getUid())){
            edit_profile.setText("프로필 수정");
        }else{
            checkFollow();
            saved_fotos.setVisibility(View.GONE);
        }

        edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String btn = edit_profile.getText().toString();

                //버튼이 edit pofile 이면
                if(btn.equals("프로필 수정")){

                }else if(btn.equals("팔로우")){ //버튼이 follow 면
                    FirebaseDatabase.getInstance().getReference().child("Follow").child(firebaseUser.getUid())
                            .child("following").child(profileid).setValue(true);
                    FirebaseDatabase.getInstance().getReference().child("Follow").child(profileid)
                            .child("followers").child(firebaseUser.getUid()).setValue(true);

                }else if(btn.equals("팔로잉")){
                    FirebaseDatabase.getInstance().getReference().child("Follow").child(firebaseUser.getUid())
                            .child("following").child(profileid).removeValue();
                    FirebaseDatabase.getInstance().getReference().child("Follow").child(profileid)
                            .child("followers").child(firebaseUser.getUid()).removeValue();
                }
            }
        });

        return view;
    }

    private void userInfo(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users").child(profileid);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(getContext() == null){
                    return;
                }
                MemberInfo user = dataSnapshot.getValue(MemberInfo.class);

                Glide.with(getContext()).load(user.getPhotoUrl()).into(image_profile);
                username.setText(user.getNickname());
                fullname.setText(user.getNickname());
                bio.setText(user.getPhone_num());
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void checkFollow(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference()
                .child("Follow").child(firebaseUser.getUid()).child("following");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child(profileid).exists()){
                    edit_profile.setText("팔로잉");
                }else{
                    edit_profile.setText("팔로우");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void getFollowers(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference()
                .child("Follow").child(firebaseUser.getUid()).child("followers");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                followers.setText(""+dataSnapshot.getChildrenCount());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference()
                .child("Follow").child(firebaseUser.getUid()).child("following");
        reference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                following.setText(""+dataSnapshot.getChildrenCount());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private  void getNrPosts(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Posts");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int i=0;
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Post post = snapshot.getValue(Post.class);

                    //퍼플리셔가 내 아디랑 같으면 포스트 숫자를 더해준다.
                    if(post.getPublisher().equals(profileid)){
                        i++;
                    }
                }
                posts.setText(""+i);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void myFotos(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Posts");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){

                    //포스트 클래스에 불러온 데이터를 저장하고
                    Post post = snapshot.getValue(Post.class);
                    // 포스트의 퍼블리셔가 현재 내 아이디랑 같으면
                    if(post.getPublisher().equals(profileid)){
                        postList.add(post);
                    }
                }
                //데이터를 거꾸로 보여주는 방법
                Collections.reverse(postList);
                myFotoAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


}
