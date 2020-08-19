package com.example.booknoteapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Login extends AppCompatActivity {

    EditText  email;
    EditText password;
    Button loginBtn;
    Button signUpBtn;
    CheckBox checkBox_autoLogin;

    TextView warning_noEmail_registered;

    SharedPreferences pref;
    SharedPreferences.Editor editor;


    String savedEmail="";
    String savedPassword="";

    Boolean isRegistered =true;
    Boolean isPasswordSame=false;


    boolean isKeyboardShowing = false;
    int keypadBaseHeight = 0;
    //키보드에 맞춰 화면 올리기용

    


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        initializer();
        allClickListener();
        autoLogin();



    }

    private void autoLogin() {


        if(pref.getBoolean("autoLogin",false)){
            //오토 로그인 설정이 되어 있으면 로그인 처리를 알아서 하고 넘어간다.

            //일단 마지막으로 로그인한 유저가 자동 로그인을 누르고 갔다는 소리기 때문에 그 유저 이메일 가져오고
            String userEmail = pref.getString("currentUser","");
            //그 유저 이름으로 된 저장소를 불러온다.
            // 해당 닉네임으로 인사해준다.
            SharedPreferences userPref = getSharedPreferences(userEmail,MODE_PRIVATE);
            String nickname = userPref.getString("nickname","");
            Toast.makeText(Login.this, nickname+"님 환영합니다", Toast.LENGTH_SHORT).show();

            //바로 드로워 탭으로 이동하면서 지금 액티비티는 끝내기
            Intent intent = new Intent(getApplicationContext(), MainHome.class);
            startActivity(intent);
            finish();
        }


    }



    private void initializer() {


        //쉐어드 프리퍼런스 불러오기
        pref = getSharedPreferences("users", MODE_PRIVATE);
        editor = pref.edit();
        //쉐어드 프리퍼런스

        email = findViewById(R.id.et_login_email);
        password = findViewById(R.id.et_login_password);
        loginBtn = findViewById(R.id.btn_login_login);
        signUpBtn = findViewById(R.id.btn_login_signUp);
        warning_noEmail_registered = findViewById(R.id.tv_login_warningNoEmail);
        checkBox_autoLogin = findViewById(R.id.checkbox_autoLogin);
    }

    private void allClickListener() {
        //자동로그인 체크박스
        checkBox_autoLogin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked){
                    if(isPasswordSame && isRegistered) {
                        //로그인할 수 있는 정보인데 자동 로그인 체크를 한 경우에만 신경쓰자
                        editor.putBoolean("autoLogin",true);
                        editor.commit();
                        //유저 관리창에 현재 유저 이메일을 저장한다. 다른 액티비티에서 모두 이 이메일을 불러와서 정보를 가져오게 된다.
                    }

                }else{
                    editor.putBoolean("autoLogin",false);
                    editor.commit();
                    //체크 풀리면 오토 로그인 안하게 저장해두기
                }
            }
        });
        //자동로그인 체크박스 끝


        //회원가입 버튼
        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SignUp.class);
                startActivity(intent);
            }
        });

        //회원가입 버튼 끝



        //이메일 주소 존재하는지 확인
        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                String temp = pref.getString(email.getText().toString(),"");

                if(!temp.equals("")){
                    //해당 이메일 주소로 저장된 게 있으면 여기로 와서 이메일과 비밀번호를 각각 저장해준다.
                    isRegistered = true;
                    savedEmail = email.getText().toString();
                    savedPassword = temp;


                }else{
                    //아무 것도 없으면 해당하는 이메일이 없다 = 가입해야 한다 식으로 가야할 듯
                    isRegistered = false;
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        //이메일 주소 존재하는지 확인 끝

        //비밀번호 일치하는지 확인
        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(savedPassword.equals(password.getText().toString())){
                    isPasswordSame = true;
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        //비밀번호 일치하는지 확인

        //로그인 버튼
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isPasswordSame && isRegistered) {

                    editor.putString("currentUser",savedEmail);
                    editor.commit();
                    //유저 관리창에 현재 유저 이메일을 저장한다. 다른 액티비티에서 모두 이 이메일을 불러와서 정보를 가져오게 된다.
                    SharedPreferences userPref = getSharedPreferences(savedEmail,MODE_PRIVATE);
                    String nickname = userPref.getString("nickname","");
                    Toast.makeText(Login.this, nickname+"님 환영합니다", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), DrawerTap.class);
                    startActivity(intent);
                    finish();

                }else{
                    Toast.makeText(Login.this, "존재하지 않는 계정이거나 비밀번호가 올바르지 않습니다", Toast.LENGTH_SHORT).show();
                }


            }
        });
        //로그인 버튼 끝

    }//올클릭 리스너 끝

}