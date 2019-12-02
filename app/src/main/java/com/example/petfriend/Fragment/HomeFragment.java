package com.example.petfriend.Fragment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.bumptech.glide.Glide;
import com.example.petfriend.Activity.Idea.IdeaActivity;
import com.example.petfriend.Activity.Idea.Idea_place_Activity;
import com.example.petfriend.Activity.Idea.Idea_place_set_Activity;
import com.example.petfriend.Activity.MainActivity;
import com.example.petfriend.Activity.Menu.Diary_Activity;
import com.example.petfriend.Activity.NewsActivity;
import com.example.petfriend.Activity.PetLoseActivity;
import com.example.petfriend.Adapter.PetNewsAdapter;
import com.example.petfriend.Model.Newsdata;
import com.example.petfriend.Model.Pet;
import com.example.petfriend.Model.PetFireDBHelper;
import com.example.petfriend.Model.PetLose;
import com.example.petfriend.Model.PetLoseFireDBHelper;
import com.example.petfriend.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

    public HomeFragment(){

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_home, container, false);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        ImageView img_idea = (ImageView)rootView.findViewById(R.id.img_pet);
        ImageView img_news = (ImageView)rootView.findViewById(R.id.img_news);
        ImageView img_diary = (ImageView)rootView.findViewById(R.id.img_diary);

        img_idea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent4 = new Intent(getActivity(), IdeaActivity.class);
                startActivity(intent4);
            }
        });
        img_news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent4 = new Intent(getActivity(), NewsActivity.class);
                startActivity(intent4);
            }
        });

        img_diary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent4 = new Intent(getActivity(), Diary_Activity.class);
                startActivity(intent4);
            }
        });

        layout = (LinearLayout)rootView.findViewById(R.id.layout);
       /* layout.setOnClickListener(new View.OnClickListener() {
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

                        }
                    });
                    builder.create().show();
                } else {
                }
            }
        });*/


        textView = (TextView)rootView.findViewById(R.id.tv_news_title);
        animTransUp = AnimationUtils.loadAnimation(getActivity(),R.anim.anim_traslate_up);

        img_dog = (ImageView)rootView.findViewById(R.id.img_dog);
        tv_dog_title = (TextView)rootView.findViewById(R.id.tv_dog_title);
        tv_dog_place = (TextView)rootView.findViewById(R.id.tv_dog_place);
        layout = (LinearLayout)rootView.findViewById(R.id.layout);


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
                    //영화목록 <li> 에서 다시 원하는 데이터를 추출해 낸다.


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
}
