package com.example.booknoteapp;

import android.graphics.drawable.Drawable;

import org.w3c.dom.Text;

public class Dictionary_interested {

    Drawable bookCover;
    String title;
    String memo;

    public Drawable getBookCover() {
        return bookCover;
    }

    public void setBookCover(Drawable bookCover) {
        this.bookCover = bookCover;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Dictionary_interested(Drawable bookCover, String title, String memo) {
        this.bookCover = bookCover;
        this.title = title;
        this.memo = memo;
    }
}
