package com.example.booknoteapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

public class Coordinator extends AppCompatActivity {

    RecyclerView recyclerView=null;
    Adapter_Note adapterNote = null;
    ArrayList<Dictionary_note> mList = new ArrayList<>();
    Dictionary_note dic;
    int position;

    int myBlue;


    View.OnClickListener mEditListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            position = (int)view.getTag();
            //지금 눌린 수정 버튼이 배열에서 가진 인덱스 값을 구해온다.
            Dictionary_note temp = mList.get(position);
            //해당 데이터들을 가져온다.

            Intent intent = new Intent(getApplicationContext(), EditNote.class);
            intent.putExtra("dicForEdit",temp);
            //수정대상인 데이터값을 전부 보낸다.
            startActivityForResult(intent, 3);
            //수정을 위해 보낼 때는 리퀘스트 코드를 3으로 한다.
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordinator);
        myBlue = getColor(R.color.myBlue);

        Toolbar toolbar = (Toolbar)findViewById(R.id.app_toolbar);
        toolbar.setTitleTextColor(myBlue);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("김지은입니다");


        recyclerView = findViewById(R.id.recycler_notes);
        adapterNote = new Adapter_Note(mList,mEditListener);
        recyclerView.setAdapter(adapterNote);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        for(int i=0; i<20; i++) {
//
//            dic = new Dictionary_note("123","2020.08.01","아주 재미가 있는 책이었다 이말입니다." +
//                    "\n완전 읽을 맛이 났다." +
//                    "\n얼른 과제 끝내고 또 보고 싶다. " +
//                    "\n후후후후후후후" +
//                    "\naskdjf;alsdj;fak","사실 여기가 제일 중요한 내용인데 ", myBlue);

            mList.add(dic);
            adapterNote.notifyDataSetChanged();
        }




    }
}