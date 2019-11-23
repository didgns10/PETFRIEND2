package com.example.petfriend.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class Diary implements Parcelable {

    private String title,time,image,content;
    private long id;

    public Diary(){}

    public Diary(String title, String time, String image, String content) {
        this.title = title;
        this.time = time;
        this.image = image;
        this.content = content;
    }

    protected Diary(Parcel in) {
        title = in.readString();
        time = in.readString();
        image = in.readString();
        content = in.readString();
        id = in.readLong();
    }

    public static final Creator<Diary> CREATOR = new Creator<Diary>() {
        @Override
        public Diary createFromParcel(Parcel in) {
            return new Diary(in);
        }

        @Override
        public Diary[] newArray(int size) {
            return new Diary[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(time);
        dest.writeString(image);
        dest.writeString(content);
        dest.writeLong(id);
    }
}
