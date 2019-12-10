package com.example.petfriend.Model;

public class Comment {

    private String comment;
    private String publisher;
    private long time;
    private String commentid;


    public Comment(String comment, String publisher, long time, String commentid) {
        this.comment = comment;
        this.publisher = publisher;
        this.time = time;
        this.commentid =commentid;
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
    public String getCommentid() {
        return commentid;
    }

    public void setCommentid(String commentid) {
        this.commentid = commentid;
    }
}
