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
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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


    MaterialCalendarView calendarView;

    RecyclerView recyclerView;

    TextView tvNum_calender_pageSum;

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

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");


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

        try {
            makeCalendarDayList();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        calendarSetting();
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
        recyclerView = findViewById(R.id.recycler_calendar);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(),RecyclerView.HORIZONTAL,false));

        tv_dayOfTheMonth = findViewById(R.id.tv_dayOfTheMonth);
        calendarView = findViewById(R.id.calendarView);
        //총 읽은 페이지 뷰

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
                Toast.makeText(getApplicationContext(), date.toString(), Toast.LENGTH_LONG).show();


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
                }
            }
        });


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
            view.addSpan(new DotSpan(5,getColor(R.color.green)));
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