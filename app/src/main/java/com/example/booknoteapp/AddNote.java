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
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class AddNote extends AppCompatActivity {

    EditText editText_userNote;
    EditText editText_quote;
    EditText editText_page;
    Button btn_addNote_done;
    TextView tv_addNote_date;

    boolean wantToGo=false;

    //현재시간 가져오는 메소드
    long now = System.currentTimeMillis();
    Date date = new Date(now);
    SimpleDateFormat mFormat = new SimpleDateFormat("yyyy.MM.dd");
    String time = mFormat.format(date);
    //현재시간 가져오는 메소드

    //달력
    java.util.Calendar cal = Calendar.getInstance();

    int myBlack;
    int myBlue;
    int myRed;
    int pickedColor;

    String title;
    SharedPreferences userPref;
    SharedPreferences.Editor userEditor;
    ArrayList<Dictionary_note> noteList = new ArrayList<>();


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu,menu);
        return true;

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);


        initialize();
        allListener();


    }


    private void initialize() {

        //////툴바 적용하기
        Toolbar toolbar = (Toolbar)findViewById(R.id.app_toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.green));
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("노트 추가하기");


        String currentEmail = getSharedPreferences("users", Context.MODE_PRIVATE).getString("currentUser","");
        userPref = getSharedPreferences(currentEmail,this.MODE_PRIVATE);
        userEditor = userPref.edit();
        title = userPref.getString("bookNow","");


        //라디오 버튼 선언
        RadioButton radioBtn_black = findViewById(R.id.radioBtn_addnote_black);
        RadioButton radioBtn_blue = findViewById(R.id.radioBtn_addnote_blue);
        RadioButton radioBtn_red =  findViewById(R.id.radioBtn_addnote_red);

        //라디오 그룹 설정
        RadioGroup radioGroup = findViewById(R.id.radioGroup_addnote_txtcolor);
        radioGroup.setOnCheckedChangeListener(radioGroupButtonChangeListener);

        myBlack = ContextCompat.getColor(this,R.color.myBlack);
        myBlue = ContextCompat.getColor(this,R.color.myBlue);
        myRed = ContextCompat.getColor(this,R.color.myRed);
        pickedColor = myBlack;// 색상 기본값은 검은색

        //유저 노트 값을 받은 edit Text
        editText_userNote = findViewById(R.id.tv_addnote_userNote);
        editText_userNote.setTextColor(pickedColor);
        editText_page = findViewById(R.id.editText_note_pageNum);
        editText_quote = findViewById(R.id.tv_note_quote);
        tv_addNote_date = findViewById(R.id.tv_addNote_date);


        tv_addNote_date.setText(time);


    }


    private void allListener() {


        //읽은 날짜
        tv_addNote_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatePickerDialog dialog = new DatePickerDialog(AddNote.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int date) {

                        tv_addNote_date.setText(String.format("%2d.%02d.%02d",year,month+1,date));
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
               //완료 버튼을 누른 경우 (저장하기)

               getPrefToArray();
               //쉐어드에 저장된 리스트를 불러온다.
               saveNewNoteToArray();
               //리스트에 지금 노트를 추가한다.
               saveArrayToPref();
               //리스트를 다시 저장한다.
               finish();

               return true;


       }
       return super.onOptionsItemSelected(item);


    }

    private void alert() {
        AlertDialog.Builder reallyGoOutAlert = new AlertDialog.Builder(AddNote.this);
        reallyGoOutAlert.setTitle("정말 나가시겠습니까?")
                .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        wantToGo = false;
                    }
                })
                .setPositiveButton("나가기", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        wantToGo = true;
                        Intent intent1 = new Intent(getApplicationContext(), BookLog_Notes.class);
                        intent1.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                        startActivity(intent1);
                        finish();
                    }
                }).show();
    }

    private void saveArrayToPref() {
        Gson gson = new Gson();
        String json = gson.toJson(noteList);
        userEditor.putString("note",json);
        userEditor.commit();
    }


    //지금 받은 정보 어레이 리스트에 저장하기
    private void saveNewNoteToArray() {
        String note = editText_userNote.getText().toString();
        String page =editText_page.getText().toString();
        String date = tv_addNote_date.getText().toString();
        String quote = editText_quote.getText().toString();

        Dictionary_note newNote = new Dictionary_note(title,page,date,quote,note,pickedColor);
        noteList.add(0,newNote);

    }
    //지금 받은 정보 저장하기 끝

    ///쉐어드 프리퍼런스 가져와서 어레이 리스트로 바꿔주기
    private void getPrefToArray() {
        Gson gson = new Gson();
        String json ="EMPTY";
        json = userPref.getString("note","EMPTY");

        if(!json.equals("EMPTY")){
            Type type = new TypeToken<ArrayList<Dictionary_note>>() {
            }.getType();
            noteList = gson.fromJson(json,type);
            Log.d("들어오는지 확인", json);
            //   Log.d("대체 어레이 리스트가 존재는 하는지 확인 ", String.valueOf(getArrayList.size()));
            //  Log.d("대체 어레이 리스트가 존재는 하는지 확인 ", getArrayList.get(0).getTitle());
        }else{
            // 내용이 없으면 가져오지 않음
        }

    }
    ///쉐어드 프리퍼런스 가져와서 어레이 리스트로 바꿔주기 끝


    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        final Activity thisActivity = this;

        AlertDialog.Builder reallyGoOutAlert = new AlertDialog.Builder(this);
        reallyGoOutAlert.setPositiveButton("나가기", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                NavUtils.navigateUpFromSameTask(thisActivity);
                finish();

            }
        });
        reallyGoOutAlert.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        if(editText_userNote.getText().length()!=0){
            System.out.println("/////////////////////////////////////////텍스트 내용 : "+editText_userNote.getText().length());
            AlertDialog alert = reallyGoOutAlert.create();
            alert.setTitle("아직 저장하지 않은 노트가 있습니다. \n정말로 나가시겠습니까?");
            alert.show();
        }else{
            String note = editText_userNote.getText().toString();
            String page =editText_page.getText().toString();
            String date = tv_addNote_date.getText().toString();
            String quote = editText_quote.getText().toString();


//            //노트 추가 화면에서 책 상세 화면으로 넘겨줘야 할 자료들 인텐트에 넣기
//            Intent intent = new Intent();
//            Dictionary_note dic;
//            dic = new Dictionary_note(page,date,quote,note,pickedColor);
//            intent.putExtra("dictionary",dic);
//            intent.putExtra("note",note);
//            setResult(RESULT_OK,intent);
//            finish();
        }

    }

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

//        SharedPreferences pref = getSharedPreferences("pref",Activity.MODE_PRIVATE);
//        SharedPreferences.Editor editor = pref.edit();
//
//        editor.putString("name","띠로로");
//        //name 이라는 키를 가진 프리퍼런스에 띠로로 라는 글자를 저장해뒀다.
//        editor.commit();
    }

    @Override
    protected void onPause() {
        super.onPause();


//        SharedPreferences pref = getSharedPreferences("pref", Activity.MODE_PRIVATE);
//        if(pref!=null){
//            //저장된 데이터가 있는 경우에
//            String name = pref.getString("name","");
//            //두번째 파라미터는 저장된 name이 없는 경우에 사용할 디폴트 값.
//
//            Toast.makeText(this,"넣어둔 건 말이야 : "+name, Toast.LENGTH_LONG).show();
//            finish();
//        }
    }

    //    RadioButton.OnClickListener radioButtonClickListener = new RadioButton.OnClickListener(){
//        @Override
//        public void onClick(View view) {
//            Toast.makeText(getApplicationContext(),"black_btn : "+radioBtn_black.isChecked)
//        }
//    };
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

}