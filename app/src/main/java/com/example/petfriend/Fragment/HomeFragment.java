package com.example.petfriend.Fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
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



        queue = Volley.newRequestQueue(getActivity());
        getNews();
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
