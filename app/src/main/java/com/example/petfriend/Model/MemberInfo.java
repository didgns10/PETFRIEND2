package com.example.petfriend.Model;

import android.widget.EditText;

import com.example.petfriend.R;

public class MemberInfo {


    private  String phone_num,nickname,address,photoUrl, id;


    public MemberInfo(){}

    public MemberInfo(String nickname, String phone_num, String address , String photoUrl,String id) {
        this.phone_num = phone_num;
        this.nickname = nickname;
        this.address = address;
        this.photoUrl = photoUrl;
        this.id = id;
    }

    public MemberInfo(String nickname, String phone_num, String address) {
        this.phone_num = phone_num;
        this.nickname = nickname;
        this.address = address;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }
    public String getPhone_num() {
        return phone_num;
    }

    public void setPhone_num(String phone_num) {
        this.phone_num = phone_num;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
