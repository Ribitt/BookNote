package com.example.booknoteapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Stat extends AppCompatActivity {

    //하단 메뉴 버튼
    Button btu_to_calendar;
    Button btn_to_drawer;
    Button btn_to_essay;
    Button btn_to_setting;

    /////////////////////////////

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stat);

        // 하단 메뉴바 버튼 선언
        btu_to_calendar = (Button)findViewById(R.id.btn_to_calender);
        btn_to_drawer = (Button)findViewById(R.id.btn_to_drawer);
        btn_to_essay = (Button)findViewById(R.id.btn_to_assay);
        btn_to_setting = (Button)findViewById(R.id.btn_to_setting);

        // 하단 메뉴바 버튼 선언 끝
        // 하단 메뉴바 클릭 이벤트
        btu_to_calendar.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getApplicationContext(), BookCalendar.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                        startActivity(intent);
                    }
                }
        );
        btn_to_drawer.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                        startActivity(intent);
                        finish();
                    }
                }
        );
        btn_to_essay.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getApplicationContext(),Essay.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                        startActivity(intent);
                    }
                }
        );
        btn_to_setting.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
//                        Intent intent = new Intent(getApplicationContext(),SettingsActivity.class);
//                        startActivity(intent);
                    }
                }
        );



        // 하단 메뉴바 클릭 이벤트 끝
    }
}