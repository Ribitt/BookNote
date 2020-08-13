package com.example.booknoteapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class DrawerTap extends AppCompatActivity {


    //하단 메뉴 버튼
    Button btu_to_calendar;
    Button btn_to_drawer;
    Button btn_to_essay;
    Button btn_to_setting;
    Button btn_to_stat;
    /////////////////////////////

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer_tap);

        ViewPager vp = findViewById(R.id.viewpager);
        Adapter_ViewPager adapter_viewPager = new Adapter_ViewPager(getSupportFragmentManager());
        vp.setAdapter(adapter_viewPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(vp);

        // 하단 메뉴바 버튼 선언
        btu_to_calendar = (Button) findViewById(R.id.btn_to_calender);
        btn_to_drawer = (Button) findViewById(R.id.btn_to_drawer);
        btn_to_essay = (Button) findViewById(R.id.btn_to_assay);
        btn_to_setting = (Button) findViewById(R.id.btn_to_setting);
        btn_to_stat = (Button) findViewById(R.id.btn_to_stat);
        // 하단 메뉴바 버튼 선언 끝
        // 하단 메뉴바 클릭 이벤트
        btu_to_calendar.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getApplicationContext(), Calendar.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                        startActivity(intent);
                    }
                }
        );
//        btn_to_drawer.setOnClickListener(
//                new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
//                        startActivity(intent);
//                        finish();
//                    }
//                }
//        );
        btn_to_essay.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getApplicationContext(), Essay.class);
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
                        Intent intent = new Intent(getApplicationContext(), Stat.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                        startActivity(intent);
                    }
                }
        );

        // 하단 메뉴바 클릭 이벤트 끝

    }//온크리에이트 끝

}



//        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//                int posistion = tab.getPosition();
//                //이 포지션으로 몇번째 메뉴가 선택되었는지 구할 수 있다.
//
//            }
//
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {
//
//            }
//
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {
//
//            }
//        });

//    private void changeView(int index) {
//        textView1 = findViewById(R.id.tab_text1);
//        textView2 =findViewById(R.id.tab_text2);
//        textView3 = findViewById(R.id.tab_text3);
//        //텍스트 뷰 선언해주고
//
//        switch(index) {
//            case 0:
//                //읽는 중인 책
//                textView1.setVisibility(View.VISIBLE);
//                textView2.setVisibility(View.INVISIBLE);
//                textView3.setVisibility(View.INVISIBLE);
//                break;
//            case 1:
//                textView1.setVisibility(View.INVISIBLE);
//                textView2.setVisibility(View.VISIBLE);
//                textView3.setVisibility(View.INVISIBLE);
//                break;
//            case 2:
//                textView1.setVisibility(View.INVISIBLE);
//                textView2.setVisibility(View.INVISIBLE);
//                textView3.setVisibility(View.VISIBLE);
//                break;
//        }
//
//    }