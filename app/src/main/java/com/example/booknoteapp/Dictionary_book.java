package com.example.booknoteapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class Dictionary_book implements Serializable {


    //필수로 들어가야 하는 항목
    String status;
    String title;
    String author;


    //추가될 수 있는 항목
    String pageNum="";
    String bookCover;
    String bookCoverUri="";
    String bookCoverUrl="";
    String publisher="";

    String finishedDate="";
    String review="";
    float rating=0;

    String memo="";

   // ArrayList<Dictionary_pageLog> pageLogArrayList;
   // ArrayList<Dictionary_note> noteArrayList;


    public Dictionary_book(String status, String title, String author) {
        this.title = title;
        this.author = author;
        this.status = status;
    }

    public Bitmap getBookCover() {
        byte[] decodeByte = Base64.decode(this.bookCover,0);
        return BitmapFactory.decodeByteArray(decodeByte, 0, decodeByte.length);

    }

    //비트맵 이미지를 넣으면 base64로 바꿔서 저장해둔다.
    public void setBookCover(Bitmap bitmapImg) {
        Bitmap bitmap = bitmapImg;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100,baos);
        byte[] b= baos.toByteArray();
        String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);
        Log.d("Image Log........................", imageEncoded);
        this.bookCover = imageEncoded;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPageNum() {
        return pageNum;
    }

    public void setPageNum(String pageNum) {
        this.pageNum = pageNum;
    }

    public String getBookCoverUri() {
        return bookCoverUri;
    }

    public void setBookCoverUri(String bookCoverUri) {
        this.bookCoverUri = bookCoverUri;
    }

    public String getBookCoverUrl() {
        return bookCoverUrl;
    }

    public void setBookCoverUrl(String bookCoverUrl) {
        this.bookCoverUrl = bookCoverUrl;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getFinishedDate() {
        return finishedDate;
    }

    public void setFinishedDate(String finishedDate) {
        this.finishedDate = finishedDate;
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

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

//    public ArrayList<Dictionary_pageLog> getPageLogArrayList() {
//        return pageLogArrayList;
//    }
//
//    public void setPageLogArrayList(ArrayList<Dictionary_pageLog> pageLogArrayList) {
//        this.pageLogArrayList = pageLogArrayList;
//    }
//
//    public ArrayList<Dictionary_note> getNoteArrayList() {
//        return noteArrayList;
//    }
//
//    public void setNoteArrayList(ArrayList<Dictionary_note> noteArrayList) {
//        this.noteArrayList = noteArrayList;
//    }
}
