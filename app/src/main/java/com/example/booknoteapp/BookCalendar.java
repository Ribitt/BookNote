package com.example.booknoteapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.util.Calendar;

public class BookCalendar extends AppCompatActivity {


    MaterialCalendarView calendarView;
    TextView tvNum_calender_pageSum;
    //하단 메뉴 버튼

    Button btn_toDrawer;
    Button btn_toEssay;
    Button btn_toCalender;
    Button btn_toHome;

    /////////////////////////////
    ImageButton btn_to_bookdetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calander);


        initialize();
        allListener();
        calendarSetting();




    }


    private void initialize() {

        calendarView = findViewById(R.id.calendarView);
        //총 읽은 페이지 뷰

        //아래 메뉴 버튼
        btn_toDrawer =findViewById(R.id.btn_to_drawer);
        btn_toCalender =findViewById(R.id.btn_to_calender);
        btn_toEssay =findViewById(R.id.btn_to_assay);
        btn_toHome = findViewById(R.id.btn_to_home);


    }

    private void allListener() {

        calendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {

            }
        });


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
                new oneDayDecorator());
        //일요일 빨간색 토요일 파란색, 오늘은 글자 크고 굵게

        calendarView.setSelectedDate(CalendarDay.today());
        //오늘 날짜에 기본적으로 동글뱅이 가 있도록

        calendarView.setDynamicHeightEnabled(true);
        //주수에 따라 높이 달라지는 것에 맞춰서 총 높이 정해지기


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

    @Override
    protected void onResume() {
        super.onResume();


    }

}