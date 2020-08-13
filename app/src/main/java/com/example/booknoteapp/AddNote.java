package com.example.booknoteapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
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
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddNote extends AppCompatActivity {

    EditText editText_userNote;
    EditText editText_quote;
    EditText editText_page;
    Button btn_addNote_done;
    TextView tv_addNote_date;


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

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("노트 작성");
       actionBar.setDisplayHomeAsUpEnabled(false);
        //뒤로가기 버튼 생성




        Toast.makeText(this, "onCreate 호출 됨",Toast.LENGTH_LONG).show();
       ////////////생명주기 테스트


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


        //////////////////////////////////////////////////노트 쓰는 날짜 고르기
        tv_addNote_date.setHint(time);
        tv_addNote_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatePickerDialog dialog = new DatePickerDialog(AddNote.this, new DatePickerDialog.OnDateSetListener() {
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

//
//        final boolean[] check = new boolean[1];
//
//        AlertDialog.Builder reallyGoOutAlert = new AlertDialog.Builder(this);
//        reallyGoOutAlert.setPositiveButton("나가기", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                NavUtils.navigateUpFromSameTask(thisActivity);
//                check[0] = true;
//            }
//        });
//        reallyGoOutAlert.setNegativeButton("취소", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                check[0] = false;
//            }
//        });
       switch (item.getItemId()) {
//           case android.R.id.home:
//               boolean a=false;
//
//               if(a){
//                   AlertDialog alert = reallyGoOutAlert.create();
//                   alert.setTitle("정말로 나가시겠습니까?");
//                   alert.show();
//
//
//                   a= check[0];
//               }
//
//
//          return a;

           case R.id.btn_done:

//            String message = editText_userNote.getText().toString();
//            String page =editText_page.getText().toString();

               String note = editText_userNote.getText().toString();
               String page =editText_page.getText().toString();
               String date = tv_addNote_date.getText().toString();
               String quote = editText_quote.getText().toString();

              //노트 추가 화면에서 책 상세 화면으로 넘겨줘야 할 자료들 인텐트에 넣기
               Intent intent = new Intent();
               Dictionary_note dic;
               dic = new Dictionary_note(page,date,quote,note,pickedColor);
               intent.putExtra("dictionary",dic);
               intent.putExtra("note",note);
               setResult(RESULT_OK,intent);
               finish();

               return true;


       }
       return super.onOptionsItemSelected(item);


    }

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


            //노트 추가 화면에서 책 상세 화면으로 넘겨줘야 할 자료들 인텐트에 넣기
            Intent intent = new Intent();
            Dictionary_note dic;
            dic = new Dictionary_note(page,date,quote,note,pickedColor);
            intent.putExtra("dictionary",dic);
            intent.putExtra("note",note);
            setResult(RESULT_OK,intent);
            finish();
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
                Toast.makeText(getApplicationContext(), "검은색 버튼을 누르셨습니다.", Toast.LENGTH_SHORT).show();
                editText_userNote.setTextColor(myBlack);
                pickedColor = myBlack;
            }else if(i==R.id.radioBtn_addnote_blue){
                Toast.makeText(getApplicationContext(), "파란색 버튼을 누르셨습니다.", Toast.LENGTH_SHORT).show();
                editText_userNote.setTextColor(myBlue);
                pickedColor = myBlue;
            }else if(i==R.id.radioBtn_addnote_red){
                Toast.makeText(getApplicationContext(), "빨간색 버튼을 누르셨습니다.", Toast.LENGTH_SHORT).show();
                editText_userNote.setTextColor(myRed);
                pickedColor = myRed;
            }
        }
    };

}