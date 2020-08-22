package com.example.booknoteapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
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

   Boolean myEssay = true;
   Button btn_myEssay;
   Button btn_everyEssay;

   TextView tv_warning_noEssay;

   RecyclerView recyclerView;
   Adapter_Essay adapter_essay;
   Dictionary_book dictionary_book;
   Dictionary_Essay dictionary_essay;
   ArrayList<Dictionary_Essay> essayArrayList = new ArrayList<>();

   SharedPreferences userPref;
   SharedPreferences.Editor userPrefEditor;

   SharedPreferences devPref;
   SharedPreferences.Editor devPrefEditor;

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

        devPref = getSharedPreferences("users", Context.MODE_PRIVATE);
        devPrefEditor = devPref.edit();

        String currentEmail = devPref.getString("currentUser","");
        userPref = getSharedPreferences(currentEmail,this.MODE_PRIVATE);
        userPrefEditor = userPref.edit();

        btn_writeEssay = findViewById(R.id.btn_write_essay);
        btn_myEssay = findViewById(R.id.btn_myEssay);
        btn_everyEssay = findViewById(R.id.btn_everyEssay);

        tv_warning_noEssay = findViewById(R.id.tv_warning_noEssay);

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

        //내 에세이만 보기 버튼
        btn_myEssay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myEssay = true;
                btn_everyEssay.setBackground(getDrawable(R.drawable.button_grey));
                btn_myEssay.setBackground(getDrawable(R.drawable.button_yellowgreen));
                getArrayFromPref(userPref);

            }
        });
        //내 에세이 버튼 끝

        //모두의 에세이 보기 버튼
        btn_everyEssay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myEssay=false;
                btn_everyEssay.setBackground(getDrawable(R.drawable.button_yellowgreen));
                btn_myEssay.setBackground(getDrawable(R.drawable.button_grey));

                Gson gson = new Gson();
                essayArrayList.clear();
                String json = devPref.getString("essay","EMPTY");

                Type type = new TypeToken<ArrayList<Dictionary_Essay>>() {
                }.getType();
                if(!json.equals("EMPTY")){
                    essayArrayList = gson.fromJson(json,type);
                }
                adapter_essay = new Adapter_Essay(essayArrayList);
                recyclerView.setAdapter(adapter_essay);

            }
        });
        //모두의 에세이

        //에세이 쓰기 버튼
        btn_writeEssay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), AddEssayBook.class);
                startActivity(intent);

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
    }//올리스너 끝

    @Override
    protected void onPostResume() {
        super.onPostResume();

        if(myEssay){
            getArrayFromPref(userPref);
        }else{
            getArrayFromPref(devPref);
        }

    }

    private void getArrayFromPref(SharedPreferences pref) {
        Gson gson = new Gson();

       // 프레프 비우기용
//        essayArrayList.clear();
//        String json = gson.toJson(essayArrayList);
//        userPrefEditor.putString("essay",json);
//        userPrefEditor.commit();
//        devPrefEditor.putString("essay",json);
//        devPrefEditor.commit();

        essayArrayList.clear();
        String json = pref.getString("essay","EMPTY");

        Type type = new TypeToken<ArrayList<Dictionary_Essay>>() {
        }.getType();
        if(!json.equals("EMPTY")){
            essayArrayList = gson.fromJson(json,type);
        }

        if(myEssay&& essayArrayList.size()==0){
           tv_warning_noEssay.setVisibility(View.VISIBLE);
        }else if(myEssay&& essayArrayList.size()>0){
            tv_warning_noEssay.setVisibility(View.GONE);
        }
        adapter_essay = new Adapter_Essay(essayArrayList);
        recyclerView.setAdapter(adapter_essay);
        adapter_essay.notifyDataSetChanged();

    }
}