package com.example.booknoteapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Essay extends AppCompatActivity {

    //현재시간 가져오는 메소드
    long now = System.currentTimeMillis();
    Date date = new Date(now);
    SimpleDateFormat mFormat = new SimpleDateFormat("yyyy.MM.dd");
    String time = mFormat.format(date);
    //현재시간 가져오는 메소드

   Button btn_writeEssay;

   Boolean myEssayOnly = false;
   Button btn_myEssay;
   Button btn_everyEssay;


    String currentEmail;

   TextView tv_warning_noEssay;
   Animation alpha;

   RecyclerView recyclerView;
   Adapter_Essay adapter_essay;
   Dictionary_book dictionary_book;
   Dictionary_Essay dictionary_essay;

    ArrayList<Dictionary_Essay> emptyList = new ArrayList<>();

   ArrayList<Dictionary_Essay> wholeEssayList = new ArrayList<>();
    ArrayList<Dictionary_Essay> myEssayList = new ArrayList<>();
    ArrayList<Dictionary_Essay> openEssayList = new ArrayList<>();
    ArrayList<Dictionary_Essay> wholeExceptMyEssayList = new ArrayList<>();

//    AlphaAnimation alphaAnimation;

   SharedPreferences userPref;
   SharedPreferences.Editor userPrefEditor;

   SharedPreferences devPref;
   SharedPreferences.Editor devPrefEditor;

    //하단 메뉴 버튼

    Button btn_toDrawer;
    Button btn_toEssay;
    Button btn_toCalender;
    Button btn_toHome;

    /////////////////////////////
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_essay);

        initialize();
        allListener();
    }

    private void initialize(){

//        alphaAnimation = new AlphaAnimation(0,1);
//        alphaAnimation.setDuration(800);

        alpha = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.alpha);
        devPref = getSharedPreferences("users", Context.MODE_PRIVATE);
        devPrefEditor = devPref.edit();

        currentEmail = devPref.getString("currentUser","");
        userPref = getSharedPreferences(currentEmail,this.MODE_PRIVATE);
        userPrefEditor = userPref.edit();

        btn_writeEssay = findViewById(R.id.btn_write_essay);
        btn_myEssay = findViewById(R.id.btn_myEssay);
        btn_everyEssay = findViewById(R.id.btn_everyEssay);

        tv_warning_noEssay = findViewById(R.id.tv_warning_noEssay);

        recyclerView = findViewById(R.id.recycler_essay);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //아래 메뉴 버튼
        btn_toDrawer =findViewById(R.id.btn_to_drawer);
        btn_toCalender =findViewById(R.id.btn_to_calender);
        btn_toEssay =findViewById(R.id.btn_to_assay);
        btn_toHome = findViewById(R.id.btn_to_home);

        // 하단 메뉴바 버튼 선언 끝

    }


    private void allListener(){

        //내 에세이만 보기 버튼
        btn_myEssay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myEssayOnly = true;
                btn_everyEssay.setBackground(getDrawable(R.drawable.button_grey));
                btn_myEssay.setBackground(getDrawable(R.drawable.button_yellowgreen));
                getWholeArrayFromPref(devPref);
                getUserEssayOnly();
                showRecycler(myEssayList,wholeEssayList);

            }
        });
        //내 에세이 버튼 끝

        //모두의 에세이 보기 버튼
        btn_everyEssay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myEssayOnly =false;
                btn_everyEssay.setBackground(getDrawable(R.drawable.button_yellowgreen));
                btn_myEssay.setBackground(getDrawable(R.drawable.button_grey));
                getWholeArrayFromPref(devPref);
                getOpenList();
                //공개된 에세이만 따로 저장한다
                showRecycler(openEssayList, wholeEssayList);
                //어댑터에 공개 에세이와 전체 에세이를 보낸다.

            }
        });
        //모두의 에세이

        //에세이 쓰기 버튼
        btn_writeEssay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             //  makeFakeEssay();
                Intent intent = new Intent(getApplicationContext(), AddEssayBook.class);
                startActivity(intent);

            }
        });

        //에세이 쓰기 버튼 끝

        // 하단 메뉴바 클릭 이벤트
        btn_toHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainHome.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
            }
        });

        btn_toDrawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), DrawerTap.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
            }
        });

        btn_toCalender.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent intent = new Intent(getApplicationContext(), BookCalendar.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                        startActivity(intent);
                    }
                }
        );

        btn_toEssay.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent intent = new Intent(getApplicationContext(),Essay.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                        startActivity(intent);
                    }
                }
        );
        // 하단 메뉴바 클릭 이벤트 끝
    }//올리스너 끝

    private void getUserEssayOnly(){

        myEssayList.clear();
        for(int i=0; i<wholeEssayList.size(); i++){
            if(wholeEssayList.get(i).getUserEmail().equals(currentEmail)){
                myEssayList.add(wholeEssayList.get(i));
            }else {
                wholeExceptMyEssayList.add(wholeEssayList.get(i));
            }
        }
    }

    private void makeFakeEssay(){

        Gson gson = new Gson();
        String json = devPref.getString("essay","EMPTY");
        ArrayList<Dictionary_Essay> wholeList = new ArrayList<>();

        Type type = new TypeToken<ArrayList<Dictionary_Essay>>() {
        }.getType();
        if(!json.equals("EMPTY")){
           wholeList = gson.fromJson(json,type);
        }

        dictionary_book = new Dictionary_book("read","와일드","셰릴 스트레이드");
        dictionary_book.setBookCover(BitmapFactory.decodeResource(getApplicationContext().getResources(),R.drawable.book_jieun));
        dictionary_essay = new Dictionary_Essay(dictionary_book,true);
        dictionary_essay.setDate(time);
        dictionary_essay.setOpen(true);
        dictionary_essay.setNickname("노바");
        dictionary_essay.setUserEmail("bittnuri@gmail.com");
        dictionary_essay.setEssayTitle("안보여야 하는 에세이");
        dictionary_essay.setEssayContent("이 책을 읽으면 될 것 같다. 미친듯이 힘들어서 이이이이이이이디디디디디댜댣" +
                "ㄷㄹㄷㅀㄷㅎㄷㅎㄷㅎㄷㅎㄷㅎㄷㅎㄷㅎㄷㅎㄷㅎㄷㅎㄷㅎㄷㅎㅎㄷㅎㄷㅎㄷㅎㄷㅎㄷㅎㄷㅎㄷ" +
                "ㅎㄷㅎㄷㅎㄷㅎㄷㅎㄷㅎㄷㅎㄷㅎㄷㅎㄷㅎㄷㅎㄷㅎㄷㅎㄷㅎㄷㅎㅎㄷ다다다다다ㅏㄷㄷㄷㄷㄷㄷㄷㄷ" +
                "ㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷ" +
                "ㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷ" +
                "너무재밌다");
      wholeList.add(dictionary_essay);

        json = gson.toJson(wholeList);
        devPrefEditor.putString("essay",json);
        devPrefEditor.commit();

    }

    @Override
    protected void onPostResume() {
        super.onPostResume();

        getWholeArrayFromPref(devPref);
        //전체 에세이 리스트를 받아온다. 각 에세이는 객체는 지금 전체 리스트에서의 인덱스값을 저장해준다.
        if(myEssayOnly){
            getUserEssayOnly();
            showRecycler(myEssayList,wholeEssayList);
            //혹시 내 에세이가 하나도 없으면 없다고 글자 띄워주기
            if(myEssayList.size()==0){
                tv_warning_noEssay.setVisibility(View.VISIBLE);
            }else if(myEssayList.size()>0){
                tv_warning_noEssay.setVisibility(View.GONE);
            }

        }else{
            getOpenList();
            //공개된 에세이만 따로 저장한다
            showRecycler(openEssayList, wholeEssayList);
           //어댑터에 공개 에세이와 전체 에세이를 보낸다.
        }

    }


    private void showRecycler(ArrayList<Dictionary_Essay> showList, ArrayList<Dictionary_Essay> wholeEssayList){
        adapter_essay = new Adapter_Essay(showList, wholeEssayList);
        recyclerView.setAdapter(adapter_essay);
        recyclerView.startAnimation(alpha);
       // recyclerView.setAnimation(alphaAnimation);

    }

    private void getOpenList(){

        openEssayList.clear();
        for(int i=0; i<wholeEssayList.size(); i++){
            if(wholeEssayList.get(i).getOpen()){
                openEssayList.add(wholeEssayList.get(i));
                //공개 설정된 녀석은 공개 에세이 리스트로 넣어준다.
            }
        }

    }


    private void getWholeArrayFromPref(SharedPreferences pref) {
        Gson gson = new Gson();

//        //프레프 비우기용
//        String json = gson.toJson(emptyList);
//        userPrefEditor.putString("essay",json);
//        userPrefEditor.commit();
//        devPrefEditor.putString("essay",json);
//        devPrefEditor.commit();

        wholeEssayList.clear();
        String json = pref.getString("essay","EMPTY");

        Type type = new TypeToken<ArrayList<Dictionary_Essay>>() {
        }.getType();
        if(!json.equals("EMPTY")){
            wholeEssayList = gson.fromJson(json,type);
            Log.d("전체 리스트 길이 : ", String.valueOf(wholeEssayList.size()));
            //전체 리스트를 가져온다
            for(int i=0; i<wholeEssayList.size(); i++){
                wholeEssayList.get(i).setPositionInWhole(i);
                //각 에세이에 원래 전체 리스트에서의 인덱스를 저장한다.
            }
            //전체 리스트에서 각 에세이가 어느 위치였는지 인덱스 값을 준다
        }

    }


}