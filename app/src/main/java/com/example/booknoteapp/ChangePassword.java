package com.example.booknoteapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.internal.$Gson$Preconditions;

public class ChangePassword extends AppCompatActivity {

    EditText et_nowPassword;
    EditText et_newPassword;
    EditText et_newPasswordConfirm;
    TextView tv_warning;
    TextView tv_nowPWWarning;

    Button btn_change;

    SharedPreferences devPref;
    SharedPreferences.Editor devEditor;

    String userEmail;
    String nowPassword;

    Boolean isNewPasswordSame=false;
    Boolean isNowPasswordSame=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        initialize();
        allListener();
    }

    private void initialize(){

        devPref = getSharedPreferences("users",MODE_PRIVATE);
        devEditor = devPref.edit();
        userEmail = devPref.getString("currentUser","EMPTY");
        nowPassword = devPref.getString(userEmail,"");
        Log.d("지금 비밀번호 잘 나오나",nowPassword);

        et_nowPassword = findViewById(R.id.et_nowPassword);
        et_newPassword = findViewById(R.id.et_newPassword);
        et_newPasswordConfirm = findViewById(R.id.et_newPassword_confirm);
        tv_warning = findViewById(R.id.warning_passwordDifferent);
        tv_nowPWWarning = findViewById(R.id.warning_nowPWDifferent);
        btn_change = findViewById(R.id.btn_change);

    }

    private void allListener(){

        btn_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isNewPasswordSame && isNowPasswordSame){

                    devEditor.putString(userEmail,et_newPasswordConfirm.getText().toString());
                    devEditor.commit();
                    Toast.makeText(getApplicationContext(),"비밀번호가 성공적으로 변경되었습니다" ,Toast.LENGTH_LONG).show();
                    finish();

                }else{
                    Toast.makeText(getApplicationContext(),"비밀번호를 확인하세요",Toast.LENGTH_LONG).show();
                }
            }
        });

        //지금 비밀번호 일치여부 확인하기
        et_nowPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {


            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if(et_nowPassword.getText().toString().equals(nowPassword)) {
                    Log.d("비번 일치","일치함");
                    isNowPasswordSame = true;
                    tv_nowPWWarning.setVisibility(View.GONE);
                }else {
                    isNowPasswordSame = false;
                    tv_nowPWWarning.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        //지금 비밀번호 일치 확인 끝

        //새 비밀번호 일치여부 확인하기
        et_newPasswordConfirm.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(et_newPassword.getText().toString().equals(et_newPasswordConfirm.getText().toString())) {
                    tv_warning.setVisibility(View.GONE);
                    isNewPasswordSame = true;
                }else {
                    tv_warning.setVisibility(View.VISIBLE);
                    isNewPasswordSame = false;
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        //새 비밀번호 일치 확인 끝
    }
}