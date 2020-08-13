package com.example.booknoteapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class Calendar extends AppCompatActivity {


    TextView tvNum_calender_pageSum;
    //하단 메뉴 버튼
    Button btu_to_calendar;
    Button btn_to_drawer;
    Button btn_to_essay;
    Button btn_to_setting;
    Button btn_to_stat;
    /////////////////////////////
    ImageButton btn_to_bookdetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calander);





        //총 읽은 페이지 뷰
        tvNum_calender_pageSum = (TextView)findViewById(R.id.tvNum_calender_pageSum);

        // 하단 메뉴바 버튼 선언
        btu_to_calendar = (Button)findViewById(R.id.btn_to_calender);
        btn_to_drawer = (Button)findViewById(R.id.btn_to_drawer);
        btn_to_essay = (Button)findViewById(R.id.btn_to_assay);
        btn_to_setting = (Button)findViewById(R.id.btn_to_setting);
        btn_to_stat = (Button)findViewById(R.id.btn_to_stat);
        // 하단 메뉴바 버튼 선언 끝
        // 하단 메뉴바 클릭 이벤트
        btu_to_calendar.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getApplicationContext(), Calendar.class);
                        startActivity(intent);
                        finish();
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

        btn_to_stat.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getApplicationContext(),Stat.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                        startActivity(intent);
                    }
                }
        );

        // 하단 메뉴바 클릭 이벤트 끝

        btn_to_bookdetail = (ImageButton)findViewById(R.id.btn_calender_bookcover);
    }

    @Override
    protected void onStart() {
        super.onStart();


    }

    @Override
    protected void onRestart() {
        super.onRestart();


    }

    @Override
    protected void onResume() {
        super.onResume();
//        int prefNum;
//        prefNum = Integer.parseInt( PreferenceManager.getString(getApplicationContext(),"pageNum"));
        if(PreferenceManager.getString(getApplicationContext(),"pageNum")!=""){
            tvNum_calender_pageSum.setText(PreferenceManager.getString(getApplicationContext(),"pageNum"));
        }else{
            tvNum_calender_pageSum.setText("0");
        }

//        btn_to_bookdetail.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getApplicationContext(),Bookdetail_note_byPage.class);
//                startActivity(intent);
//            }
//        });



    }

}