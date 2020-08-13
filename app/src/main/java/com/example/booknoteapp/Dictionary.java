package com.example.booknoteapp;

public class Dictionary {

    private String English;
    private String Korean;
    private String id;

    public String getEnglish() {
        return English;
    }

    public void setEnglish(String english) {
        English = english;
    }

    public String getKorean() {
        return Korean;
    }

    public void setKorean(String korean) {
        Korean = korean;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Dictionary(String id,String english, String korean ) {
        English = english;
        Korean = korean;
        this.id = id;
    }
}
