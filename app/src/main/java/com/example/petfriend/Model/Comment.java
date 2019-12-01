package com.example.petfriend.Model;

public class Comment {

    private String comment;
    private String publisher;
    private long time;

    public Comment(String comment, String publisher,long time) {
        this.comment = comment;
        this.publisher = publisher;
        this.time = time;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public Comment() {
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }
}
