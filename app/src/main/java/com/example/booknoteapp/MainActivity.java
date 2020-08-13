package com.example.booknoteapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView mRecylerView = null;
    Adapter_Reading mAdapter = null;
    ArrayList<Dictionary_reading> mList = new ArrayList<>();
    Dictionary_reading dic;
    final int REQUEST_ADD_BOOK=134;
    final int REQUEST_EDIT_BOOK=290;

    int position;


    //책 추가 버튼
    Button btn_addBook;
    //책 추가 버튼 누르면 나올 선택지 리스트
    CharSequence[] list_howToAddBook = {"직접 입력","책 검색하기","바코드 스캔"};
    //하단 메뉴 버튼
    Button btn_to_calendar;
    Button btn_to_drawer;
    Button btn_to_essay;
    Button btn_to_setting;
    Button btn_to_stat;
    /////////////////////////////



    View.OnClickListener editListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            position = (int) view.getTag();

            if(mAdapter.getItemCount()>0){ //뭐라도 하나 넣은 다음에 실행되어야 오류가 나지 않는다.
                if(position!=RecyclerView.NO_POSITION){//노 포지션이라고 안나오면 진행한다.
                    Dictionary_reading dict = mList.get(position);

                    Intent intentEdit = new Intent(getApplicationContext(), AddBook_toReading.class);
                    intentEdit.putExtra("dict", dict);
                    startActivityForResult(intentEdit,REQUEST_EDIT_BOOK);
                }

            }

        }
    };
//    protected void goToEdit() {
//
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        ///////////////////////////////////////////////////리사이클러뷰
        mRecylerView = findViewById(R.id.recycler_reading);
        //리사이클러뷰에 어댑터 객체 지정
        mAdapter = new Adapter_Reading(mList,editListener);
        mRecylerView.setAdapter(mAdapter);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        mRecylerView.setLayoutManager(gridLayoutManager);

//
//        addItem(getDrawable(R.drawable.book_crazylady),"Crazy Lady!");
//        addItem(getDrawable(R.drawable.book_habits),"Atomic Habits");
//        addItem(getDrawable(R.drawable.book_jieun),"김지은입니다");

        ///////////////////////////////////////////////////////리사이클러뷰




        Button btnToInterested = (Button) findViewById(R.id.btn_to_interested);
        btnToInterested.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getApplicationContext(),Drawer_interested.class);
                        startActivity(intent);

                    }
                }
        );

        Button btnToRead = (Button) findViewById(R.id.btn_to_read);
        btnToRead.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getApplicationContext(),Drawer_read.class);
                        startActivity(intent);

                    }
                }
        );

//        ImageButton btnToBook;
//        btnToBook = (ImageButton)findViewById(R.id.btn_readingD_bookcover);
//        btnToBook.setOnClickListener(
//                new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        Intent intent = new Intent(getApplicationContext(),Bookdetail_note_byPage.class);
//                        startActivity(intent);
//                    }
//                }
//        );



//        ImageButton btnToCrazy;
//        btnToCrazy = (ImageButton)findViewById(R.id.btn_crazy);
//        btnToCrazy.setOnClickListener(
//                new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        Intent intent = new Intent(getApplicationContext(),BookLog_Notes.class);
//                        startActivity(intent);
//                    }
//                }
//        );





        //책 추가하기 버튼
        btn_addBook = (Button) findViewById(R.id.btn_addBook);
        //책 추가 버튼 클릭 리스너
        btn_addBook.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //책 추가버튼 누를 때마다 동일하게 세팅된 정보가 리사이클러뷰에 보이도록 함
//                        dic = new Dictionary_reading(getDrawable(R.drawable.book_crazylady),"Crazy Lady!");
//                        mList.add(dic);
//                        mAdapter.notifyDataSetChanged();

                        //////////////////////////////////////////////


                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                        builder.setTitle("책 추가하기") //팝업창 제목
                                .setItems(list_howToAddBook, new DialogInterface.OnClickListener() {
                                    //선택목록이랑 클릭 이벤트 리스너를 같이 주는군
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        switch (i) {
                                            case 0:
                                                Intent intent = new Intent(getApplicationContext(), AddBook_toReading.class);
                                                startActivityForResult(intent,REQUEST_ADD_BOOK);
                                                break;
                                            case 1:
                                                Toast.makeText(getApplicationContext(), list_howToAddBook[1]+"를 골랐습니다.",Toast.LENGTH_LONG).show();
                                                break;
                                            case 2:
                                                Toast.makeText(getApplicationContext(), list_howToAddBook[2]+"을 골랐습니다.",Toast.LENGTH_LONG).show();
                                                break;
                                        }

                                    }
                                });

                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                    }
                }
        );



        // 하단 메뉴바 버튼 선언
        btn_to_calendar = (Button)findViewById(R.id.btn_to_calender);
        btn_to_essay = (Button)findViewById(R.id.btn_to_assay);
        btn_to_stat = (Button)findViewById(R.id.btn_to_stat);
        btn_to_setting = (Button)findViewById(R.id.btn_to_setting);
        // 하단 메뉴바 버튼 선언 끝
        // 하단 메뉴바 클릭 이벤트
        btn_to_calendar.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent intent = new Intent(getApplicationContext(), Calendar.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                        startActivity(intent);

                    }
                }
        );

        btn_to_essay.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent intent = new Intent(getApplicationContext(),Essay.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                        startActivity(intent);

                    }
                }
        );


        btn_to_stat.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent intent = new Intent(getApplicationContext(),Stat.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                        startActivity(intent);

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

        // 하단 메뉴바 클릭 이벤트 끝


    }
    ///////////////////////////////////////온크리에이트 여기까지

//    public Dictionary_reading addItem(Drawable bookcoverImg, String title){
//        Dictionary_reading dic = new Dictionary_reading();
//
//        dic.setBookCover(bookcoverImg);
//        dic.setBookTitle(title);
//
//        return dic;
//    }




    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
        protected void onResume() {
            super.onResume();



    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK && requestCode == REQUEST_ADD_BOOK) {

            byte[] arr = data.getByteArrayExtra("image");
            Bitmap image = BitmapFactory.decodeByteArray(arr, 0, arr.length);
            Drawable bookCover = new BitmapDrawable(image);
            String title = data.getStringExtra("title");
            Dictionary_reading dict = new Dictionary_reading(image, title);

            mList.add(dict);
            mAdapter.notifyDataSetChanged();

        }else if(resultCode==RESULT_OK && requestCode == REQUEST_EDIT_BOOK){


                    byte[] editArr = data.getByteArrayExtra("image");
                    Bitmap editImage = BitmapFactory.decodeByteArray(editArr, 0, editArr.length);
                    Drawable eidtCover = new BitmapDrawable(editImage);
                    String editTitle = data.getStringExtra("title");
                    Dictionary_reading editDict = new Dictionary_reading(editImage,editTitle);

                    mList.set(position,editDict);
                    mAdapter.notifyItemChanged(position);
                   // mAdapter.notifyDataSetChanged();


            }
        }

}


// 전화걸기 / 특정 링크로 이동하기 예제

////  액티비티 onCreate 안에 특정 아이디를 가진 텍스트 뷰를 선언하고
//        num_textVIew = (TextView)findViewById(R.id.num_textView);
//                url_textVIew = (TextView)findViewById(R.id.url_textView);
//                // 클릭하면 이벤트가 발생하도록 하고 무슨 일을 실행시킬지는 mClickListener에서 정의
//                num_textVIew.setOnClickListener(mClickListener);
//                url_textVIew.setOnClickListener(mClickListener);

// 온크리에이트 외부에 클릭이 발생하면 무슨 일이 일어날건지 내가 정한 메소드 만들기
//View.OnClickListener mClickListener = new View.OnClickListener() {
//    @Override
//    public void onClick(View view) {
//        switch (view.getId()){
//            case R.id.num_textView :
//                //번호 텍스트뷰를 클릭하면 전화거는 액션을 가진 인텐트를 실행할건데 번호는 해당 아이디의 텍스트 값을 가져와서 스트링으로 변환한 것
//                Intent intent = new Intent(Intent.ACTION_DIAL,
//                        Uri.parse("tel:"+num_textVIew.getText().toString()));
//                startActivity(intent);
//
//                break;
//            case R.id.url_textView :
//
//                Intent intent2 = new Intent(Intent.ACTION_VIEW,
//                        Uri.parse(url_textVIew.getText().toString()));
//                startActivity(intent2);
//                break;
//
//        }
//    }
//};