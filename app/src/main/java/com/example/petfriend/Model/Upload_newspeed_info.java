package com.example.petfriend.Model;

import java.util.ArrayList;
import java.util.Date;

public class Upload_newspeed_info {
    private String title;
    private ArrayList<String> content;
    private String publisher;
    private Date createdAt;

    public Upload_newspeed_info(String title, ArrayList<String> content, String publisher, Date createdAt) {
        this.title = title;
        this.content = content;
        this.publisher = publisher;
        this.createdAt = createdAt;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getTitle() {
        return title;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<String> getContent() {
        return content;
    }

    public void setContent(ArrayList<String> content) {
        this.content = content;
    }
}
