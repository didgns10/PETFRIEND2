package com.example.petfriend.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class Pet implements Parcelable {

    //이름, 수명 , 사진, 설명, 원산지
    private String name, elevation, photo, description, location;

    protected Pet(Parcel in) {
        name = in.readString();
        elevation = in.readString();
        photo = in.readString();
        description = in.readString();
        location = in.readString();
    }

    public static final Creator<Pet> CREATOR = new Creator<Pet>() {
        @Override
        public Pet createFromParcel(Parcel in) {
            return new Pet(in);
        }

        @Override
        public Pet[] newArray(int size) {
            return new Pet[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getElevation() {
        return elevation;
    }

    public void setElevation(String elevation) {
        this.elevation = elevation;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Pet(){
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(elevation);
        dest.writeString(photo);
        dest.writeString(description);
        dest.writeString(location);
    }
}
