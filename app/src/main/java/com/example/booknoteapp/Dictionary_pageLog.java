package com.example.booknoteapp;

import java.io.Serializable;

public class Dictionary_pageLog implements Serializable {

    String date;
    String startP;
    String endP;
    int readPageNum;
    Dictionary_book dictionary_book;
    int positionInWholeList;

    public int getPositionInWholeList() {
        return positionInWholeList;
    }

    public void setPositionInWholeList(int positionInWholeList) {
        this.positionInWholeList = positionInWholeList;
    }

    public Dictionary_pageLog(Dictionary_book dictionary_book, String date, String startP, String endP) {
        this.date = date;
        this.startP = startP;
        this.endP = endP;
        this.dictionary_book = dictionary_book;
        readPageNum = (Integer.parseInt(endP)-Integer.parseInt(startP));
    }

    public int getReadPageNum() {
        return readPageNum;
    }

    public Dictionary_book getDictionary_book() {
        return dictionary_book;
    }

    public void setDictionary_book(Dictionary_book dictionary_book) {
        this.dictionary_book = dictionary_book;
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
        readPageNum = (Integer.parseInt(this.endP)-Integer.parseInt(startP));
    }

    public String getEndP() {
        return endP;
    }

    public void setEndP(String endP) {
        this.endP = endP;
        readPageNum = (Integer.parseInt(endP)-Integer.parseInt(this.startP));
    }
}
