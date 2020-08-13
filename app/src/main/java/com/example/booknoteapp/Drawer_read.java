package com.example.booknoteapp;

import androidx.appcompat.app.AppCompatActivity;
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

public class Drawer_read extends AppCompatActivity {

    ////////////리사이클러뷰
    ArrayList<Dictionary_read> mList = new ArrayList<>();
    Dictionary_read dic;
    RecyclerView recyclerView;
    Adapter_read adapter_read;
    LinearLayoutManager layoutManager = new LinearLayoutManager(this);
    ////////////리사이클러뷰

    //책 추가 버튼
    Button btn_addBook;
    //책 추가 버튼 누르면 나올 선택지 리스트
    CharSequence[] list_howToAddBook = {"직접 입력","책 검색하기","바코드 스캔"};
    //하단 메뉴 버튼
    Button btu_to_calendar;
    Button btn_to_drawer;
    Button btn_to_essay;
    Button btn_to_setting;
    Button btn_to_stat;
    /////////////////////////////

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer_read);

        ////////////리사이클러뷰
        recyclerView = findViewById(R.id.recycler_read);
        adapter_read = new Adapter_read(mList);
        for(int i=0; i<10; i++){
//            dic = new Dictionary_read(getDrawable(R.drawable.book_jieun),"김지은입니다", "2020.8.4",(float) 4.5, "자칭 진보면서 민주주의를 지향한다는 이들의 더럽고 허무한 이면");
//            mList.add(dic);
        }

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter_read);

        ////////////리사이클러뷰




        //책 추가하기 버튼
        btn_addBook = (Button) findViewById(R.id.btn_addBook);

        Button btnToInterested;
        btnToInterested = (Button) findViewById(R.id.btn_to_interested);
        btnToInterested.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getApplicationContext(),Drawer_interested.class);
                        startActivity(intent);
                        finish();
                    }
                }
        );

        Button btnToReading = (Button)findViewById(R.id.btn_to_reading);
        btnToReading.setOnClickListener(
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

//        ImageButton btnToDetail = (ImageButton)findViewById(R.id.btn_bookcover_read);
//        btnToDetail.setOnClickListener(
//                new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        Intent intent = new Intent(getApplicationContext(),Bookdetail_note_byPage.class);
//                        startActivity(intent);
//                        finish();
//                    }
//                }
//        );


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

    }

    @Override
    protected void onStop() {
        super.onStop();

        Toast.makeText(Drawer_read.this, "끝날 때 리스트 길이 "+mList.size(),Toast.LENGTH_LONG);

    }

    @Override
    protected void onResume() {
        super.onResume();



        //책 추가하기에서 데이터 수신하기
        intent = getIntent();
        if(getIntent().getExtras()!=null){
            byte[] arr = intent.getByteArrayExtra("image");
            Bitmap image = BitmapFactory.decodeByteArray(arr, 0, arr.length);
            Drawable bookCover = new BitmapDrawable(image);
            String title = intent.getExtras().getString("title");
            String date = intent.getExtras().getString("endDate");
            float rating = intent.getFloatExtra("rating",0);
            String review = intent.getExtras().getString("review");

          //  Dictionary_read dict = new Dictionary_read(bookCover,title,date,rating,review);
          //  mList.add(0,dict);
            adapter_read.notifyItemInserted(0);
            adapter_read.notifyDataSetChanged();
             //데이터 수신 끝
//            intent.removeExtra("image");
//            intent.removeExtra("title");
//            intent.removeExtra("rating");
//            intent.removeExtra("review");
        }


        //책 추가 버튼 클릭 리스너
        btn_addBook.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(Drawer_read.this);

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
                                                Toast.makeText(getApplicationContext(),list_howToAddBook[1]+"를 골랐습니다.",Toast.LENGTH_LONG).show();
                                                break;
                                            case 2:
                                                Toast.makeText(getApplicationContext(),list_howToAddBook[2]+"을 골랐습니다.",Toast.LENGTH_LONG).show();
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