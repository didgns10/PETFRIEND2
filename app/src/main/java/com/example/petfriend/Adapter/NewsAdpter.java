package com.example.petfriend.Adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.petfriend.Model.Newsdata;
import com.example.petfriend.R;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

public class NewsAdpter extends RecyclerView.Adapter<NewsAdpter.MyViewHolder> {
    private List<Newsdata> mDataset;
    private static View.OnClickListener onClickListener;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView TextView_title;
        public TextView TextView_Content;
        public SimpleDraweeView ImageView_title;
        public View rootView;
        public MyViewHolder(View v) {
            //부모 뷰가 v이기떄문에 v.find가 나온다 (부모 뷰 = row_news)
            super(v);
            TextView_title = v.findViewById(R.id.TextView_title);
            TextView_Content = v.findViewById(R.id.TextView_content);
            ImageView_title = v.findViewById(R.id.ImageView_title);
            rootView = v;
            v.setClickable(true);
            v.setEnabled(true);
            v.setOnClickListener(onClickListener);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public NewsAdpter(List<Newsdata> myDataset, Context context, View.OnClickListener onClick) {
        //{"1","2"}
        mDataset = myDataset;
        onClickListener = onClick;
        Fresco.initialize(context);
    }

    // Create new views (invoked by the layout manager)
    @Override
    public NewsAdpter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        // create a new view
        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_news, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        Newsdata news = mDataset.get(position);
        holder.TextView_title.setText(news.getTitle());

        String content = news.getContent();
        if(content != "null" && content.length() >0 ) {
            holder.TextView_Content.setText(content);
        }
        else{
            holder.TextView_Content.setText("빈 내용입니다");
        }

        Uri uri = Uri.parse(news.getUrlToImage());
        String uri_ok = news.getUrlToImage();

        if(uri_ok != "null" && uri_ok.length() >0 ) {
            holder.ImageView_title.setImageURI(uri);
        }
        else{
            holder.ImageView_title.setImageURI("data:image/png;base64,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");
        }

        //tag - label
        holder.rootView.setTag(position);


    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        //삼항 연산자
        return mDataset == null  ? 0 : mDataset.size();
    }

    public Newsdata getNews(int position){
        return mDataset != null ?  mDataset.get(position) : null;
    }

}
