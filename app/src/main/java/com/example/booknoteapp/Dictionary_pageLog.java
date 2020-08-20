package com.example.booknoteapp;

public class Dictionary_pageLog {

    String date;
    String startP;
    String endP;
    String title;

    public Dictionary_pageLog(String title, String date, String startP, String endP) {
        this.date = date;
        this.startP = startP;
        this.endP = endP;
        this.title =title;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStartP() {
        return startP;
    }

    public void setStartP(String startP) {
        this.startP = startP;
    }

    public String getEndP() {
        return endP;
    }

    public void setEndP(String endP) {
        this.endP = endP;
    }
}
