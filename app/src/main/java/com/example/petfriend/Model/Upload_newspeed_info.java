package com.example.petfriend.Model;

public class Upload_newspeed_info {
    private String title, content;
    private String publisher;

    public Upload_newspeed_info(String title, String content, String publisher) {
        this.title = title;
        this.content = content;
        this.publisher = publisher;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
