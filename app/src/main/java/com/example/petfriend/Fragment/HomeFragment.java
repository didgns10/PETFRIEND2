package com.example.petfriend.Fragment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.petfriend.Activity.Game.GameActivity;
import com.example.petfriend.Activity.Idea.IdeaActivity;
import com.example.petfriend.Activity.Map.GoolgleMapActivity;
import com.example.petfriend.Activity.Menu.Diary_Activity;
import com.example.petfriend.Activity.Any.NewsActivity;
import com.example.petfriend.Activity.Any.PetLoseActivity;
import com.example.petfriend.Adapter.WeatherAdapter;
import com.example.petfriend.Model.Gloval;
import com.example.petfriend.Model.Newsdata;
import com.example.petfriend.Model.PetLose;
import com.example.petfriend.Model.PetLoseFireDBHelper;
import com.example.petfriend.Model.Weather;
import com.example.petfriend.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class HomeFragment extends Fragment {

    private RecyclerView mRrecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    RequestQueue queue ;
    Newsdata newsdata;
    Handler handler = new Handler();
    private TextView textView;
    private int index;
    private int newdata =0;
    Thread t;
    private  Animation animTransUp;
    private ArrayList<Newsdata> list = new ArrayList();
    private ArrayList<PetLose> petLoses = new ArrayList();
    private FirebaseUser firebaseUser;
    private TextView tv_dog_place;
    private TextView tv_dog_title;
    private ImageView img_dog;
    private LinearLayout layout;
    private ImageButton img_googlemap;
    private double lat ;
    private double lon ;

    public HomeFragment(){

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_home, container, false);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        mRrecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);

        img_googlemap = rootView.findViewById(R.id.img_googlemap);
        layout = (LinearLayout) rootView.findViewById(R.id.layout_game);
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), GameActivity.class);
                startActivity(intent);
            }
        });

        textView = (TextView) rootView.findViewById(R.id.tv_news_title);
        animTransUp = AnimationUtils.loadAnimation(getActivity(), R.anim.anim_traslate_up);

        img_dog = (ImageView) rootView.findViewById(R.id.img_dog);
        tv_dog_title = (TextView) rootView.findViewById(R.id.tv_dog_title);
        tv_dog_place = (TextView) rootView.findViewById(R.id.tv_dog_place);
        layout = (LinearLayout) rootView.findViewById(R.id.layout);

        mRrecyclerView.setHasFixedSize(true);


        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getContext());
        mRrecyclerView.setLayoutManager(mLayoutManager);

        queue = Volley.newRequestQueue(getContext());

        if (Gloval.getState() != null) {
            lat = Gloval.getLatitude();
            lon = Gloval.getLongitude();
        } else {
            lat = 37.57;
            lon = 126.98;
        }
        img_googlemap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), GoolgleMapActivity.class);
                startActivity(intent);
            }
        });
        getWeather();
        showPet();
        new Description().execute();

        return rootView;

    }
    private void showPet() {
        new PetLoseFireDBHelper().readPetLose(new PetLoseFireDBHelper.DataStatus() {
            @Override
            public void DataIsLoaded(final ArrayList<PetLose> petlist, ArrayList<String> keys) {
                final int[] i = {0};
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while (true) {
                            try {
                                if (getActivity() != null) {
                                    getActivity().runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            img_dog.setVisibility(View.VISIBLE);
                                            tv_dog_title.setVisibility(View.VISIBLE);
                                            tv_dog_place.setVisibility(View.VISIBLE);

                                            tv_dog_title.setText(petlist.get(i[0]).getTitle());
                                            tv_dog_place.setText(petlist.get(i[0]).getPlace());
                                            Glide.with(getActivity()).load(petlist.get(i[0]).getUrlToImage()).into(img_dog);

                                            layout.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    if (firebaseUser.getUid().equals("o7WI1MVBkufUvCzoRuu4nynK4ou2")) {
                                                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                                                        builder.setTitle("화면을 선택하세요.");
                                                        builder.setMessage("관리자모드 또는 일반모드");
                                                        builder.setPositiveButton("관리자모드", new DialogInterface.OnClickListener() {
                                                            @Override
                                                            public void onClick(DialogInterface dialog, int which) {
                                                                Intent intent = new Intent(getActivity(), PetLoseActivity.class);
                                                                startActivity(intent);
                                                            }
                                                        });
                                                        builder.setNeutralButton("취소", new DialogInterface.OnClickListener() {
                                                            @Override
                                                            public void onClick(DialogInterface dialog, int which) {
                                                                dialog.dismiss();
                                                            }
                                                        });
                                                        builder.setNegativeButton("일반모드", new DialogInterface.OnClickListener() {
                                                            @Override
                                                            public void onClick(DialogInterface dialog, int which) {
                                                                String url = petlist.get(i[0]-1).getUrl();
                                                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url) );
                                                                getActivity().startActivity(intent);
                                                            }
                                                        });
                                                        builder.create().show();
                                                    } else {
                                                        String url = petlist.get(i[0]-1).getUrl();
                                                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url) );
                                                        getActivity().startActivity(intent);
                                                    }
                                                }
                                            });
                                            i[0]++;
                                            if (petlist.size() == i[0]) {
                                                i[0] = 0;
                                            }
                                        }
                                    });
                                }
                                Thread.sleep(2500);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }).start();

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
    private class Description extends AsyncTask<Void, Void, Void> {

        //진행바표시
        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //진행다일로그 시작
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setMessage("잠시 기다려 주세요.");
            progressDialog.show();

        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                Document doc = Jsoup.connect("https://www.junsungki.com/lounge/petcommunity-news-list.do").get();
                Elements mElementDataSize = doc.select("ul[class=thumb-list-wrapper thumb-list-wrapper--4]").select("li"); //필요한 녀석만 꼬집어서 지정
                int mElementSize = mElementDataSize.size(); //목록이 몇개인지 알아낸다. 그만큼 루프를 돌려야 하나깐.

                for(Element elem : mElementDataSize){ //이렇게 요긴한 기능이
                    //목록 <li> 에서 다시 원하는 데이터를 추출해 낸다.


                    String my_title = elem.select("li a h3[class=title]").text();

                    Log.d("title :", "title " + my_title);

                    String my_link = "https://www.junsungki.com"+elem.select("li a[class=humb-list__link]").attr("href");
                    String my_imgUrl = elem.select("li a div[class=thumb-list__img thumb-list__img--sm]").attr("style");
                    String my_imgUrl2 ;
                    String my_imgUrl3 ;
                    my_imgUrl2 = my_imgUrl.replace("background-image: url(","");
                    my_imgUrl3 = my_imgUrl2.replace(")","");

                    Log.d("title :", "link " + my_link);
                    Log.d("title :", "link_url " + my_imgUrl3);
                    // Log.d("image :", "List " + my_imgUrl);
                    list.add(new Newsdata(my_title, my_imgUrl3, my_link));
                }

                if(list != null) {
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
                                                textView.setText(list.get(i[0]).getTitle());
                                                textView.startAnimation(animTransUp);
                                                textView.setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View v) {
                                                        String urll = (list.get(i[0] - 1).getUrl());
                                                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(urll));
                                                        startActivity(intent);
                                                    }
                                                });
                                                Log.e("로그", "로그1");
                                                i[0]++;
                                                if (list.size() == i[0]) {
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
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            progressDialog.dismiss();
        }
    }
    public void getWeather() {

        String url ="https://api.openweathermap.org/data/2.5/weather?lat="+lat+"&lon="+lon+"&APPID=5784f313fc0b1ea7e15c66bdb9c0158f&mode=json&units=metric&cnt=15";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d("NEWS",response);

                        try {
                            JSONObject jsonObj = new JSONObject(response);

                            JSONObject obj1 = jsonObj.getJSONObject("main");
                            String json = jsonObj.getString("name");

                            JSONArray arrayarticles = jsonObj.getJSONArray("weather");

                            //response -> NewsData 클래스 에다가 분류
                            List<Weather> weathers = new ArrayList<>();

                            Weather weather = new Weather();
                            for(int i = 0, j = arrayarticles.length(); i < j ; i++ ){

                                JSONObject obj = arrayarticles.getJSONObject(i);

                                Log.d("weather",obj.toString());

                                weather.setId(obj.getString("id"));
                                weather.setDescription(obj.getString("description"));
                            }
                            //   weather.setCity(jsonObj.getString("name"));
                            weather.setTemp(obj1.getString("temp"));
                            Log.d("temp",obj1.toString());
                            weather.setTemp_max(obj1.getString("temp_max"));
                            weather.setTemp_min(obj1.getString("temp_min"));
                            weather.setCity(json);
                            Log.d("name",json);
                            weathers.add(weather);

                            // specify an adapter (see also next example)
                            mAdapter = new WeatherAdapter(weathers, getContext());
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
