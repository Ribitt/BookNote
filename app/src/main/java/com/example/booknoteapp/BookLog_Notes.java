package com.example.booknoteapp;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class BookLog_Notes extends AppCompatActivity  {


    RecyclerView recyclerView=null;
    Adapter_Note adapterNote = null;
    ArrayList<Dictionary_note> mList = new ArrayList<>();
    Dictionary_note dic;


    TextView tv_toBookLogPages;
    TextView tv_bookLog_title;
    ImageView bookCoverImg;

    //책 상세페이지에 있는 프로그레스 바
    ProgressBar progressBar_bookLog;
    //프로그레스 바 밑에 현재 페이지 텍스트뷰(책갈피)
    TextView tv_progress_readPageNum;
    //프로그레스 바 프로그레스에 넣어줄 인트값
    int userReadPageNum;

    //유저가 작성한 독서노트 페이지&노트 프리뷰
    TextView tv_notePreview;
    TextView tv_bookPage;
    TextView tv_p;
    //노트 추가 버튼
    Button btn_addNote;
    //하단 메뉴 버튼
    Button btn_to_calendar;
    Button btn_to_drawer;
    Button btn_to_essay;
    Button btn_to_setting;
    Button btn_to_stat;
    /////////////////////////////

    int position;
    //수정하려고 하는 리사이클러 뷰 인덱스 넘버


    View.OnClickListener mEditListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            position = (int)view.getTag();
            //지금 눌린 수정 버튼이 배열에서 가진 인덱스 값을 구해온다.
            Dictionary_note temp = mList.get(position);
            //해당 데이터들을 가져온다.

            Intent intent = new Intent(getApplicationContext(), EditNote.class);
            intent.putExtra("dicForEdit",temp);
            //수정대상인 데이터값을 전부 보낸다.
            startActivityForResult(intent, 3);
            //수정을 위해 보낼 때는 리퀘스트 코드를 3으로 한다.
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_log__notes);



        tv_bookLog_title = findViewById(R.id.tv_bookLog_title);
        bookCoverImg =findViewById(R.id.bookCoverImg);

        Intent intent = getIntent();
        if(intent.getExtras()!=null) {
            String title = intent.getExtras().get("title").toString();
            tv_bookLog_title.setText(title);
            byte[] editArr = intent.getByteArrayExtra("image");
            Bitmap editImage = BitmapFactory.decodeByteArray(editArr, 0, editArr.length);
            bookCoverImg.setImageBitmap(editImage);

        }


       recyclerView = findViewById(R.id.recycler_notes);
       adapterNote = new Adapter_Note(mList,mEditListener);
       recyclerView.setAdapter(adapterNote);
       LinearLayoutManager layoutManager = new LinearLayoutManager(this);
       recyclerView.setLayoutManager(layoutManager);
//       dic = new Dictionary_note("123","2020.08.01","아주 재미가 있는 책이었다 이말입니다." +
//               "\n완전 읽을 맛이 났다." +
//               "\n얼른 과제 끝내고 또 보고 싶다. " +
//               "\n후후후후후후후" +
//               "\naskdjf;alsdj;fak");
//
//        mList.add(dic);
//        noteAdapter.notifyDataSetChanged();



        tv_progress_readPageNum=(TextView)findViewById(R.id.tv_bookLog_progress_readPage);
        progressBar_bookLog=(ProgressBar)findViewById(R.id.progressBar_bookLog);

//        String text = PreferenceManager.getString(this,"rebuild");
//        if(text.equals("")){
//            text = "저장된 데이터가 없습니다.";
//            PreferenceManager.setString(this,"rebuild","암것도 없습니다.");
//        }


      //  독서 페이지 기록 페이지로 버튼
        tv_toBookLogPages = (TextView)findViewById(R.id.tv_toBookLogPages);
        tv_toBookLogPages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),BookLog_Pages.class);
                startActivity(intent);
                finish();

            }
        });



        //노트 추가버튼
        btn_addNote = (Button)findViewById(R.id.btn_bookdetail_addNote);
        //노트 프리뷰,페이지 뷰
        tv_notePreview = (TextView)findViewById(R.id.tv_note_userNote) ;
//        tv_bookPage = (TextView)findViewById(R.id.tv_bookPage);
//        tv_p = (TextView)findViewById(R.id.tv_p);

        //노트 추가 버튼 클릭 이벤트
        btn_addNote.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getApplicationContext(), AddNote.class);
                        startActivityForResult(intent, 1);

                        //그냥 추가되기
//                        dic = new Dictionary_note("123","2020.08.01","인용문이 들어갈 자리","아주 재미가 있는 책이었다 이말입니다." +
//                                "\n완전 읽을 맛이 났다." +
//                                "\n얼른 과제 끝내고 또 보고 싶다. " +
//                                "\n후후후후후후후" +
//                                "\naskdjf;alsdj;fak" +
//                                "\naskdjf;alsdj;fak");
//
//                        mList.add(dic);
//                        adapterNote.notifyDataSetChanged();
                    }
                }
        );
        //노트 추가 버튼 클릭 이벤트 끝

//        //노트 프리뷰 클릭 이벤트
//        tv_notePreview.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getApplicationContext(),Note.class);
//                startActivity(intent);
//            }
//        });

        //노트 글자 클릭 이벤트 끝


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

//        tv_forTest = (TextView)findViewById(R.id.tv_forTest);
//        tv_forTest.setText(text);
    }


    @Override
    protected void onResume() {
        super.onResume();


        if((PreferenceManager.getString(getApplicationContext(),"pageNum")!="")){
            tv_progress_readPageNum.setText( PreferenceManager.getString(getApplicationContext(),"pageNum"));
            userReadPageNum = Integer.parseInt( PreferenceManager.getString(getApplicationContext(),"pageNum"));
            progressBar_bookLog.setProgress(userReadPageNum);
        }

    }

    @Override
    protected void onStop() {
        super.onStop();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
            if(resultCode==RESULT_OK){
                if(requestCode==1){
                    Toast.makeText(this, data.getStringExtra("note"),Toast.LENGTH_LONG).show();

                    dic = (Dictionary_note)data.getSerializableExtra("dictionary");
                    mList.add(dic);

                    adapterNote.notifyDataSetChanged();}
                else if(requestCode==3){

                    dic = (Dictionary_note)data.getSerializableExtra("dictionary");
                    mList.set(position,dic);
                    adapterNote.notifyItemChanged(position);
                  //  adapterNote.notifyItemRangeChanged(position,mList.size());
                }
            }




                //if(data.getStringExtra("MESSAGE").length()==0 &&data.getStringExtra("PAGE").length()==0 ){
//        if(data.getSerializableExtra("dictionary")==null){//비어있으면 암것도 하지말고
//        }else
            //뒤에 R.color.myBlack자리는 만약 인텐트로 넘어온 값이 없을 경우 디폴트로 정해둘 값이다.
//            tv_p.setText("P. ");
//            tv_notePreview.setText(message);
//            tv_bookPage.setText(page);int blue = android.R.color.holo_blue_dark;
            //Integer color = data.getIntExtra("COLOR",R.color.myBlack);
//            tv_notePreview.setTextColor(color);

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