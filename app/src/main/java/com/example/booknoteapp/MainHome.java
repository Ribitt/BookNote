package com.example.booknoteapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class MainHome extends AppCompatActivity {

    TextView nickname;

    Button btn_toDrawer;
    Button btn_toEssay;
    Button btn_toCalender;
    Button btn_toHome;
    ImageView btn_toSetting;

    TextView tv_yearAndMonth;
    TextView tv_home_monthly_read_pages;
    TextView tv_home_monthly_written_notes;
    TextView tv_yearly_goal_pages;
    ProgressBar progressBar_home_yearly;
    TextView tv_goal_percentage;

   RecyclerView recentReadingRecycler;
    Adapter_Reading adapter_reading=null;
    ArrayList<Dictionary_book> readingList;

    RecyclerView recentNoteRecycler;
    Adapter_NoteForHome adapter_note=null;
    ArrayList<Dictionary_note> noteArrayList;

    String yearAndMonth;

    SharedPreferences userPref;
    SharedPreferences.Editor userEditor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initialize();
        allListener();
        getTime();

    }


    @Override
    protected void onResume() {
        super.onResume();

        getReadingPrefToArray();
        showReadingRecycler();
        getNoteArrayFromPref();
        showNoteRecycler();
        getPageLogNumFromPref();
        getPageGoalFromPref();
    }

    private void initialize() {


        //관리자용 쉐어드를 불러온다
        SharedPreferences userPref = getSharedPreferences("users", Activity.MODE_PRIVATE);
        //currentUser에 지금 로그인한 회원의 이메일이 저장되어있다.
        String userEmail = userPref.getString("currentUser","");
        //그 유저 이메일이 패키지 이름인 된 저장소를 불러온다.
        this.userPref = getSharedPreferences(userEmail,MODE_PRIVATE);
        //저장되어있는 닉네임을 불러온다
        String nick = this.userPref.getString("nickname","");
        userEditor = this.userPref.edit();

        nickname = findViewById(R.id.tv_home_nickname);
        tv_yearAndMonth = findViewById(R.id.tv_home_yearAndMonth);
        tv_home_monthly_read_pages = findViewById(R.id.tv_home_monthly_read_pages);
        tv_home_monthly_written_notes = findViewById(R.id.tv_home_monthly_written_notes);

        tv_yearly_goal_pages = findViewById(R.id.tv_yearly_goal_pages);
        progressBar_home_yearly = findViewById(R.id.progressBar_home_yearly);
        tv_goal_percentage = findViewById(R.id.tv_goal_percentage);

        btn_toSetting = findViewById(R.id.btn_to_my_setting);


        //아래 메뉴 버튼
        btn_toDrawer =findViewById(R.id.btn_to_drawer);
        btn_toCalender =findViewById(R.id.btn_to_calender);
        btn_toEssay =findViewById(R.id.btn_to_assay);
        btn_toHome = findViewById(R.id.btn_to_home);
        btn_toSetting = findViewById(R.id.btn_to_my_setting);

        //리사이클러뷰
        recentReadingRecycler = findViewById(R.id.recycler_reading);
        recentNoteRecycler = findViewById(R.id.recycler_recent_notes);

        nickname.setText(nick);

    }//이니셜라이징 끝

    private void allListener() {

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

        btn_toSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ///////////////////////////////////세팅화면 만들고 수정 필요
                Intent intent = new Intent(getApplicationContext(),MySetting.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
            }
        });

    }//올리스너 끝


    //현재시간 가져오는 메소드
    private void getTime() {
        Calendar calendar = Calendar.getInstance();
        String year = String.valueOf( calendar.get(Calendar.YEAR));
        String month = String.valueOf(calendar.get(Calendar.MONTH)+1);
        yearAndMonth = year+"\n"+month+"월";
        tv_yearAndMonth.setText(yearAndMonth);

    }

    //목표 페이지 가져오기
    private void getPageGoalFromPref(){
        int pageGaol = userPref.getInt("pageGoal",10000);
        progressBar_home_yearly.setMax(pageGaol);
        //프로그레스 바 최대치로 정해준다

        DecimalFormat format = new DecimalFormat("###,###");
        String formatString = format.format(pageGaol);
        tv_yearly_goal_pages.setText(formatString+" 페이지");
    }
    //목표 페이지 가져오기 끝



    //현재시간 가져오는 메소드 끝
    private void getReadingPrefToArray() {
        Gson gson = new Gson();
        String json = userPref.getString("reading","EMPTY");
        Type type = new TypeToken<ArrayList<Dictionary_book>>() {
        }.getType();
        if(!json.equals("EMPTY")){
            readingList= gson.fromJson(json,type);
        }

    }

    private void getNoteArrayFromPref() {
        Gson gson = new Gson();
//       //노트 비우기
//        ArrayList<Dictionary_note> emptyNote = new ArrayList<>();
//        String json1 = gson.toJson(emptyNote);
//        userEditor.putString("note",json1);
//        userEditor.commit();

        String json = userPref.getString("note","EMPTY");
        Type type = new TypeToken<ArrayList<Dictionary_note>>() {
        }.getType();
        if(!json.equals("EMPTY")){
            noteArrayList= gson.fromJson(json,type);
        }
        tv_home_monthly_written_notes.setText(String.valueOf(noteArrayList.size()));
    }

    private void getPageLogNumFromPref(){
        Gson gson = new Gson();
        String json = userPref.getString("pageLog","EMPTY");
        ArrayList<Dictionary_pageLog> allLogList = new ArrayList<>();
        Type type = new TypeToken<ArrayList<Dictionary_pageLog>>() {
        }.getType();
        if(!json.equals("EMPTY")){
            allLogList= gson.fromJson(json,type);
        }//페이지 로그 전체를 가져온다.

        int pageSum=0;

        for(int i=0; i<allLogList.size();i++){
            pageSum = pageSum + allLogList.get(i).getReadPageNum();
        }

        tv_home_monthly_read_pages.setText(String.valueOf(pageSum));

    }

    private void showReadingRecycler() {
        adapter_reading = new Adapter_Reading(readingList,"drawer");
        recentReadingRecycler.setAdapter(adapter_reading);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recentReadingRecycler.setLayoutManager(layoutManager);

    }

    private void showNoteRecycler() {
        adapter_note = new Adapter_NoteForHome(noteArrayList);
        recentNoteRecycler.setAdapter(adapter_note);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recentNoteRecycler.setLayoutManager(layoutManager);
    }



}