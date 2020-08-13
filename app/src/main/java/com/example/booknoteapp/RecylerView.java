package com.example.booknoteapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class RecylerView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyler_view);

        //리사이클러뷰에 표시될 데이터 리스트를 생성한다.
        ArrayList<String> list = new ArrayList<>();
        for(int i=0; i<100; i++){
            list.add(String.format("APPLE %d",i+1));
            //리사이클러뷰에 순서대로 APPLE 0, 1, 2, 3,... 이 나올 것이다.
        }

        //리사이클러뷰에 LinearLayoutManager객체를 지정한다
        RecyclerView recyclerView = findViewById(R.id.recycler_test);
        //리사이클러뷰에 보일 아이템을 구성했던 레이아웃에서 리사이클러 뷰에 해당하는 아이디를 참조

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //리사이클러뷰에 SimpleTextAdapter 객체를 지정
        SimpleTextAdapter adapter = new SimpleTextAdapter(list);

        //이 액티비티에 지정된 비어있는 리사이클러 뷰 컨테이너에
        //이 자바 클래스에서 만든 리스트를 넣어서 뽑아낸 어댑터를 연결해준다
        recyclerView.setAdapter(adapter);



    }
}