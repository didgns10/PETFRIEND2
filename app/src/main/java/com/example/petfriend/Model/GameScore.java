package com.example.petfriend.Model;

public class GameScore {
    private int score;
    private String publisher;


    public GameScore() {
    }

    public GameScore(int score, String publisher) {
        this.score = score;
        this.publisher = publisher;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
