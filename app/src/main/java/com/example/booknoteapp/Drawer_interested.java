package com.example.booknoteapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

public class Drawer_interested extends AppCompatActivity {

    RecyclerView recyclerView = null;
    Adapter_Interested adapter = null;
    ArrayList<Dictionary_interested> myList = new ArrayList<>();
    Dictionary_interested dic;


    //책 추가 버튼
    Button btn_addBook;
    //책 추가 버튼 누르면 나올 선택지 리스트
    CharSequence[] list_howToAddBook = {"직접 입력", "책 검색하기", "바코드 스캔"};

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
        setContentView(R.layout.activity_drawer_interested);


        ///////////////////리사이클러뷰
        recyclerView = findViewById(R.id.recycler_interested);
        adapter = new Adapter_Interested(myList);
        recyclerView.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        dic = new Dictionary_interested(getDrawable(R.drawable.book_habits), "아주 작은 습관의 힘", "진짜 유명해서 한 번 읽어보고 싶다. " +
                "얼마나 재밌으면 사람들이 그렇게 추천을 하는걸까. " +
                "이걸 읽으면 인생이 바뀌나? " +
                "\n가격이 ");

        for (int i = 0; i < 20; i++) {
            myList.add(dic);

        }


        adapter.notifyDataSetChanged();
        /////////////////리사이클러뷰



        //책 추가하기 버튼
        btn_addBook = (Button) findViewById(R.id.btn_addBook);


        Button btnToRead;
        btnToRead = (Button) findViewById(R.id.btn_to_read);
        btnToRead.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getApplicationContext(), Drawer_read.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                        startActivity(intent);
                    }
                }
        );

        Button btnToReading = (Button) findViewById(R.id.btn_to_reading);
        btnToReading.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                        startActivity(intent);
                        finish();
                    }
                }
        );


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

    }


    @Override
    protected void onResume() {
        super.onResume();


        //책 추가하기에서 데이터 수신하기
        Intent intent = getIntent();
        if (getIntent().getExtras() != null) {
            byte[] arr = intent.getByteArrayExtra("image");
            Bitmap image = BitmapFactory.decodeByteArray(arr, 0, arr.length);
            Drawable bookCover = new BitmapDrawable(image);
            String title = intent.getExtras().getString("title");
            String memo = intent.getExtras().getString("memo");

            Dictionary_interested dict = new Dictionary_interested(bookCover, title, memo);
            myList.add(0, dict);
            adapter.notifyItemInserted(0);
            adapter.notifyDataSetChanged();
            //데이터 수신 끝

            //책 추가 버튼 클릭 리스너
            btn_addBook.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(Drawer_interested.this);

                            builder.setTitle("책 추가하기") //팝업창 제목
                                    .setItems(list_howToAddBook, new DialogInterface.OnClickListener() {
                                        //선택목록이랑 클릭 이벤트 리스너를 같이 주는군
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            switch (i) {
                                                case 0:
                                                    Intent intent = new Intent(getApplicationContext(), AddBook_toReading.class);
                                                    startActivity(intent);
                                                    break;
                                                case 1:
                                                    Toast.makeText(getApplicationContext(), list_howToAddBook[1] + "를 골랐습니다.", Toast.LENGTH_LONG).show();
                                                    break;
                                                case 2:
                                                    Toast.makeText(getApplicationContext(), list_howToAddBook[2] + "을 골랐습니다.", Toast.LENGTH_LONG).show();
                                                    break;
                                            }

                                        }
                                    });

                            AlertDialog alertDialog = builder.create();
                            alertDialog.show();
                        }
                    }
            );


        }
    }
}