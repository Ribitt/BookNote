package com.example.booknoteapp;


import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class BookLog_Pages extends AppCompatActivity  {

    int y=0, m=0, d=0;
    java.util.Calendar calendar = java.util.Calendar.getInstance();

    //리사이클러뷰
    RecyclerView recyclerView;
    ArrayList<Dictionary_pageLog> mList = new ArrayList<>();
    Adapter_PageLog adapter_pageLog;
    Dictionary_pageLog dic;
    LinearLayoutManager layoutManager = new LinearLayoutManager(this);
    //리사이클러뷰




    //책 상세페이지에 있는 프로그레스 바
    ProgressBar progressBar_bookLog;
    //프로그레스 바 밑에 현재 페이지 텍스트뷰(책갈피)
    TextView tv_progress_readPageNum;
    //프로그레스 바 프로그레스에 넣어줄 인트값
    int userReadPageNum;



    //기록 추가 버튼
    Button btn_bookLog_insertPageLog;
    //기록추가 커스텀 다이얼로그
    private Dialog_insertPage dialog;

    //독서 노트로 넘어가는 버튼
    TextView tv_toBookLogNotes;

    TextView tv_user_readPageNum;
    //하단 메뉴 버튼
    Button btn_to_calendar;
    Button btn_to_drawer;
    Button btn_to_essay;
    Button btn_to_setting;
    Button btn_to_stat;
    /////////////////////////////


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_log__pages);




        //리사이클러뷰
        recyclerView = findViewById(R.id.recycler_pageLog);
        adapter_pageLog = new Adapter_PageLog(mList);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter_pageLog);
        //리사이클러뷰


        //독서 노트 화면으로
        tv_toBookLogNotes = (TextView)findViewById(R.id.tv_toBookLogNotes);
        tv_toBookLogNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),BookLog_Notes.class);
                startActivity(intent);
                finish();
            }
        });


        //기록 추가버튼
        btn_bookLog_insertPageLog = (Button)findViewById(R.id.btn_bookLog_insertPageLog);
        //책 상세 페이지에 있는 프로그레스 바와 밑에 있는 읽은 숫자
        progressBar_bookLog = (ProgressBar)findViewById(R.id.progressBar_bookLog);
        tv_progress_readPageNum=(TextView)findViewById(R.id.tv_bookLog_progress_readPage);

        //기록 추가 버튼 클릭 이벤트
        btn_bookLog_insertPageLog.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        dialog = new Dialog_insertPage(BookLog_Pages.this, dateListener, positive, negative);
                        dialog.show();


                    }
                }
        );

        // 하단 메뉴바 버튼 선언
        btn_to_calendar = (Button)findViewById(R.id.btn_to_calender);
        btn_to_drawer = (Button)findViewById(R.id.btn_to_drawer);
        btn_to_essay = (Button)findViewById(R.id.btn_to_assay);
        btn_to_setting = (Button)findViewById(R.id.btn_to_setting);
        btn_to_stat = (Button)findViewById(R.id.btn_to_stat);
        // 하단 메뉴바 버튼 선언 끝

        // 하단 메뉴바 클릭 이벤트
        btn_to_calendar.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent intent = new Intent(getApplicationContext(), Calendar.class);
                        startActivity(intent);
                        finish();

                    }
                }
        );
        btn_to_drawer.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                        startActivity(intent);
                        finish();
                    }
                }
        );
        btn_to_essay.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getApplicationContext(),Essay.class);
                        startActivity(intent);
                        finish();
                    }
                }
        );
        btn_to_setting.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
//                        Intent intent = new Intent(getApplicationContext(),SettingsActivity.class);
//                        startActivity(intent);
                    }
                }
        );

        btn_to_stat.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getApplicationContext(),Stat.class);
                        startActivity(intent);
                        finish();
                    }
                }
        );

        // 하단 메뉴바 클릭 이벤트 끝



    }





    ////////////////////////////페이지 추가 다이얼로그 생성자에 해당하는 커스텀 클릭 리스너들
   private View.OnClickListener dateListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            showDialog();
        }
    };

    private void showDialog(){

        DatePickerDialog pickerDialog = new DatePickerDialog(BookLog_Pages.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int date) {
                dialog.setDate(String.format("%2d.%02d.%02d",year,month+1,date));
            }
        }, calendar.get(java.util.Calendar.YEAR), calendar.get(java.util.Calendar.MONTH), calendar.get(java.util.Calendar.DAY_OF_MONTH));
        pickerDialog.getDatePicker().setMaxDate(new Date().getTime());
        pickerDialog.show();
    }


    private View.OnClickListener positive = new View.OnClickListener() {
       @Override
       public void onClick(View view) {

           if(!dialog.getStartP().equals("") && !dialog.getEndP().equals("")){//둘 다 값이 있을 때만 일하자 !!

               //ArrayList에 넣어준다
               dic = new Dictionary_pageLog(dialog.getDate(),dialog.getStartP(),dialog.getEndP());
               mList.add(0,dic);
               //새로 추가한 내용이 맨 위로 가도록 한다. 그냥 dict넣어주면 맨 밑으로 간다.

               adapter_pageLog.notifyItemInserted(0); //0번 위치에 새로운 애가 왔다 잘 봐라
               //맨 밑으로 추가하면 그냥 notifyDataSetChanged호출


               //유저가 입력한 마지막 페이지 값을 프로그레스 바에도 적용한다. 입력값은 문자열이므로 정수로 변환한다.
               userReadPageNum = Integer.parseInt(dialog.getEndP());
               progressBar_bookLog.setProgress(userReadPageNum);

               //유저가 입력한 값을 프로그레스 바 하단에 있는 숫자에도 반영해준다
               tv_progress_readPageNum.setText(dialog.getEndP());

               //리스트에도 넣어준다
               PreferenceManager.setString(getApplicationContext(),"pageNum",dialog.getEndP());


           }else{//하나라도 없으면 아무 일 없이 넘어가기

           }

           dialog.dismiss();
       }
   };

    private View.OnClickListener negative = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            dialog.dismiss();
        }
    };
////////////////////////////페이지 추가 다이얼로그 생성자에 해당하는 커스텀 클릭 리스너들 끝






    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onRestart() {
        super.onRestart();




    }

    @Override
    protected void onResume() {
        super.onResume();

        System.out.println("/////////////////////////// 내용있냐"+PreferenceManager.getString(getApplicationContext(),"pageNum"));
        if(!PreferenceManager.getString(getApplicationContext(),"pageNum").equals("")){

            userReadPageNum = Integer.parseInt(PreferenceManager.getString(getApplicationContext(),"pageNum"));
            progressBar_bookLog.setProgress(userReadPageNum);
            tv_progress_readPageNum.setText(PreferenceManager.getString(getApplicationContext(),"pageNum"));

        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }


}


//버튼을 눌러서 에딧텍스트 값을 텍스트뷰에 집어넣는 예제
//    Button sampleBtn = (Button)findViewById(R.id.btn_note_done);
//        sampleBtn.setOnClickListener(
//                new View.OnClickListener() {
//@Override
//public void onClick(View view) {
//        TextView textView = (TextView)findViewById(R.id.tv_sample);
//        EditText editText = (EditText)findViewById(R.id.editText_sample);
//        textView.setText(editText.getText());
//        }
//        }
//        );

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if(requestCode==1){
//            String message = data.getStringExtra("MESSAGE");
//            String page = data.getStringExtra("PAGE");
//            Integer color = data.getIntExtra("COLOR",R.color.myBlack);
//            //뒤에 R.color.myBlack자리는 만약 인텐트로 넘어온 값이 없을 경우 디폴트로 정해둘 값이다.
//            tv_p.setText("P. ");
//            tv_notePreview.setText(message);
//            tv_bookPage.setText(page);int blue = android.R.color.holo_blue_dark;
//            tv_notePreview.setTextColor(color);
//        }
//    }


//다이얼로그 띄우기
//    protected void showInputDialog() {
//        LayoutInflater vi =(LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        LinearLayout inputLayout = (LinearLayout) vi.inflate(R.layout.dialog_insert_page,null);
//
//        AlertDialog.Builder insertLog = new AlertDialog.Builder(BookLog_Pages.this);
//
//        //  EditText date = (EditText)inputLayout.findViewById(R.id.editText_insertDate);
//        final EditText startPage = (EditText)inputLayout.findViewById(R.id.editText_startPage);
//        final EditText endPage = (EditText)inputLayout.findViewById(R.id.editText_endPage);
//
//        final Context context = this.getApplicationContext();
//
//        insertLog.setTitle("읽은 페이지를 기록해주세요.")
//                .setView(inputLayout);
//
//        date.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                showDialog();
//            }
//        });
//
//
//        insertLog.setPositiveButton("확인", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//
//                if(!startPage.getText().toString().equals("") && !endPage.getText().toString().equals("")){//둘 다 값이 있을 때만 일하자 !!
//
//                    //유저가 입력한 값을 받아다가
//                    String startP = startPage.getText().toString();
//                    String endP = endPage.getText().toString();
//
//                    //ArrayList에 넣어준다
//                    Dictionary_pageLog dict = new Dictionary_pageLog("2020.08.02",startP,endP);
//                    mList.add(0,dict);
//                    //새로 추가한 내용이 맨 위로 가도록 한다. 그냥 dict넣어주면 맨 밑으로 간다.
//
//                    adapter_pageLog.notifyItemInserted(0); //0번 위치에 새로운 애가 왔다 잘 봐라
//                    //맨 밑으로 추가하면 그냥 notifyDataSetChanged호출
//
//
//                    //유저가 입력한 마지막 페이지 값을 프로그레스 바에도 적용한다. 입력값은 문자열이므로 정수로 변환한다.
//                    userReadPageNum = Integer.parseInt(endP);
//                    progressBar_bookLog.setProgress(userReadPageNum);
//
//                    //유저가 입력한 값을 프로그레스 바 하단에 있는 숫자에도 반영해준다
//                    tv_progress_readPageNum.setText(endPage.getText());
//
//                    //리스트에도 넣어준다
//                    PreferenceManager.setString(getApplicationContext(),"pageNum",endPage.getText().toString());
//
//
//
//                }else{//하나라도 없으면 아무 일 없이 넘어가기
//
//                }
//
//
//            }
//        }).show();
//    }
