package com.example.booknoteapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.DecimalFormat;

public class MySetting extends AppCompatActivity {

    SharedPreferences devPref;
    SharedPreferences.Editor devEditor;

    SharedPreferences userPref;
    SharedPreferences.Editor userEditor;


    Toolbar toolbar;
    ActionBar actionBar;

    LinearLayout btn_pageGoal;
    TextView btn_changeNick;
    TextView btn_changePassword;
    TextView btn_logOut;
    TextView btn_signOut;
    TextView btn_sendDevEmail;
    TextView tv_pageGoal;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_setting);

        initialize();
        allListener();
    }

    private void initialize(){

        devPref = getSharedPreferences("users",MODE_PRIVATE);
        devEditor = devPref.edit();

        String userEmail = devPref.getString("currentUser","");
        userPref = getSharedPreferences(userEmail,MODE_PRIVATE);
        userEditor = userPref.edit();

        //////툴바 적용하기
        toolbar = (Toolbar)findViewById(R.id.app_toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.green));
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("독서노트 설정");

        btn_pageGoal = findViewById(R.id.btn_pageGoal);
        tv_pageGoal = findViewById(R.id.tv_pageGoal);
        btn_changeNick = findViewById(R.id.btn_changeNick);
        btn_changePassword = findViewById(R.id.btn_changePassword);
        btn_logOut = findViewById(R.id.btn_logOut);
        btn_signOut = findViewById(R.id.btn_signOut);
        btn_sendDevEmail = findViewById(R.id.btn_sendDevEmail);


    }//이니셜라이즈

    private void allListener(){

        //목표 페이지 변경하기
        btn_pageGoal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pageGoalDialog().show();
            }
        });

        //목표 페이지 변경하기 끝

        //개발자에게 이메일 보내기
        btn_sendDevEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.setType("plain/text");
                String[] address = {"bittnuri@gmail.com"};
                emailIntent.putExtra(Intent.EXTRA_EMAIL, address);
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "[독서노트 문의메일]");
                emailIntent.putExtra(Intent.EXTRA_TEXT,"개발자에게 문의하고 싶은 내용을 적어 보내주세요.");
                startActivity(emailIntent);
            }
        });
        //개발자에게 이메일 보내기 끝



    }//올 리스너 끝

    private AlertDialog.Builder pageGoalDialog(){

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(MySetting.this);
        dialogBuilder.setTitle("독서 목표 페이지 변경");
        dialogBuilder.setMessage("1년 동안 읽을 목표 페이지를 정해주세요");

        final EditText et = new EditText(MySetting.this);
        et.setHint("10,000");
        et.setInputType(InputType.TYPE_CLASS_NUMBER);
        dialogBuilder.setView(et);
        dialogBuilder.setPositiveButton("변경하기", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                DecimalFormat format = new DecimalFormat("###,###");
                int number = Integer.parseInt(et.getText().toString());
                String formatString = format.format(number);
                tv_pageGoal.setText(formatString+" 페이지");
                //받은 숫자를 3번째 자리마다 , 찍게 해서 띄우기

                userEditor.putInt("pageGoal",number);
                userEditor.commit();
                //받은 숫자를 유저 프레프에 저장하기

                dialogInterface.dismiss();
            }
        });
        dialogBuilder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        return dialogBuilder;

    }


}