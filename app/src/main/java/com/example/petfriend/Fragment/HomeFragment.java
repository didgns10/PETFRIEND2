package com.example.petfriend.Fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.petfriend.Activity.MainActivity;
import com.example.petfriend.Adapter.NewsAdpter;
import com.example.petfriend.Model.Newsdata;
import com.example.petfriend.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private RecyclerView mRrecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    RequestQueue queue ;
    List<Newsdata> news;
    Newsdata newsdata;
    Handler handler = new Handler();
    private TextView textView;
    private int index;
    private int newdata =0;
    Thread t;
    private  Animation animTransUp;

    public HomeFragment(){

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_home, container, false);

        mRrecyclerView = (RecyclerView) rootView.findViewById(R.id.my_recycler_view);
        mRrecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRrecyclerView.setLayoutManager(mLayoutManager);

        textView = (TextView)rootView.findViewById(R.id.tv_news_title);
        queue = Volley.newRequestQueue(getActivity());
        getNews();

        animTransUp = AnimationUtils.loadAnimation(getActivity(),R.anim.anim_traslate_up);

        return rootView;

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
                             news = new ArrayList<>();

                            for(int i = 0, j = arrayarticles.length(); i < j ; i++ ){

                                JSONObject obj = arrayarticles.getJSONObject(i);

                                Log.d("NEWS",obj.toString());

                                newsdata = new Newsdata();
                                newsdata.setTitle(obj.getString("title"));
                                newsdata.setUrlToImage(obj.getString("urlToImage"));
                                newsdata.setContent(obj.getString("description"));
                                newsdata.setUrl(obj.getString("url"));
                                news.add(newsdata);
                            }

                            // specify an adapter (see also next example)
                            mAdapter = new NewsAdpter(news, getActivity(), new View.OnClickListener() {
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

                            if(news != null) {
                                final int[] i = {0};
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        while (true) {
                                            try {
                                                if(getActivity() != null) {
                                                    getActivity().runOnUiThread(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            textView.setVisibility(View.VISIBLE);
                                                            textView.setText(news.get(i[0]).getTitle());
                                                            textView.startAnimation(animTransUp);
                                                            textView.setOnClickListener(new View.OnClickListener() {
                                                                @Override
                                                                public void onClick(View v) {
                                                                    String urll = (news.get(i[0] - 1).getUrl());
                                                                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(urll));
                                                                    startActivity(intent);
                                                                }
                                                            });
                                                            Log.e("로그", "로그1");
                                                            i[0]++;
                                                            if (news.size() == i[0]) {
                                                                i[0] = 0;
                                                            }
                                                        }
                                                    });
                                                }
                                                Thread.sleep(3000);
                                            } catch (InterruptedException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }
                                }).start();
                            }

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
    public class AnimThread extends Thread {
        @Override
        public void run() {
            index = 0;
            while (true) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        textView.setText(news.get(index).getTitle());
                    }
                });
                index++;
              /*  if(index == news.size()){
                    index = 0;
                }*/
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
