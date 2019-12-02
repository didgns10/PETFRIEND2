package com.example.petfriend.Model;

public class PetLose {

    private String title;
    private String urlToImage;
    private String url;
    private String place;

    public PetLose(){}

    public PetLose(String title, String urlToImage, String url, String place) {
        this.title = title;
        this.urlToImage = urlToImage;
        this.url = url;
        this.place = place;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public void setUrlToImage(String urlToImage) {
        this.urlToImage = urlToImage;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }
}
