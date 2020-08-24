package com.example.booknoteapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;

public class Dictionary_Essay  implements Serializable {

    String userEmail;
    int positionInWhole;

    public int getPositionInWhole() {
        return positionInWhole;
    }

    public void setPositionInWhole(int positionInWhole) {
        this.positionInWhole = positionInWhole;
    }

    String bookTitle;
    String bookAuthor;
    String bookCover;
    String bookPublisher;

    String nickname;
    String essayTitle;
    String essayContent;
    String date;
    String likeNum="0";

    Boolean isOpen=true;
    Boolean likeClicked = false;

    public Boolean getLikeClicked() {
        return likeClicked;
    }

    public void setLikeClicked(Boolean likeClicked) {
        this.likeClicked = likeClicked;
    }

    public Boolean getOpen() {
        return isOpen;
    }

    public void setOpen(Boolean open) {
        isOpen = open;
    }

    Dictionary_Essay(Dictionary_book book, Boolean isOpen){

        this.bookTitle = book.getTitle();
        this.bookAuthor = book.getAuthor();
        this.bookCover = book.bookCover;
        this.bookPublisher = book.getPublisher();
        this.isOpen = isOpen;

    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public String getBookAuthor() {
        return bookAuthor;
    }

    public void setBookAuthor(String bookAuthor) {
        this.bookAuthor = bookAuthor;
    }

    public Bitmap getBookCover() {

        byte[] decodeByte = Base64.decode(this.bookCover,0);
        return BitmapFactory.decodeByteArray(decodeByte, 0, decodeByte.length);

    }

    public void setBookCover(Bitmap bitmapImg) {
        Bitmap bitmap = bitmapImg;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100,baos);
        byte[] b= baos.toByteArray();
        String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);
        Log.d("Image Log........................", imageEncoded);
        this.bookCover = imageEncoded;
    }

    public String getBookPublisher() {
        return bookPublisher;
    }

    public void setBookPublisher(String bookPublisher) {
        this.bookPublisher = bookPublisher;
    }

    public String getEssayTitle() {
        return essayTitle;
    }

    public void setEssayTitle(String essayTitle) {
        this.essayTitle = essayTitle;
    }

    public String getEssayContent() {
        return essayContent;
    }

    public void setEssayContent(String essayContent) {
        this.essayContent = essayContent;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLikeNum() {
        return likeNum;
    }

    public void setLikeNum(String likeNum) {
        this.likeNum = likeNum;
    }
}
