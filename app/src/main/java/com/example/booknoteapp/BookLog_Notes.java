package com.example.booknoteapp;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class BookLog_Notes extends AppCompatActivity  {


    RecyclerView recyclerView=null;
    Adapter_Note adapterNote = null;
    ArrayList<Dictionary_note> mList = new ArrayList<>();
    Dictionary_note dic;


    TextView tv_toBookLogPages;
    TextView tv_bookLog_title;
    ImageView bookCoverImg;
    TextView tv_author;
    TextView tv_pageNum;
    TextView tv_publisher;



    //유저가 작성한 독서노트 페이지&노트 프리뷰
    TextView tv_notePreview;
    TextView tv_bookPage;
    TextView tv_p;
    //노트 추가 버튼
    Button btn_addNote;
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


        initialize();
        setIntentDate();
        allListener();

    }


    private void initialize() {


        tv_bookLog_title = findViewById(R.id.tv_bookLog_title);
        bookCoverImg =findViewById(R.id.bookCoverImg);
        tv_author = findViewById(R.id.tv_bookLog_author);
     //   tv_pageNum = findViewById(R.id.tv_bookLog_bookPages);
        tv_publisher = findViewById(R.id.tv_bookLog_publisher);

        //노트 프리뷰,페이지 뷰
        tv_notePreview = (TextView)findViewById(R.id.tv_note_userNote) ;

        //뷰페이저
        ViewPager vp = findViewById(R.id.viewpager);
        Adapter_ViewPager_BookDetail adapter_viewPager = new Adapter_ViewPager_BookDetail(getSupportFragmentManager());
        vp.setAdapter(adapter_viewPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(vp);
//
//        recyclerView = findViewById(R.id.recycler_notes);
//        adapterNote = new Adapter_Note(mList,mEditListener);
//        recyclerView.setAdapter(adapterNote);
//        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
//        recyclerView.setLayoutManager(layoutManager);


    }//이니셜라이저 끝


    private void getNote() {


    }
    //전달받은 책 정보 띄우기
    private void setIntentDate() {
        Intent intent = getIntent();
        if(intent.getExtras()!=null) {

            //책 커버, 제목, 작가, 출판사, 페이지
            Dictionary_book dict = (Dictionary_book) intent.getSerializableExtra("selectedBook");
            tv_bookLog_title.setText(dict.getTitle());
            if(dict.bookCover==null){
                bookCoverImg.setBackground(getResources().getDrawable(R.drawable.ic_book));
            }else{
                bookCoverImg.setImageBitmap(dict.getBookCover());
            }
            if(dict.getPageNum().equals("")){
             //   tv_pageNum.setText("0");
            }else{
             //   tv_pageNum.setText(dict.getPageNum());
            }

            tv_author.setText(dict.getAuthor());
            tv_publisher.setText(dict.getPublisher());
        }

    }
    //전달받은 책 정보 띄우기 끝

    private void allListener() {

    }

    @Override
    protected void onResume() {
        super.onResume();


//        if((PreferenceManager.getString(getApplicationContext(),"pageNum")!="")){
//            tv_progress_readPageNum.setText( PreferenceManager.getString(getApplicationContext(),"pageNum"));
//            userReadPageNum = Integer.parseInt( PreferenceManager.getString(getApplicationContext(),"pageNum"));
//            progressBar_bookLog.setProgress(userReadPageNum);
//        }

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