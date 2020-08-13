package com.example.booknoteapp;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class Dictionary_reading implements Parcelable {
    //비트맵, 드로어블 이미지는 시리얼라이저블한 데이터타입이 아니라서 파슬러블로 변경했다.

    private Bitmap bookCover;
    private String bookTitle;

    @Override
    public int describeContents() {
        return 0;
    }


    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(bookCover, i);
        parcel.writeString(bookTitle);
    }



    public static final Creator<Dictionary_reading> CREATOR = new Creator<Dictionary_reading>() {
        @Override
        public Dictionary_reading createFromParcel(Parcel in) {
            return new Dictionary_reading(in);
        }

        @Override
        public Dictionary_reading[] newArray(int size) {
            return new Dictionary_reading[size];
        }
    };

    protected Dictionary_reading(Parcel in) {
        bookCover = in.readParcelable(Bitmap.class.getClassLoader());
        bookTitle = in.readString();


    }

public void readFromParcel(Parcel src){
        bookTitle = src.readString();
        bookCover = src.readParcelable(Bitmap.class.getClassLoader());
}

    public Dictionary_reading(Bitmap bookCover, String bookTitle) {
        this.bookCover = bookCover;
        this.bookTitle = bookTitle;
    }

    public Bitmap getBookCover() {
        return bookCover;
    }

    public void setBookCover(Bitmap bookCover) {

        this.bookCover = bookCover;
    }

    public String getBookTitle() {

        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }



}
