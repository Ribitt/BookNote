package com.example.booknoteapp;

import android.graphics.drawable.Drawable;

public class Dictionary_read {

// Drawable bookCover;
 String title;
 String endDate;
 float rating;
 String review;

    public Dictionary_read(String title, String endDate,float rating, String review) {
       // this.bookCover = bookCover;
        this.title = title;
        this.endDate = endDate;
        this.rating = rating;
        this.review = review;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

//    public Drawable getBookCover() {
//        return bookCover;
//    }
//
//    public void setBookCover(Drawable bookCover) {
//        this.bookCover = bookCover;
//    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }
}
