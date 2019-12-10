package com.example.petfriend.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.petfriend.Model.Weather;
import com.example.petfriend.R;

import java.util.List;

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.ViewHolder>{

    private List<Weather> weatherList;
    private Context mcontext;

    public WeatherAdapter(List<Weather> weatherList, Context mcontext) {
        this.weatherList = weatherList;
        this.mcontext = mcontext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_weather, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Weather weather = weatherList.get(position);

        holder.tv_temp.setText(weather.getTemp());
        holder.tv_temp_max.setText(weather.getTemp_max());
        holder.tv_temp_min.setText(weather.getTemp_min());
        holder.tv_weather.setText(weather.getDescription());
        holder.tv_city.setText(weather.getCity());
        Han(weather.getId(),holder.img_weather);

    }

    @Override
    public int getItemCount() {
        return weatherList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_temp,tv_temp_max,tv_temp_min,tv_weather,tv_city;
        public ImageView img_weather;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_temp =itemView.findViewById(R.id.tv_temp);
            tv_temp_max =itemView.findViewById(R.id.tv_temp_max);
            tv_temp_min =itemView.findViewById(R.id.tv_temp_min);
            tv_weather =itemView.findViewById(R.id.tv_weather);
            img_weather =itemView.findViewById(R.id.img_weather);
            tv_city =itemView.findViewById(R.id.tv_city);
        }
    }

    private void Han(String id,ImageView imageView){
        int a = Integer.parseInt(id);
        if(a == 200 || a == 201 ||  a ==202 ||  a ==210 || a ==211 || a ==212 || a ==230 || a ==221 || a ==231 || a ==232){
            imageView.setImageResource(R.drawable.strom);
        }
        if(a == 300 ||a == 301 || a ==302 || a ==310 ||a ==311 ||a == 312 || a ==313 || a ==314 ||a == 321){
            imageView.setImageResource(R.drawable.rain);
        }
        if(a == 500 ||a == 501 ||a == 502 ||a == 503 || a ==504 ||a == 511 || a ==520 || a ==521 || a ==522 ||a == 531){
            imageView.setImageResource(R.drawable.rain);
        }
        if(a ==600 || a ==601 ||a == 602 || a ==611 ||a ==612 || a ==615 || a ==616 || a ==620 || a ==621 || a ==622){
            imageView.setImageResource(R.drawable.snow);
        }
        if(a ==701 ||  a ==711 || a == 721 ||  a ==731 ||a == 741 ||a == 751 ||a == 761 ||a == 762 || a ==771 ||a == 781 || a ==801 || a ==802 || a ==803){
            imageView.setImageResource(R.drawable.mist);
        }
        if(Integer.parseInt(id)== 800){
            imageView.setImageResource(R.drawable.sun);
        }
        if(Integer.parseInt(id)== (804)){
            imageView.setImageResource(R.drawable.cloud);
        }
        if(a ==51 ||a == 952 ||a == 953 || a ==954 ||a ==955 || a ==956 || a ==957 || a ==958 || a ==959 ||a == 960 || a ==961 || a ==962){
            imageView.setImageResource(R.drawable.wind);
        }

    }
}
