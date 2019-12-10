package com.example.petfriend.Model;

public class VideoDetails {
    public String VideoId,title,url;

    public VideoDetails(String videoId, String title, String url) {
        VideoId = videoId;
        this.title = title;
        this.url = url;
    }

    public VideoDetails() {
    }

    public String getVideoId() {
        return VideoId;
    }

    public void setVideoId(String videoId) {
        VideoId = videoId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
