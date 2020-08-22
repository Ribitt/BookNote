package com.example.booknoteapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class AddEssay extends AppCompatActivity {


    Button btn_open;
    Button btn_private;

    ImageView iv_bookCover;
    TextView tv_bookTitle;
    TextView tv_bookAuthor;
    TextView tv_bookPublisher;

    EditText et_essayTitle;
    EditText et_essayContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_essay);

        initialize();
        allListener();
        setBook();
    }

    private void initialize(){
        //////툴바 적용하기
        Toolbar toolbar = (Toolbar)findViewById(R.id.app_toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.green));
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("에세이 쓰기");

        btn_open = findViewById(R.id.btn_open);
        btn_private =findViewById(R.id.btn_private);

        iv_bookCover = findViewById(R.id.iv_book_cover);
        tv_bookTitle = findViewById(R.id.tv_book_title);
        tv_bookAuthor = findViewById(R.id.tv_book_author);
        tv_bookPublisher = findViewById(R.id.tv_book_publisher);

        et_essayContent = findViewById(R.id.et_essayContent);
        et_essayTitle = findViewById(R.id.et_essayTitle);


    }//이니셜라이저 끝

    private void allListener(){

    }//올리스너 끝

    private void setBook(){

        Intent intent = getIntent();
        if(intent.getExtras()==null
        ){
            /////그냥 책 추가하기를 누른 경우. 아무 일도 안해도 된다.
        }else{
            //책 검색을 통해 넘어온 경우. 이미지, 저자, 책제목, 출판사를 세팅해준다.
            Dictionary_book dict = (Dictionary_book) intent.getSerializableExtra("bookDict");
            iv_bookCover.setImageBitmap(dict.getBookCover());
            tv_bookTitle.setText(dict.getTitle());
            tv_bookPublisher.setText(dict.getPublisher());
            tv_bookAuthor.setText(dict.getAuthor());

        }
    }
}