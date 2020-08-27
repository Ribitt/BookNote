package com.example.booknoteapp;

import android.graphics.drawable.Drawable;
import android.widget.TextView;

import java.io.Serializable;

public class Dictionary_note implements Serializable {

    Dictionary_book dictionary_book;

    String pageNum;
    String date;
    String note;
    String quote;
    int color;
    int positionInWholeList;

    public int getPositionInWholeList() {
        return positionInWholeList;
    }

    public void setPositionInWholeList(int positionInWholeList) {
        this.positionInWholeList = positionInWholeList;
    }

    public Dictionary_book getDictionary_book() {
        return dictionary_book;
    }

    public void setDictionary_book(Dictionary_book dictionary_book) {
        this.dictionary_book = dictionary_book;
    }

    public int getColor() {

        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public String getQuote() {
        return quote;
    }

    public void setQuote(String quote) {
        this.quote = quote;
    }

    public String getPageNum() {
        return pageNum;
    }

    public void setPageNum(String pageNum) {
        this.pageNum = pageNum;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Dictionary_note(Dictionary_book book,String pageNum, String date, String quote,String note, int color) {
        this.dictionary_book = book;
        this.pageNum = pageNum;
        this.date = date;
        this.note = note;
        this.quote = quote;
        this.color = color;
    }
}
