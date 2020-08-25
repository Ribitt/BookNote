package com.example.booknoteapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.spans.DotSpan;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class BookCalendar extends AppCompatActivity {

    long now = System.currentTimeMillis();
    Date date = new Date(now);
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");
    String time = dateFormat.format(date);
    Date datePicked;

    MaterialCalendarView calendarView;

    LinearLayout layout;
    Animation alpha;

    RecyclerView recyclerView;
    TextView tv_youRead;
    TextView tv_dayOfTheMonth;
    ArrayList<CalendarDay> calendarDays = new ArrayList<>();
    Calendar calendar = Calendar.getInstance();



    //하단 메뉴 버튼

    Button btn_toDrawer;
    Button btn_toEssay;
    Button btn_toCalender;
    Button btn_toHome;
    /////////////////////////////

    SharedPreferences userPref;
    ArrayList<Dictionary_pageLog> pageLogArrayList;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calander);

        initialize();
        allListener();

    }

    @Override
    protected void onResume() {
        super.onResume();

        getPageLogArrayFromPref();
        //저장된 페이지 로그 불러오기

        try {
            datePicked = dateFormat.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }//오늘 시간을 데이트 양식으로 가져온다

        try {
            showRecycler(datePicked);
        } catch (ParseException e) {
            e.printStackTrace();
        }//오늘 날짜에 해당하는 로그를 리사이클러뷰에 띄운다


        try {
            makeCalendarDayList();
        } catch (ParseException e) {
            e.printStackTrace();
        } // 페이지 로그가 있는 날을 리스트로 쭉 만들기
        calendarSetting();
        //달력 세팅 (토,일 색상, 선택된 날 색상, 오늘 글자크기, 페이지 로그가 있는 날에 점찍기
        //클릭한 날짜 리사이클러뷰 보이기

    }

    private void makeCalendarDayList() throws ParseException {
        ArrayList<Date> dates = new ArrayList<>();

        for(Dictionary_pageLog pageLog : pageLogArrayList){
            dates.add(dateFormat.parse(pageLog.getDate()));
            //String인 날짜를 date포맷으로 바꾼뒤에 어레이 리스트로 만들어준다
        }

        for(Date date : dates){
          calendarDays.add(CalendarDay.from(date));
        }


    }

    private void initialize() {

        //유저 쉐어드 가져오기
        String userEmail = getSharedPreferences("users",MODE_PRIVATE).getString("currentUser","");
        userPref = getSharedPreferences(userEmail,MODE_PRIVATE);

        //리사이클러뷰 참고값 & 가로 리니어 레이아웃 셋팅
        recyclerView = findViewById(R.id.recycler_logOnCalendar);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(),RecyclerView.HORIZONTAL,false));

        tv_dayOfTheMonth = findViewById(R.id.tv_dayOfTheMonth);
        calendarView = findViewById(R.id.calendarView);
        //총 읽은 페이지 뷰
        tv_youRead = findViewById(R.id.tv_youRead);

        layout = findViewById(R.id.layout_tvAndRecycler);
        alpha = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.alpha);

        //아래 메뉴 버튼
        btn_toDrawer =findViewById(R.id.btn_to_drawer);
        btn_toCalender =findViewById(R.id.btn_to_calender);
        btn_toEssay =findViewById(R.id.btn_to_assay);
        btn_toHome = findViewById(R.id.btn_to_home);


    }

    private void allListener() {


        //메뉴 버튼 리스너

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


    }

    private void getPageLogArrayFromPref(){
        Gson gson = new Gson();
        String json = userPref.getString("pageLog","EMPTY");

        Type type = new TypeToken<ArrayList<Dictionary_pageLog>>() {
        }.getType();
        if(!json.equals("EMPTY")){
            pageLogArrayList = gson.fromJson(json,type);
        }
    }

    private void calendarSetting(){
        calendarView.state().edit()
                .setFirstDayOfWeek(Calendar.MONDAY)
                .setMinimumDate(CalendarDay.from(1992,6,28))
                .setMaximumDate(CalendarDay.from(2030,12,30))
                .setCalendarDisplayMode(CalendarMode.MONTHS)
                .commit();

        calendarView.addDecorators(
                new SundayDecorator(),
                new SaturdayDecorator(),
                new oneDayDecorator(),
                new logDayDecorator());
        //일요일 빨간색 토요일 파란색, 오늘은 글자 크고 굵게

        calendarView.setSelectedDate(CalendarDay.today());
        //오늘 날짜에 기본적으로 동글뱅이 가 있도록

        calendarView.setDynamicHeightEnabled(true);
        //주수에 따라 높이 달라지는 것에 맞춰서 총 높이 정해지기


        calendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
              //  Toast.makeText(getApplicationContext(), date.toString(), Toast.LENGTH_LONG).show();

                Date datePicked = date.getDate();
                String strDatePicked = dateFormat.format(datePicked);
                //선택된 날짜를 내가 사용하는 데이트 포맷으로 바꾼다

                tv_dayOfTheMonth.setText(strDatePicked);
                //날짜를 텍스트뷰에 띄워준다

                //해당 날짜에 존재하는 로그를 정리해서 책 표지, 제목, 읽은 페이지수 띄우는 리사이클러뷰를 만들어야 한다.

                try {
                    getLogOfThisDay(datePicked);
                } catch (ParseException e) {
                    e.printStackTrace();
                } // 로그가 있는 날짜를 모아두는 리스트를 만든다 -> 달력에 점찍기 용

                try {
                    showRecycler(datePicked);
                } catch (ParseException e) {
                    e.printStackTrace();
                } // 리사이클러에 해당 날짜에 존재하는 로그를 띄운다

                recyclerView.startAnimation(alpha);


            }
        });


    }

    private void showRecycler(Date datePicked) throws ParseException {

        ArrayList<Dictionary_pageLog> mList = new ArrayList<>();

        mList.clear();
        //이전에 클릭한 날짜와 동일한 날짜에 쓰인 페이지 로그가 저장되어있을 수 있기 때문에 비워준다.

        for(Dictionary_pageLog pageLog : pageLogArrayList){
            Date dateFromLog = dateFormat.parse(pageLog.getDate());
            if(dateFromLog.compareTo(datePicked)==0){
                mList.add(pageLog);
            }
        }//전체 리스트에서 해당 날짜에 쓰인 로그만 뽑아서 모으기

        for(int j=0; j<mList.size(); j++){
            for(int i=j+1; i<mList.size();i++){
                if(mList.get(j).getDictionary_book().getTitle().equals(mList.get(i).getDictionary_book().getTitle())){
                    mList.get(j).readPageNum =  mList.get(0).readPageNum+mList.get(i).readPageNum;
                    mList.remove(i);
                }
            }
        }//동일한 제목은 숫자 합치고 하나만 나오도록 하기


        Adapter_LogOnCalendar adapter_logOnCalendar = new Adapter_LogOnCalendar(mList);
        recyclerView.setAdapter(adapter_logOnCalendar);
        //만든 리스트로 리사이클러뷰 띄우기

        int readPagesOnThisDay=0;
        for(Dictionary_pageLog pageLog : mList){
            readPagesOnThisDay += pageLog.readPageNum;
        }//리스트에 있는 전체 페이지 수를 다 더한다

        if(mList.size()>0){
            tv_youRead.setText(String.valueOf(readPagesOnThisDay)+"페이지를 읽었습니다");
            tv_youRead.setVisibility(View.VISIBLE);
            //있는 날에는 몇 페이지나 읽었는지 세서 띄운다
        }else{
            tv_youRead.setVisibility(View.INVISIBLE);
            //없는 날에는 글자 없애버리시오
        }



    }

    private void getLogOfThisDay(Date datePicked) throws ParseException {

        Date dateFromLog;

        for(int i=0; i<pageLogArrayList.size(); i++){

            dateFromLog = dateFormat.parse(pageLogArrayList.get(i).getDate());
            int compare = datePicked.compareTo(dateFromLog);
            if(compare==0){
                Log.d("동일한 날짜에 로그가 있다", pageLogArrayList.get(i).toString());

            }
        }
    }

    public class logDayDecorator implements DayViewDecorator{
        private CalendarDay date;
        @Override
        public boolean shouldDecorate(CalendarDay day) {
            for(CalendarDay calendarDay : calendarDays){
                date = calendarDay;
            }
            return calendarDays.contains(day);
        }

        @Override
        public void decorate(DayViewFacade view) {
            view.addSpan(new DotSpan(7,getColor(R.color.yellowGreen)));
        }
    }

    public class SundayDecorator implements DayViewDecorator{

        private final Calendar calendar = Calendar.getInstance();

        @Override
        public boolean shouldDecorate(CalendarDay day) {
            day.copyTo(calendar);
            int weekDay = calendar.get(Calendar.DAY_OF_WEEK);
            return weekDay == Calendar.SUNDAY;
        }

        @Override
        public void decorate(DayViewFacade view) {
            view.addSpan(new ForegroundColorSpan(getColor(R.color.myRed)));

        }
    }
    public class SaturdayDecorator implements DayViewDecorator{

        private final Calendar calendar = Calendar.getInstance();

        @Override
        public boolean shouldDecorate(CalendarDay day) {
            day.copyTo(calendar);
            int weekDay = calendar.get(Calendar.DAY_OF_WEEK);
            return weekDay == Calendar.SATURDAY;
        }

        @Override
        public void decorate(DayViewFacade view) {
            view.addSpan(new ForegroundColorSpan(getColor(R.color.myBlue)));

        }
    }



    private class oneDayDecorator implements DayViewDecorator{
        private CalendarDay date;

        public oneDayDecorator(){
            date = CalendarDay.today();
        }

        @Override
        public boolean shouldDecorate(CalendarDay day) {

            return date!= null && day.equals(date);
        }

        @Override
        public void decorate(DayViewFacade view) {
            view.addSpan(new StyleSpan(Typeface.BOLD));
            view.addSpan(new RelativeSizeSpan(1.2f));

            //view.addSpan(new ForegroundColorSpan(getColor(R.color.green)));
            //view.addSpan(new BackgroundColorSpan(getColor(R.color.myYellow)));

        }
    }



    @Override
    protected void onStart() {
        super.onStart();


    }

    @Override
    protected void onRestart() {
        super.onRestart();


    }



}