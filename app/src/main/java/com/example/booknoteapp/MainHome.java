package com.example.booknoteapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainHome extends AppCompatActivity {

    TextView nickname;

    Button btn_toDrawer;
    Button btn_toEssay;
    Button btn_toCalender;
    Button btn_toHome;
    ImageView btn_toSetting;


   RecyclerView recentReadingRecycler;
    Adapter_Reading adapter_reading=null;
    ArrayList<Dictionary_book> readingList;


    SharedPreferences currentUserPref;
    SharedPreferences.Editor currentUserPrefEditor;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initialize();
        allListener();
        getReadingPrefToArray();
        showReadingRecycler();
    }

    private void initialize() {

        nickname = findViewById(R.id.tv_home_nickname);

        //아래 메뉴 버튼
        btn_toDrawer =findViewById(R.id.btn_to_drawer);
        btn_toCalender =findViewById(R.id.btn_to_calender);
        btn_toEssay =findViewById(R.id.btn_to_assay);
        btn_toHome = findViewById(R.id.btn_to_home);
        btn_toSetting = findViewById(R.id.btn_home_setting);

        //리사이클러뷰
        recentReadingRecycler = findViewById(R.id.recycler_reading);



        //관리자용 쉐어드를 불러온다
        SharedPreferences userPref = getSharedPreferences("users", Activity.MODE_PRIVATE);
        //currentUser에 지금 로그인한 회원의 이메일이 저장되어있다.
        String userEmail = userPref.getString("currentUser","");
        //그 유저 이메일이 패키지 이름인 된 저장소를 불러온다.
        currentUserPref= getSharedPreferences(userEmail,MODE_PRIVATE);
        //저장되어있는 닉네임을 불러온다
        String nick = currentUserPref.getString("nickname","");

        nickname.setText(nick);

    }//이니셜라이징 끝

    private void allListener() {

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

        btn_toSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ///////////////////////////////////세팅화면 만들고 수정 필요
                Intent intent = new Intent(getApplicationContext(),Essay.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
            }
        });

    }//올리스너 끝


    private void getReadingPrefToArray() {
        Gson gson = new Gson();
        String json = currentUserPref.getString("reading","EMPTY");
        Type type = new TypeToken<ArrayList<Dictionary_book>>() {
        }.getType();
        if(!json.equals("EMPTY")){
            readingList= gson.fromJson(json,type);
        }

    }

    private void showReadingRecycler() {
        adapter_reading = new Adapter_Reading(readingList);
        recentReadingRecycler.setAdapter(adapter_reading);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recentReadingRecycler.setLayoutManager(layoutManager);

    }



}