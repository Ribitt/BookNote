package com.example.booknoteapp;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Dialog_insertPage extends Dialog {

    TextView date;
    private Button positive;
    private Button negative;
    EditText startP;
    EditText endP;

    //현재시간 가져오는 메소드
    long now = System.currentTimeMillis();
    Date dateNow = new Date(now);
    SimpleDateFormat mFormat = new SimpleDateFormat("yyyy.MM.dd");
    String time = mFormat.format(dateNow);
    //현재시간 가져오는 메소드


    public String getStartP() {
        return startP.getText().toString();
    }

    public void setStartP(String startP) {
        this.startP.setText(startP);
    }

    public String getEndP() {
        return endP.getText().toString();
    }

    public void setEndP(String endP) {
        this.endP.setText(endP);
    }

    public String getDate() {
        return date.getText().toString();
    }

    public void setDate(String date) {
        this.date.setText(date);
    }

    private View.OnClickListener mDateListener;
    private View.OnClickListener mNegativeListener;
    private View.OnClickListener mPositiveListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //다이얼로그 밖의 화면은 흐리게 만들기
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        layoutParams.dimAmount = 0.8f;
        getWindow().setAttributes(layoutParams);

        setContentView(R.layout.dialog_insert_page);

        date = findViewById(R.id.editText_insertDate);
        positive = findViewById(R.id.btn_insertPage_positive);
        negative = findViewById(R.id.btn_insertPage_negative);
        startP = findViewById(R.id.editText_startPage);
        endP = findViewById(R.id.editText_endPage);


        //클릭 리스너 세팅해서 클릭 버튼이 동작하도록 만들어줌
        date.setOnClickListener(mDateListener);
        //기본은 오늘 날짜가 뜨도록 세팅해두기
        date.setText(time);

        positive.setOnClickListener(mPositiveListener);
        negative.setOnClickListener(mNegativeListener);


    }
    public Dialog_insertPage(@NonNull Context context, View.OnClickListener dateListener, View.OnClickListener positive, View.OnClickListener negative){
        super(context);
        this.mDateListener = dateListener;
        this.mPositiveListener =positive;
        this.mNegativeListener = negative;

    }
}
