package com.example.petfriend.Activity.Menu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.textclassifier.TextLinks;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.petfriend.Activity.Login.LoginActivity;
import com.example.petfriend.Activity.Login.SignupActivity;
import com.example.petfriend.Activity.MainActivity;
import com.example.petfriend.Adapter.YoutubeAdapter;
import com.example.petfriend.Model.VideoDetails;
import com.example.petfriend.R;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

//api key = AIzaSyDaAUIsNyhBmAPw2Ss6gVgFaRbA2Rk7gI8
public class YoutubeActivity extends AppCompatActivity
{

    private RecyclerView recyclerView;
    private ArrayList<VideoDetails> videoDetailsArrayList;
    private YoutubeAdapter youtubeAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private String url ="https://www.googleapis.com/youtube/v3/search?part=snippet&channelId=UCee1MvXr6E8qC_d2WEYTU5g&maxResults=15&key=AIzaSyDaAUIsNyhBmAPw2Ss6gVgFaRbA2Rk7gI8";

    private String API_KEY = "AIzaSyDaAUIsNyhBmAPw2Ss6gVgFaRbA2Rk7gI8";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube);
        recyclerView = findViewById(R.id.recyclerview);
        videoDetailsArrayList = new ArrayList<>();

        recyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        youtubeAdapter = new YoutubeAdapter(YoutubeActivity.this,videoDetailsArrayList);
        recyclerView.setLayoutManager(mLayoutManager);

        ImageButton button_back = (ImageButton) findViewById(R.id.imageButton_back);

        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(YoutubeActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        displayVideos();

    }

    private void displayVideos()
    {
        final RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response)
            {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("items");

                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        JSONObject jsonVideoId = jsonObject1.getJSONObject("id");
                        JSONObject jsonObjectSnippet = jsonObject1.getJSONObject("snippet");

                        JSONObject jsonObjectDefault = jsonObjectSnippet.getJSONObject("thumbnails").getJSONObject("medium");

                        String video_id = jsonVideoId.getString("videoId");

                        VideoDetails vd = new VideoDetails();

                        vd.setVideoId(video_id);
                        vd.setTitle(jsonObjectSnippet.getString("title"));
                        vd.setUrl(jsonObjectDefault.getString("url"));

                        videoDetailsArrayList.add(vd);
                    }
                    recyclerView.setAdapter(youtubeAdapter);
                    youtubeAdapter.notifyDataSetChanged();

                }catch (JSONException e){
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_SHORT).show();

            }
        });

        requestQueue.add(stringRequest);
    }
}
