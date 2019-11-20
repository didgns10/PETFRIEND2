package com.example.petfriend.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.PopupMenu;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.petfriend.Activity.Idea.IdeaActivity;
import com.example.petfriend.Activity.Login.LoginActivity;
import com.example.petfriend.Activity.Login.Signup_MemberActivity;
import com.example.petfriend.Activity.Menu.likeActivity;
import com.example.petfriend.Activity.Menu.myprofileActivity;
import com.example.petfriend.Activity.Menu.optionActivity;
import com.example.petfriend.Activity.Newspeed.newspeedActivity;
import com.example.petfriend.Activity.Newspeed.newspeed_photo_selectActivity;
import com.example.petfriend.Adapter.NewsAdpter;
import com.example.petfriend.Model.Newsdata;
import com.example.petfriend.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private FirebaseAuth mAuth;
    RequestQueue queue ;
    private RecyclerView mRrecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<Newsdata> myDataset ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

       if(FirebaseAuth.getInstance().getCurrentUser() == null) {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }else {
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            DocumentReference docRef = db.collection("users").document(user.getUid());
            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if(document != null) {
                            if (document.exists()) {
                                Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                            } else {
                                Log.d(TAG, "No such document");
                                Intent intent = new Intent(MainActivity.this, Signup_MemberActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }
                    } else {
                        Log.d(TAG, "get failed with ", task.getException());
                    }

                }
            });
        }
        ImageButton button_menu = (ImageButton) findViewById(R.id.imageButton_menu);
        ImageButton button_newspeed = (ImageButton) findViewById(R.id.imageButton_newspeed);
        ImageButton button_idea = (ImageButton) findViewById(R.id.imageButton_idea);
        ImageButton button_notice = (ImageButton) findViewById(R.id.imageButton_notice);
        FloatingActionButton button_chat = (FloatingActionButton) findViewById(R.id.float_chat);
        ImageButton button_serach = (ImageButton) findViewById(R.id.imageButton_search);

        button_newspeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent4 = new Intent(MainActivity.this, newspeedActivity.class);
                intent4.addFlags (Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent4);

            }
        });
        // 정보주는 이미지 클릭했을때
        button_idea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent4 = new Intent(MainActivity.this, IdeaActivity.class);
                intent4.addFlags (Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent4);

            }
        });



        button_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popup= new PopupMenu(getApplicationContext(), view);//v는 클릭된 뷰를 의미

                getMenuInflater().inflate(R.menu.menu, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.like_baguni:
                                Intent intent = new Intent(MainActivity.this, likeActivity.class);
                                startActivity(intent);
                                break;
                            case R.id.my_profile:
                                Intent intent1 = new Intent(MainActivity.this, myprofileActivity.class);
                                startActivity(intent1);
                                break;
                            case R.id.option:
                                Intent intent2 = new Intent(MainActivity.this, optionActivity.class);
                                startActivity(intent2);
                            default:
                                break;
                        }
                        return false;
                    }
                });
                popup.show();//Popup Menu 보이기
            }

        });
        mRrecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mRrecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        mRrecyclerView.setLayoutManager(mLayoutManager);

        queue = Volley.newRequestQueue(this);
        getNews();
    }


    public void image_like4(View view) {

                Intent intent = new Intent(MainActivity.this, newspeed_photo_selectActivity.class);
                startActivity(intent);
    }

    public void getNews() {

        String url ="\n" +
                "https://newsapi.org/v2/everything?q=apple&from=2019-11-12&to=2019-11-12&sortBy=popularity&apiKey=ae092d0c38db47cfb48eedffd9386524";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d("NEWS",response);

                        try {
                            JSONObject jsonObj = new JSONObject(response);

                            JSONArray arrayarticles = jsonObj.getJSONArray("articles");

                            //response -> NewsData 클래스 에다가 분류
                            List<Newsdata> news = new ArrayList<>();

                            for(int i = 0, j = arrayarticles.length(); i < j ; i++ ){

                                JSONObject obj = arrayarticles.getJSONObject(i);

                                Log.d("NEWS",obj.toString());

                                Newsdata newsdata = new Newsdata();
                                newsdata.setTitle(obj.getString("title"));
                                newsdata.setUrlToImage(obj.getString("urlToImage"));
                                newsdata.setContent(obj.getString("description"));
                                newsdata.setUrl(obj.getString("url"));
                                news.add(newsdata);
                            }

                            // specify an adapter (see also next example)
                            mAdapter = new NewsAdpter(news, MainActivity.this, new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if(v.getTag() != null){
                                        int positon =  (int) v.getTag();
                                        String urll = ((NewsAdpter) mAdapter).getNews(positon).getUrl();
                                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(urll));
                                        startActivity(intent);
                                    }
                                }
                            });
                            mRrecyclerView.setAdapter(mAdapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }
}

