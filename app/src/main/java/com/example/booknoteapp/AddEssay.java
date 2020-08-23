package com.example.booknoteapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class AddEssay extends AppCompatActivity {


    SharedPreferences userPref;
    SharedPreferences.Editor userPrefEditor;

    SharedPreferences devPref;
    SharedPreferences.Editor devPrefEditor;

    ArrayList<Dictionary_Essay> userEssayArrayList = new ArrayList<>();
    ArrayList<Dictionary_Essay> everyEssayList = new ArrayList<>();
    Dictionary_book bookDict;


    Button btn_open;
    Button btn_private;

    Boolean isOpen=true;
    String userEmail;

    ImageView iv_bookCover;
    TextView tv_bookTitle;
    TextView tv_bookAuthor;
    TextView tv_bookPublisher;

    EditText et_essayTitle;
    EditText et_essayContent;

    String time = "";




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu,menu);
        return true;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_essay);

        initialize();
        allListener();
        setBook();
    }

    private void initialize(){

        devPref = getSharedPreferences("users",MODE_PRIVATE);
        devPrefEditor = devPref.edit();

        userEmail = devPref.getString("currentUser","");
        userPref = getSharedPreferences(userEmail,MODE_PRIVATE);
        userPrefEditor = userPref.edit();


        //////툴바 적용하기
        Toolbar toolbar = (Toolbar)findViewById(R.id.app_toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.green));
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("에세이 쓰기");

        btn_open = findViewById(R.id.btn_open);
        btn_private =findViewById(R.id.btn_private);

        iv_bookCover = findViewById(R.id.iv_book_cover);
        tv_bookTitle = findViewById(R.id.tv_book_title);
        tv_bookAuthor = findViewById(R.id.tv_book_author);
        tv_bookPublisher = findViewById(R.id.tv_book_publisher);

        et_essayContent = findViewById(R.id.et_essayContent);
        et_essayTitle = findViewById(R.id.et_essayTitle);


    }//이니셜라이저 끝

    private void allListener(){

        btn_open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isOpen =true;
                btn_open.setBackground(getDrawable(R.drawable.button_yello));
                btn_private.setBackground(getDrawable(R.drawable.button_grey));
            }
        });

        btn_private.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isOpen = false;
                btn_open.setBackground(getDrawable(R.drawable.button_grey));
                btn_private.setBackground(getDrawable(R.drawable.button_yello));
            }
        });

    }//올리스너 끝


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {


        switch (item.getItemId()) {
            case android.R.id.home:

                alert();
                return true;

            case R.id.btn_done://완료 버튼을 누른 경우 (저장하기)


                getArrayFromUserPref(userPref);
                saveNewEssayToArray(userEssayArrayList);
                //리스트에 지금 노트를 추가한다.
                saveArrayToPref(userPrefEditor, userEssayArrayList);
                //리스트를 다시 저장한다.

                if(isOpen){
                    //공개 리스트의 경우
                    getArrayFromDevPref(devPref);
                    saveNewEssayToArray(everyEssayList);
                    saveArrayToPref(devPrefEditor, everyEssayList);
                    //내 쉐어드 가져와서 추가하고
                    //에세이 쉐어드 가져와서 추가하고
                }


                Intent intent1 = new Intent(getApplicationContext(), Essay.class);
                intent1.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent1);
                finish();

                return true;
        }
        return super.onOptionsItemSelected(item);


    }
    //상단 메뉴 버튼 리스너


    private void getArrayFromUserPref(SharedPreferences pref){
        Gson gson = new Gson();
        String json = pref.getString("essay","EMPTY");

        Type type = new TypeToken<ArrayList<Dictionary_Essay>>() {
        }.getType();
        if(!json.equals("EMPTY")){
            userEssayArrayList = gson.fromJson(json,type);
        }

    }

    private void getArrayFromDevPref(SharedPreferences pref){
        Gson gson = new Gson();
        String json = pref.getString("essay","EMPTY");

        Type type = new TypeToken<ArrayList<Dictionary_Essay>>() {
        }.getType();
        if(!json.equals("EMPTY")){
            everyEssayList = gson.fromJson(json,type);
        }

    }

    //지금 에세이 어레이 리스트에 저장하기
    private void saveNewEssayToArray(ArrayList<Dictionary_Essay> list) {
        getTime();

        Dictionary_Essay newEssay = new Dictionary_Essay(bookDict,isOpen);
        newEssay.setDate(time);
        newEssay.setUserEmail(userEmail);
        newEssay.setEssayTitle(et_essayTitle.getText().toString());
        newEssay.setEssayContent(et_essayContent.getText().toString());
        list.add(0,newEssay);


    }
    //지금 에세이 저장하기 끝


    //현재시간 가져오는 메소드
    private void getTime() {

        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat mFormat = new SimpleDateFormat("yyyy.MM.dd");
        time = mFormat.format(date);

    }
    //현재시간 가져오는 메소드 끝



    //쉐어드에 저장하기
    private void saveArrayToPref(SharedPreferences.Editor editor, ArrayList<Dictionary_Essay> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        editor.putString("essay",json);
        editor.commit();
    }
    //쉐어드에 저장하기



    //정말 나가시겠습니까?
    private void alert() {
        AlertDialog.Builder reallyGoOutAlert = new AlertDialog.Builder(AddEssay.this);
        reallyGoOutAlert.setTitle("정말 나가시겠습니까?")
                .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                })
                .setPositiveButton("나가기", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent1 = new Intent(getApplicationContext(), Essay.class);
                        intent1.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                        intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        //
                        startActivity(intent1);
                        finish();
                    }
                }).show();
    }
    //정말 나가시겠습니까? 끝






    private void setBook(){

        Intent intent = getIntent();
        if(intent.getExtras()==null
        ){
            /////그냥 책 추가하기를 누른 경우. 아무 일도 안해도 된다.
        }else{
            //책 검색을 통해 넘어온 경우. 이미지, 저자, 책제목, 출판사를 세팅해준다.
            bookDict = (Dictionary_book) intent.getSerializableExtra("bookDict");
            iv_bookCover.setImageBitmap(bookDict.getBookCover());
            tv_bookTitle.setText(bookDict.getTitle());
            tv_bookPublisher.setText(bookDict.getPublisher());
            tv_bookAuthor.setText(bookDict.getAuthor());

        }
    }
}