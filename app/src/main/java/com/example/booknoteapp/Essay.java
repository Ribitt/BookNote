package com.example.booknoteapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Essay extends AppCompatActivity {

    //현재시간 가져오는 메소드
    long now = System.currentTimeMillis();
    Date date = new Date(now);
    SimpleDateFormat mFormat = new SimpleDateFormat("yyyy.MM.dd");
    String time = mFormat.format(date);
    //현재시간 가져오는 메소드

   Button btn_writeEssay;
   Button btn_myEssay;
   Button btn_everyEssay;

   RecyclerView recyclerView;
   Adapter_Essay adapter_essay;
   Dictionary_book dictionary_book;
   Dictionary_Essay dictionary_essay;
   ArrayList<Dictionary_Essay> essayArrayList = new ArrayList<>();

    //하단 메뉴 버튼

    Button btn_toDrawer;
    Button btn_toEssay;
    Button btn_toCalender;
    Button btn_toHome;

    /////////////////////////////
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_essay);

        initialize();
        allListener();
    }

    private void initialize(){

        btn_writeEssay = findViewById(R.id.btn_write_essay);
        btn_myEssay = findViewById(R.id.btn_myEssay);
        btn_everyEssay = findViewById(R.id.btn_everyEssay);

        recyclerView = findViewById(R.id.recycler_essay);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //아래 메뉴 버튼
        btn_toDrawer =findViewById(R.id.btn_to_drawer);
        btn_toCalender =findViewById(R.id.btn_to_calender);
        btn_toEssay =findViewById(R.id.btn_to_assay);
        btn_toHome = findViewById(R.id.btn_to_home);

        // 하단 메뉴바 버튼 선언 끝
    }


    private void allListener(){
        //에세이 쓰기 버튼
        btn_writeEssay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dictionary_book = new Dictionary_book("read","와일드","셰릴 스트레이드");
                dictionary_book.setBookCover(BitmapFactory.decodeResource(getApplicationContext().getResources(),R.drawable.book_jieun));
                dictionary_essay = new Dictionary_Essay(dictionary_book);
                dictionary_essay.setDate(time);
                dictionary_essay.setEssayTitle("내가 길을 잃었을 때");
                dictionary_essay.setEssayContent("이 책을 읽으면 될 것 같다. 미친듯이 힘들어서 이이이이이이이디디디디디댜댣" +
                        "ㄷㄹㄷㅀㄷㅎㄷㅎㄷㅎㄷㅎㄷㅎㄷㅎㄷㅎㄷㅎㄷㅎㄷㅎㄷㅎㄷㅎㅎㄷㅎㄷㅎㄷㅎㄷㅎㄷㅎㄷㅎㄷ" +
                        "ㅎㄷㅎㄷㅎㄷㅎㄷㅎㄷㅎㄷㅎㄷㅎㄷㅎㄷㅎㄷㅎㄷㅎㄷㅎㄷㅎㄷㅎㅎㄷ다다다다다ㅏㄷㄷㄷㄷㄷㄷㄷㄷ" +
                        "ㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷ" +
                        "ㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷ" +
                        "너무재밌다");
                essayArrayList.add(dictionary_essay);
                adapter_essay = new Adapter_Essay(essayArrayList);
                recyclerView.setAdapter(adapter_essay);

            }
        });

        //에세이 쓰기 버튼 끝

        // 하단 메뉴바 클릭 이벤트
        btn_toHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainHome.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
            }
        });

        btn_toDrawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), DrawerTap.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
            }
        });

        btn_toCalender.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent intent = new Intent(getApplicationContext(), Calendar.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                        startActivity(intent);
                    }
                }
        );

        btn_toEssay.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent intent = new Intent(getApplicationContext(),Essay.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                        startActivity(intent);
                    }
                }
        );
        // 하단 메뉴바 클릭 이벤트 끝
    }
}