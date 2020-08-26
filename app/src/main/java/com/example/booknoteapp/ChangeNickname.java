package com.example.booknoteapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

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

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class ChangeNickname extends AppCompatActivity {

    TextView tv_nickNow;
    Button btn_checkNick;
    Button btn_change;
    EditText editText;

    SharedPreferences userPref;
    SharedPreferences devPref;

    SharedPreferences.Editor userEditor;
    SharedPreferences.Editor devEditor;

    String userEmail;
    String nickname;
    String allNicknames;

    Boolean isNewNick=false;

    ArrayList<Dictionary_Essay> wholeEssayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_nickname);

        initialize();
        allListener();
    }

    private void initialize(){

        devPref = getSharedPreferences("users",MODE_PRIVATE);
        devEditor = devPref.edit();
        allNicknames = devPref.getString("nicknames","");

        userEmail = devPref.getString("currentUser","");
        userPref = getSharedPreferences(userEmail,MODE_PRIVATE);
        nickname = userPref.getString("nickname","");
        userEditor = userPref.edit();

        //////툴바 적용하기
        Toolbar toolbar = (Toolbar)findViewById(R.id.app_toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.green));
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("닉네임 변경");

        tv_nickNow = findViewById(R.id.tv_nicknameNow);
        btn_change = findViewById(R.id.btn_change);
        btn_checkNick = findViewById(R.id.btn_checkNick);
        editText = findViewById(R.id.et_inputNick);

        tv_nickNow.setText("현재 닉네임은 "+nickname+" 입니다");

    }


    private void allListener(){


        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if(allNicknames.contains(editText.getText().toString())) {

                    isNewNick = false;
                }else {

                    isNewNick = true;
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        btn_checkNick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isNewNick){
                    Toast.makeText(getApplicationContext(), "사용 가능한 닉네임입니다",Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getApplicationContext(), "이미 사용 중인 닉네임입니다",Toast.LENGTH_LONG).show();
                }
            }
        });

        btn_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isNewNick){
                    //사용 가능한 닉네임

                    Log.d("닉네임 전체",allNicknames);
                //유저 지금 닉네임 지우기
                allNicknames = allNicknames.replaceAll("#"+nickname,"");

                //새 닉네임 집어넣기
                allNicknames = allNicknames + "#"+editText.getText().toString();

                Log.d("닉네임 잘 추가되었는지",allNicknames);

                //닉네임 지운 리스트 다시 devPref에 저장
                devEditor.putString("nicknames",allNicknames);
                devEditor.commit();

                //유저 프레프에서 수정하기
                    userEditor.putString("nickname",editText.getText().toString());
                    userEditor.commit();

                //에세이에 있는 닉네임 모두 바꾸기
                changeEssayNick(editText.getText().toString());

                    Toast.makeText(getApplicationContext(), "닉네임이 성공적으로 변경되었습니다",Toast.LENGTH_LONG).show();
                    finish();

                }else{
                    //중복 비번
                    Toast.makeText(getApplicationContext(), "닉네임 중복확인이 필요합니다",Toast.LENGTH_LONG).show();
                }
            }
        });


    }


    private void changeEssayNick(String newNick){

        getWholeArrayFromPref(devPref, newNick);
        saveArrayToPref(wholeEssayList,devEditor);

    }

        //지금 어레이를 쉐어드에 저장하기
        private void saveArrayToPref(ArrayList<Dictionary_Essay> arrayList, SharedPreferences.Editor editor) {
            Gson gson = new Gson();
            String json = gson.toJson(arrayList);
            editor.putString("essay",json);
            editor.apply();
        }

        private void getWholeArrayFromPref(SharedPreferences pref, String newNick) {
            Gson gson = new Gson();


            String json = pref.getString("essay","EMPTY");

            Type type = new TypeToken<ArrayList<Dictionary_Essay>>() {
            }.getType();
            if(!json.equals("EMPTY")){
                wholeEssayList = gson.fromJson(json,type);
                Log.d("전체 리스트 길이 : ", String.valueOf(wholeEssayList.size()));
                //전체 리스트를 가져온다
                for(int i=0; i<wholeEssayList.size(); i++){
                    if(wholeEssayList.get(i).getUserEmail().equals(userEmail)){
                        //만약 유저가 쓴 글이 있다면 다 가져와서 닉네임을 바꿔준다
                        wholeEssayList.get(i).setNickname(newNick);
                    }

                }
                //전체 리스트에서 각 에세이가 어느 위치였는지 인덱스 값을 준다
            }

        }

}
