package com.example.booknoteapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.SavedStateHandle;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MySetting extends AppCompatActivity {

    SharedPreferences devPref;
    SharedPreferences.Editor devEditor;

    SharedPreferences userPref;
    SharedPreferences.Editor userEditor;

    LinearLayout btn_pageGoal;
    TextView btn_changeNick;
    TextView btn_changePassword;
    TextView btn_logOut;
    TextView btn_signOut;
    TextView btn_sendDevEmail;

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

        btn_pageGoal = findViewById(R.id.btn_pageGoal);
        btn_changeNick = findViewById(R.id.btn_changeNick);
        btn_changePassword = findViewById(R.id.btn_changePassword);
        btn_logOut = findViewById(R.id.btn_logOut);
        btn_signOut = findViewById(R.id.btn_signOut);
        btn_sendDevEmail = findViewById(R.id.btn_sendDevEmail);


    }//이니셜라이즈

    private void allListener(){




    }//올 리스너 끝



}