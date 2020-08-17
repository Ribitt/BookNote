package com.example.booknoteapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class SearchBook extends AppCompatActivity {

    ArrayList<Dictionary_book> bookList = new ArrayList<>();
    RecyclerView recyclerView =null;
    Adapter_SearchBook adapter_searchBook = null;
    Dictionary_book dictionary_book;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_book);


        //////툴바 적용하기
        Toolbar toolbar = (Toolbar)findViewById(R.id.app_toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.green));
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("도서 검색");

        initialize();

    }

    private void initialize() {

        recyclerView = findViewById(R.id.recycler_bookSearch);

        for(int i=0; i<5; i++){
            dictionary_book = new Dictionary_book("reading","책제목","작가이름~~");
            bookList.add(dictionary_book);
        }

        adapter_searchBook = new Adapter_SearchBook(bookList);
        recyclerView.setAdapter(adapter_searchBook);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
    }
}