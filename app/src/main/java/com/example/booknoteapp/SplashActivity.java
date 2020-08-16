package com.example.booknoteapp;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.logging.LogRecord;

public class SplashActivity extends Activity {


    private static int SPLASH_TIME = 1500;

    String PREF_USERS = "users";
    SharedPreferences pref;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initialize();

        if(pref.getBoolean("autoLogin", false)){
            //자동 로그인은 시간이 좀 걸리기 때문에 시간 줄이기
            SPLASH_TIME = 500;

        }else{
            SPLASH_TIME = 1500;
        }
        startLoading();




    }

    private void initialize() {
        pref = getSharedPreferences(PREF_USERS,Activity.MODE_PRIVATE);
        editor = pref.edit();

    }

    private void startLoading() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(),Login.class);
                startActivity(intent);
                finish();
            }
        },SPLASH_TIME);

    }
}
