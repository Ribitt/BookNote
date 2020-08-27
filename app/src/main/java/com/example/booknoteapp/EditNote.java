package com.example.booknoteapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NavUtils;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class EditNote extends AppCompatActivity {

    EditText editText_userNote;
    EditText editText_quote;
    EditText editText_page;
    TextView tv_addNote_date;

    //라디오 버튼 선언
    RadioButton radioBtn_black;
    RadioButton radioBtn_blue;
    RadioButton radioBtn_red;

    SharedPreferences userPref;
    SharedPreferences.Editor userEditor;

    ArrayList<Dictionary_note> wholeList = new ArrayList<>(); //전체 노트 리스트


    //달력
    java.util.Calendar cal = Calendar.getInstance();

    int myBlack;
    int myBlue;
    int myRed;
    int pickedColor;

    Intent intent;
    Dictionary_note dictionary_note;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu,menu);
        return true;

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);

        initialize();
        allListener();
        getWholeList();
        getIntentExtras();

    }

    private void getIntentExtras() {
        intent = getIntent();

        //노트 내용 채우기
        dictionary_note = (Dictionary_note) intent.getSerializableExtra("selectedNote");

        tv_addNote_date.setText(dictionary_note.getDate());
        editText_page.setText(dictionary_note.getPageNum());
        editText_userNote.setText(dictionary_note.getNote());
        editText_quote.setText(dictionary_note.getQuote());


        if(dictionary_note.color==myBlack){
            radioBtn_black.toggle();

        } else if(dictionary_note.color==myBlue){
            radioBtn_blue.toggle();
        }else {
            radioBtn_red.toggle();
        }
        //노트 내용 채우기 끝


    }

    private void initialize() {


        //////툴바 적용하기
        Toolbar toolbar = (Toolbar)findViewById(R.id.app_toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.green));
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("노트 수정하기");


        String currentEmail = getSharedPreferences("users", Context.MODE_PRIVATE).getString("currentUser","");
        userPref = getSharedPreferences(currentEmail,this.MODE_PRIVATE);
        userEditor = userPref.edit();


        //라디오 그룹 설정
        radioBtn_black  = findViewById(R.id.radioBtn_addnote_black);
        radioBtn_blue = findViewById(R.id.radioBtn_addnote_blue);
        radioBtn_red  =  findViewById(R.id.radioBtn_addnote_red);

        RadioGroup radioGroup = findViewById(R.id.radioGroup_addnote_txtcolor);
        radioGroup.setOnCheckedChangeListener(radioGroupButtonChangeListener);

        myBlack = ContextCompat.getColor(this,R.color.myBlack);
        myBlue = ContextCompat.getColor(this,R.color.myBlue);
        myRed = ContextCompat.getColor(this,R.color.myRed);
        pickedColor = myBlack;// 색상 기본값은 검은색


        //유저 노트 값을 받은 edit Text
        editText_userNote = findViewById(R.id.tv_addnote_userNote);
        editText_page = findViewById(R.id.editText_note_pageNum);
        editText_quote = findViewById(R.id.tv_note_quote);
        tv_addNote_date = findViewById(R.id.tv_addNote_date);

    }//이니셜라이징 끝

    private void allListener() {
        tv_addNote_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatePickerDialog dialog = new DatePickerDialog(EditNote.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int date) {

                        tv_addNote_date.setText(year+"."+(int)(month+1)+"."+date);
                    }
                }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE));

                dialog.getDatePicker().setMaxDate(new Date().getTime());    //입력한 날짜 이후로 클릭 안되게 옵션
                dialog.show();


            }
        });
        /////////////////////////////////////////////////////노트 쓰는 날짜 고르기
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {

            case android.R.id.home:
                alert();
                return true;

            case R.id.btn_done:

                saveNote();
                saveArrayToPref();
                finish();
                return true;


        }
        return super.onOptionsItemSelected(item);


    }

    private void saveNote(){

        String note = editText_userNote.getText().toString();
        String page =editText_page.getText().toString();
        String date = tv_addNote_date.getText().toString();
        String quote = editText_quote.getText().toString();

        dictionary_note.setNote(note);
        dictionary_note.setPageNum(page);
        dictionary_note.setDate(date);
        dictionary_note.setQuote(quote);
        dictionary_note.setColor(pickedColor);

        wholeList.set(dictionary_note.getPositionInWholeList(),dictionary_note);
        //전체 노트 해당 자리에 수정된 노트를 저장

       // mList.set(position,dictionary_note);

    }

    //노트 전체 리스트 가져오기
    private void getWholeList(){
        Gson gson = new Gson();
        String json = userPref.getString("note","EMPTY");

        if(!json.equals("EMPTY")){
            Type type = new TypeToken<ArrayList<Dictionary_note>>() {
            }.getType();

            wholeList = gson.fromJson(json,type);
        }
        //노트 전체 리스트

    }
    //노트 전체 리스트 가져오기 끝


    //수정된 노트 어레이와 나머지 노트 어레이 합쳐서 저장
    private void saveArrayToPref() {

        Gson gson = new Gson();
        String json = gson.toJson(wholeList);
        userEditor.putString("note",json);
        userEditor.apply();
    }
    //어레이 저장 끝


    private void alert() {
        AlertDialog.Builder reallyGoOutAlert = new AlertDialog.Builder(EditNote.this);
        reallyGoOutAlert.setTitle("정말 나가시겠습니까?")
                .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("나가기", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent1 = new Intent(getApplicationContext(), BookLog_Notes.class);
                        intent1.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                        startActivity(intent1);
                        finish();
                    }
                }).show();
    }


    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        final Activity thisActivity = this;

        alert();

//        AlertDialog.Builder reallyGoOutAlert = new AlertDialog.Builder(this);
//        reallyGoOutAlert.setPositiveButton("나가기", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                NavUtils.navigateUpFromSameTask(thisActivity);
//                finish();
//
//            }
//        });
//        reallyGoOutAlert.setNegativeButton("취소", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//
//            }
//        });
//
//        if(editText_userNote.getText().length()!=0){
//            System.out.println("/////////////////////////////////////////텍스트 내용 : "+editText_userNote.getText().length());
//            AlertDialog alert = reallyGoOutAlert.create();
//            alert.setTitle("아직 저장하지 않은 노트가 있습니다. \n정말로 나가시겠습니까?");
//            alert.show();
//        }else{
//            String note = editText_userNote.getText().toString();
//            String page =editText_page.getText().toString();
//            String date = tv_addNote_date.getText().toString();
//            String quote = editText_quote.getText().toString();
//
//
//            //노트 추가 화면에서 책 상세 화면으로 넘겨줘야 할 자료들 인텐트에 넣기
//            Intent intent = new Intent();
//            Dictionary_note dic;
//            dic = new Dictionary_note("제목",page,date,quote,note,pickedColor);
//            intent.putExtra("dictionary",dic);
//            intent.putExtra("note",note);
//            setResult(RESULT_OK,intent);
//            finish();
//        }

    }

    RadioGroup.OnCheckedChangeListener radioGroupButtonChangeListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup radioGroup, int i) {

            if(i==R.id.radioBtn_addnote_black) {
                editText_userNote.setTextColor(myBlack);
                pickedColor = myBlack;
            }else if(i==R.id.radioBtn_addnote_blue){
                editText_userNote.setTextColor(myBlue);
                pickedColor = myBlue;
            }else if(i==R.id.radioBtn_addnote_red){
                editText_userNote.setTextColor(myRed);
                pickedColor = myRed;
            }
        }
    };

    @Override
    protected void onResume() {
        super.onResume();

    }



    @Override
    protected void onStop() {
        super.onStop();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onPostResume() {
        super.onPostResume();

    }

    @Override
    protected void onPause() {
        super.onPause();


    }



}