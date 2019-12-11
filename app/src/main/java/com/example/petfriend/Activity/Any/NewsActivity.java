package com.example.petfriend.Activity.Any;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.example.petfriend.Activity.MainActivity;
import com.example.petfriend.Adapter.Any.PetNewsAdapter;
import com.example.petfriend.Model.Newsdata;
import com.example.petfriend.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class NewsActivity extends AppCompatActivity {

    private RecyclerView recyclerView2;
    private RecyclerView recyclerView;
    private ArrayList<Newsdata> list = new ArrayList();
    private String my_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);

        ImageButton button_back = (ImageButton) findViewById(R.id.imageButton_back);
        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent4 = new Intent(NewsActivity.this, MainActivity.class);
                intent4.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent4);
                finish();
            }
        });

        //AsyncTask 작동시킴(파싱)
        new Description().execute();
    }


    private class Description extends AsyncTask<Void, Void, Void> {

        //진행바표시
        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //진행다일로그 시작
            progressDialog = new ProgressDialog(NewsActivity.this);
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


                    my_title = elem.select("li a h3[class=title]").text();

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



                //추출한 전체 <li> 출력해 보자.

                Log.d("debug :", "List " + mElementDataSize);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            //ArraList를 인자로 해서 어답터와 연결한다.
            PetNewsAdapter myAdapter = new PetNewsAdapter(list, NewsActivity.this);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(myAdapter);
            progressDialog.dismiss();
        }
    }
}