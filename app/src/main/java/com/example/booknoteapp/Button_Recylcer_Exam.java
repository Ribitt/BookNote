package com.example.booknoteapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class Button_Recylcer_Exam extends AppCompatActivity {

    private ArrayList<Dictionary> mArrayList;
    private Adapter_Custom mAdapter;
    private int count =-1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_button__recylcer__exam);


        RecyclerView mRecylcerView = (RecyclerView)findViewById(R.id.recyclerView_list);
        //내가 데이터를 넣을 컨테이너는 저 xml에 있는 바로 이 리사이클러뷰라네

        //LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        mRecylcerView.setLayoutManager(gridLayoutManager);
        //이 부분이 조금 헷갈려


        mArrayList = new ArrayList<>();
        // 리스트는 딕셔너리 클래스 객체를 넣은 리스트가 될거야
        mAdapter = new Adapter_Custom(mArrayList);
       // 내 어댑터는 그 딕셔너리 클래스 객체를 넣은 리스트 데이터 원본을 받는 어댑터야
        mRecylcerView.setAdapter(mAdapter);
        // 리사이클러뷰에다 그 어댑터로 보여질 수 있는 형태가 된 데이터를 출력할거야

        Button buttonInsert = (Button)findViewById(R.id.button_insert);

        buttonInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count++;
                Dictionary data = new Dictionary(count+"", "Apple"+count, "한글"+count);
                mArrayList.add(data);
                mAdapter.notifyDataSetChanged();

            }
        });


    }
}