package com.example.booknoteapp;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Bookdetail_note_byPage extends AppCompatActivity  {

    //유저가 작성한 독서노트 페이지&노트 프리뷰
    TextView tv_notePreview;
    TextView tv_bookPage;
    TextView tv_p;
    //노트 추가 버튼
    Button btn_addNote;
    //하단 메뉴 버튼
    Button btn_to_calendar;
    Button btn_to_drawer;
    Button btn_to_essay;
    Button btn_to_setting;
    Button btn_to_stat;
    /////////////////////////////

    int myYellow;
    int grey;

    private Context mContext;
    private TextView tv_forTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_log__pages);

//        String text = PreferenceManager.getString(this,"rebuild");
//        if(text.equals("")){
//            text = "저장된 데이터가 없습니다.";
//            PreferenceManager.setString(this,"rebuild","암것도 없습니다.");
//        }



        //노트 추가버튼
        //btn_addNote = (Button)findViewById(R.id.btn_bookdetail_addNote);
        //노트 프리뷰,페이지 뷰

//        tv_bookPage = (TextView)findViewById(R.id.tv_bookPage);
//        tv_p = (TextView)findViewById(R.id.tv_p);

        //노트 추가 버튼 클릭 이벤트
        btn_addNote.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getApplicationContext(), AddNote.class);
                        startActivityForResult(intent, 1);
                    }
                }
        );
        //노트 추가 버튼 클릭 이벤트 끝

        //노트 글자 클릭 이벤트
        tv_notePreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Note.class);
                startActivity(intent);
            }
        });

        //노트 글자 클릭 이벤트 끝


        // 하단 메뉴바 버튼 선언
        btn_to_calendar = (Button)findViewById(R.id.btn_to_calender);
        btn_to_drawer = (Button)findViewById(R.id.btn_to_drawer);
        btn_to_essay = (Button)findViewById(R.id.btn_to_assay);
        btn_to_setting = (Button)findViewById(R.id.btn_to_setting);
        btn_to_stat = (Button)findViewById(R.id.btn_to_stat);
        // 하단 메뉴바 버튼 선언 끝

        // 하단 메뉴바 클릭 이벤트
        btn_to_calendar.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        btn_to_calendar.setBackgroundColor(myYellow);
                        Intent intent = new Intent(getApplicationContext(), Calendar.class);
                        startActivity(intent);
                    }
                }
        );
        btn_to_drawer.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                    }
                }
        );
        btn_to_essay.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getApplicationContext(), Essay.class);
                        startActivity(intent);
                    }
                }
        );
        btn_to_setting.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
//                        Intent intent = new Intent(getApplicationContext(),SettingsActivity.class);
//                        startActivity(intent);
                    }
                }
        );

        btn_to_stat.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getApplicationContext(), Stat.class);
                        startActivity(intent);
                    }
                }
        );

        // 하단 메뉴바 클릭 이벤트 끝

//        tv_forTest = (TextView)findViewById(R.id.tv_forTest);
//        tv_forTest.setText(text);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();

        btn_to_calendar.setBackgroundColor(grey);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==1){
            String message = data.getStringExtra("MESSAGE");
            String page = data.getStringExtra("PAGE");
            Integer color = data.getIntExtra("COLOR", R.color.myBlack);
            //뒤에 R.color.myBlack자리는 만약 인텐트로 넘어온 값이 없을 경우 디폴트로 정해둘 값이다.
            tv_p.setText("P. ");
            tv_notePreview.setText(message);
            tv_bookPage.setText(page);int blue = android.R.color.holo_blue_dark;
            tv_notePreview.setTextColor(color);
        }
    }
}


//버튼을 눌러서 에딧텍스트 값을 텍스트뷰에 집어넣는 예제
//    Button sampleBtn = (Button)findViewById(R.id.btn_note_done);
//        sampleBtn.setOnClickListener(
//                new View.OnClickListener() {
//@Override
//public void onClick(View view) {
//        TextView textView = (TextView)findViewById(R.id.tv_sample);
//        EditText editText = (EditText)findViewById(R.id.editText_sample);
//        textView.setText(editText.getText());
//        }
//        }
//        );