package com.example.booknoteapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Bookdetail_note_byColor extends AppCompatActivity {

    // 하단 메뉴바 버튼
    Button btu_to_calendar;
    Button btn_to_drawer;
    Button btn_to_notes;
    Button btn_to_setting;
    /////////////////////////////

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookdetail_note_by_color);

    // 하단 메뉴바 버튼 선언
        btu_to_calendar = (Button)findViewById(R.id.btn_to_calender);
         btn_to_drawer = (Button)findViewById(R.id.btn_to_drawer);
        btn_to_notes = (Button)findViewById(R.id.btn_to_assay);
        btn_to_setting = (Button)findViewById(R.id.btn_to_setting);
        // 하단 메뉴바 버튼 선언 끝
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();


        // 하단 메뉴바 클릭 이벤트
        btu_to_calendar.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getApplicationContext(), Calendar.class);
                        startActivity(intent);
                    }
                }
        );
        btn_to_drawer.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(intent);
                    }
                }
        );
        btn_to_notes.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getApplicationContext(),Note.class);
                        startActivity(intent);
                    }
                }
        );
        btn_to_setting.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                      //  Intent intent = new Intent(getApplicationContext(),SettingsActivity.class);
                     //   startActivity(intent);
                    }
                }
        );

        // 하단 메뉴바 클릭 이벤트 끝
    }
}