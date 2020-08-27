package com.example.booknoteapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.DecimalFormat;

public class MySetting extends AppCompatActivity {

    SharedPreferences devPref;
    SharedPreferences.Editor devEditor;

    SharedPreferences userPref;
    SharedPreferences.Editor userEditor;

    String userEmail;
    String NICKNAMES="nicknames";
    String nickname;


    Toolbar toolbar;
    ActionBar actionBar;
    Boolean isNewNick = false;

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

        userEmail = devPref.getString("currentUser","");
        userPref = getSharedPreferences(userEmail,MODE_PRIVATE);
        nickname = userPref.getString("nickname","");
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

        int pageGoal = userPref.getInt("pageGoal",10000);
        DecimalFormat format = new DecimalFormat("###,###");
        String formatString = format.format(pageGoal);
        tv_pageGoal.setText(formatString+" 페이지");



    }//이니셜라이즈

    private void allListener(){

        //닉네임 변경
        btn_changeNick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ChangeNickname.class);
                startActivity(intent);
            }
        });
        //닉네임 변경 끝

        //회원 탈퇴
        btn_signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signOutDialog().show();
            }
        });
        //회원 탈퇴 끝

        //비밀번호 변경
        btn_changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ChangePassword.class);
                startActivity(intent);
            }
        });
        //비밀번호 변경 끝

        //로그아웃 하기
        btn_logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logOutDialog().show();
            }
        });
        //로그아웃 하기 끝

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




    //회원 탈퇴 다이얼로그
    private AlertDialog.Builder signOutDialog(){

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(MySetting.this);
        dialogBuilder.setTitle("회원 탈퇴");
        dialogBuilder.setMessage("정말 탈퇴하시겠습니까?");


        dialogBuilder.setPositiveButton("탈퇴하기", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                //유저 이메일, 비밀번호 지우기
                Log.d("지금 유저 이메일은 ", userEmail);
                Log.d("지금 유저 닉네임은", userPref.getString("nickname",""));

                //유저 이메일과 비밀번호 지우기
                devEditor.remove(userEmail);

                //유저 닉네임 지우기
//                String nickname = userPref.getString("nickname","");
                String allNicknames = devPref.getString(NICKNAMES,"");
                allNicknames = allNicknames.replaceAll("#"+nickname,"");
                Log.d("닉네임 전체",allNicknames);

                //닉네임 지운 리스트 다시 devPref에 저장
                devEditor.putString(NICKNAMES,allNicknames);

                //혹시 자동 로그인 되어 있었다면 바꾸기
                devEditor.putBoolean("autoLogin",false);

                devEditor.commit();

                dialogInterface.dismiss();
                finish();
                Intent intent = new Intent(getApplicationContext(), Login.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                //지금 부르는 액티비티를 제외하고 전부 죽임
                startActivity(intent);

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
    //회원 탈퇴 다이얼로그 끝

    //페이지 목표 다이얼로그
    private AlertDialog.Builder pageGoalDialog(){

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(MySetting.this);
        dialogBuilder.setTitle("독서 목표 페이지 변경");
        dialogBuilder.setMessage("목표 페이지 수를 정해주세요");

        final EditText et = new EditText(MySetting.this);
        et.setHint(tv_pageGoal.getText());
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
    //페이지 목표 다이얼로그 끝

    //로그아웃 다이얼로그
    private AlertDialog.Builder logOutDialog(){

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(MySetting.this);
        dialogBuilder.setTitle("로그아웃");
        dialogBuilder.setMessage("로그아웃 하시겠습니까?");

        final EditText et = new EditText(MySetting.this);

        dialogBuilder.setPositiveButton("로그아웃", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                devEditor.putBoolean("autoLogin",false);
                devEditor.commit();
                //개발자 프레프에 자동로그인 설정을 없애기

                dialogInterface.dismiss();
                finish();
                Intent intent = new Intent(getApplicationContext(), Login.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                //지금 부르는 액티비티를 제외하고 전부 죽임
                startActivity(intent);
            }
        });
        dialogBuilder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        return dialogBuilder;

    }//로그아웃 다이얼로그 끝


}