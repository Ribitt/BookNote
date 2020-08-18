package com.example.booknoteapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class EditBook extends AppCompatActivity {
    // private final int MY_PERMISSIONS_REQUEST_CAMERA = 1001;

    int position=0;
    Dictionary_book dictionary_book;

    //현재시간 가져오는 메소드
    long now = System.currentTimeMillis();
    Date date = new Date(now);
    SimpleDateFormat mFormat = new SimpleDateFormat("yyyy.MM.dd");
    String time = mFormat.format(date);
    //현재시간 가져오는 메소드
    //달력
    Calendar cal = Calendar.getInstance();
    //달력


    //책 상태
    Spinner status_spinner;
    boolean read =true;
    boolean reading = false;
    boolean interested = false;
    String changedStatus = "read";
    String originalStatus ="";
    //책 상태


    ArrayList<Dictionary_book> bookList = new ArrayList<>();

    ArrayList<Dictionary_book> originalList = new ArrayList<>();
    ArrayList<Dictionary_book> changedList = new ArrayList<>();



    ////이미지/카메라 받아오기
    private static final int PICK_FROM_ALBUM =185;
    private static final int PICK_FROM_CAMERA = 195;
    private File tempFile;
    String currentPhotoPath;
    Bitmap coverBitmap;
    ////이미지/카메라 받아오기

    ImageView imageV_addBook_addBookCover;
    EditText et_title;
    EditText et_author;
    EditText et_page;
    EditText et_publisher;

    LinearLayout layout_read;
    LinearLayout layout_interested;
    LinearLayout layout_startDate;

    TextView tv_addBookCover;
    EditText et_addBook_read_ALineReview;
    RatingBar rating_addBook_read;
    TextView tv_addBook_read_finishDate;

    TextView tv_addBook_read_startDate;
    EditText et_addBook_interested_memo;


    SharedPreferences pref;
    SharedPreferences.Editor editor;




    private final int CAMERA_REQUEST = 19;
    final CharSequence[] cameraOrGallery = {"사진 찍기","갤러리에서 불러오기"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_book);

        //////툴바 적용하기
        Toolbar toolbar = (Toolbar)findViewById(R.id.app_toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.green));
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("책 수정하기");


        initialize();
        allListener();

        setData(); //넘어온 책 정보와 인덱스 넘버를 정의해주는 메소드




    }//온크리에이트 여기까지

    private void setData() {

        Intent intent = getIntent();
        //책 수정하기 버튼으로 오기 때문에 무조건 인텐트가 존재한다.

        dictionary_book = (Dictionary_book)intent.getSerializableExtra("selectedBook");
        position = intent.getIntExtra("position",0);


        if(dictionary_book.bookCover==null){

        }else{
            imageV_addBook_addBookCover.setImageBitmap(dictionary_book.getBookCover());
            tv_addBookCover.setText("");
            coverBitmap = dictionary_book.getBookCover();
        }

        et_title.setText(dictionary_book.getTitle());
        et_author.setText(dictionary_book.getAuthor());
        et_publisher.setText(dictionary_book.getPublisher());
        et_page.setText(dictionary_book.getPageNum());

        originalStatus = dictionary_book.getStatus();
        changedStatus = dictionary_book.getStatus();
        //처음에는 둘 다 책 원래 책장상태를 저장한다.

        int status_position =0;
          switch (dictionary_book.getStatus()){
          case "reading":
              status_position =0;
              break;
          case "read":
              status_position=1;
              break;
          case "interested":
              status_position=2;
          }
        status_spinner.setSelection(status_position);

          ///읽은 책
        tv_addBook_read_finishDate.setText(dictionary_book.getFinishedDate());
        rating_addBook_read.setRating(dictionary_book.getRating());
        et_addBook_read_ALineReview.setText(dictionary_book.getReview());


        //읽는 중인 책
        tv_addBook_read_startDate.setText(dictionary_book.getFinishedDate());


        //읽을 책
        et_addBook_interested_memo.setText(dictionary_book.getMemo());

    }

    private void initialize() {

        pref = getSharedPreferences("book", MODE_PRIVATE);
        editor = pref.edit();



        //뷰 위에서부터 초기화~~

        //책 표지
        imageV_addBook_addBookCover = findViewById(R.id.imageV_addBook_addBookCover);
        tv_addBookCover = findViewById(R.id.tv_addBookCover);

        //입력창
        et_title = findViewById(R.id.et_addBook_title);
        et_author = findViewById(R.id.et_addBook_author);
        et_page = findViewById(R.id.et_addBook_page);
        et_publisher = findViewById(R.id.et_addBook_publisher);

        status_spinner = findViewById(R.id.spinner_addBook);


        layout_read =findViewById(R.id.layout_addBook_read);
        layout_interested = findViewById(R.id.layout_addBook_interested);
        layout_startDate =findViewById(R.id.layout_startDate);


        rating_addBook_read = findViewById(R.id.rating_addBook_read);
        et_addBook_read_ALineReview = findViewById(R.id. et_addBook_read_ALineReview);
        tv_addBook_read_finishDate = findViewById(R.id.tv_addBook_read_finishedDate);

        tv_addBook_read_startDate =findViewById(R.id.tv_addBook_read_startDate);

        et_addBook_interested_memo = findViewById(R.id.et_addBook_interested_memo);

        //뷰 초기화 끝

        //날짜 표시되는 부분 오늘 날짜로 세팅
        tv_addBook_read_finishDate.setHint(time);
        tv_addBook_read_startDate.setHint(time);
        //날짜 표시되는 부분 오늘 날짜로 세팅

    }//이니셜라이저 끝

    private void allListener() {

        //책 상태 고르는 스피너 클릭 이벤트
        status_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {

                switch (position){

                    case 1:
                        //읽은 책
                        reading = false;
                        read=true;
                        interested =false;
                        changedStatus = "read";
                        layout_startDate.setVisibility(View.VISIBLE);
                        layout_read.setVisibility(View.VISIBLE);
                        layout_interested.setVisibility(View.INVISIBLE);
                       // Toast.makeText(getApplicationContext(), "원래 상태 : "+originalStatus+"지금 상태 : "+changedStatus, Toast.LENGTH_LONG).show();
                        break;
                    case 0:
                        //읽는 중인 책
                        reading = true;
                        read=false;
                        interested =false;
                        changedStatus ="reading";
                      //  Toast.makeText(getApplicationContext(), "원래 상태 : "+originalStatus+"지금 상태 : "+changedStatus, Toast.LENGTH_LONG).show();
                        layout_read.setVisibility(View.INVISIBLE);
                        layout_startDate.setVisibility(View.VISIBLE);
                        layout_interested.setVisibility(View.INVISIBLE);

                        break;
                    case 2:
                        reading = false;
                        read=false;
                        interested =true;
                        changedStatus ="interested";
                      //  Toast.makeText(getApplicationContext(), "원래 상태 : "+originalStatus+"지금 상태 : "+changedStatus, Toast.LENGTH_LONG).show();
                        layout_startDate.setVisibility(View.INVISIBLE);
                        layout_read.setVisibility(View.INVISIBLE);
                        layout_interested.setVisibility(View.VISIBLE);
                        //읽고싶은 책
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        //////////////////////////////책 상태 고르는 스피너 클릭 이벤트


        //다 읽은 날짜 클릭 리스너

        tv_addBook_read_finishDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatePickerDialog dialog = new DatePickerDialog(EditBook.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int date) {

                        tv_addBook_read_finishDate.setText(String.format("%2d.%02d.%02d",year,month+1,date));
                    }
                }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE));

                dialog.getDatePicker().setMaxDate(new Date().getTime());    //오늘 날짜 이후의 날짜는 선택 안되게 하기
                dialog.show();


            }
        });
        /////////////////////////////////다 읽은 날짜 클릭 리스너


        //////////////////////////////읽고 있는 책 마지막으로 읽은 날 클릭 리스너

        tv_addBook_read_startDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatePickerDialog dialog = new DatePickerDialog(EditBook.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int date) {

                        tv_addBook_read_startDate.setText(String.format("%2d.%02d.%02d",year,month+1,date));
                        //이 데이터값을 어디에 따로 표시해두는 게 나중에 편하겠다. 이미 텍스트가 된 날짜값을 다시 구해오는 것보다 훨씬 낫겠지
                    }
                }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE));

                dialog.getDatePicker().setMaxDate(new Date().getTime());    //오늘 날짜 이후로 클릭 안되게 옵션
                dialog.show();


            }
        });
        //읽고 있는 책 마지막으로 읽은 날 클릭 리스너




    }//이벤트 리스너 끝


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    Boolean isTitle=true;
    Boolean isAuthor =false;

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home: {

                return true;
            }
            case R.id.btn_done: {

                if(et_title.getText().toString().equals("")){
                    isTitle=false;
                }else {
                    isTitle=true;
                }
                if(et_author.getText().toString().equals("")){
                    isAuthor =false;
                }else{
                    isAuthor= true;
                }
                ////////////////////////////////////////////작가와 저자를 모두 입력했는지 확인하기 위해 판별함

                if(isTitle){

                    if(isAuthor){

                        Intent intent = new Intent(getApplicationContext(), DrawerTap.class);

                        //수정한 정보를 dictionary에 담는다
                        putDateIntoDict();

                        //원래 책이 들어가있는 책 리스트를 가져온다
                        originalList = getList(originalStatus);
                        //getOriginalList();
                        if(originalStatus.equals(changedStatus)){//만약 책 상태를 바꾸지 않았다면

                            originalList.set(position,dictionary_book);
                            //원래 리스트에 해당 포지션에 책을 넣어준다

                            saveBookArrayToPref(originalList, originalStatus);
                            //리스트를 쉐어드에 저장한다.

                        }else{//책 상태를 바꿨다면

                         originalList.remove(position);
                         //원래 리스트에서 그녀석을 지우고
                            saveBookArrayToPref(originalList,originalStatus);
                            //저장

                         changedList = getList(changedStatus);
                         changedList.add(0,dictionary_book);
                         //새로운 리스트 0번에다가 집어넣고
                         saveBookArrayToPref(changedList,changedStatus);
                         //저장
                        }

                        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                        startActivity(intent);
                        finish();


//                        //추가할 내용을 다 더해준 리스트를 북리스트에 추가한 뒤에
//                        bookList.add(0,dictionary_book);
//                        //쉐어드에 저장해준다.
//                        saveBookArrayToPref(bookList);
//                        //이제 책 추가 액티비티는 종료

                        ///////////////////////////////////////////////////////////////////////////////////////////////////책 저장하기 완료

                    }else{
                        Toast.makeText(this, "작가를 입력해주세요", Toast.LENGTH_LONG).show();
                    }


                }else{
                    Toast.makeText(this, "제목을 입력해주세요", Toast.LENGTH_LONG).show();
                }



            }


        }
        return super.onOptionsItemSelected(item);
    }

    public void putDateIntoDict() {
        //공통으로 꼭 들어가야 할 요소를 넣어준다
        dictionary_book = new Dictionary_book(changedStatus,et_title.getText().toString(),et_author.getText().toString());
        //책 커버 이미지가 있다면 이미지를 넣어준다.
        if(coverBitmap!=null){
            dictionary_book.setBookCover(coverBitmap);
        }
        dictionary_book.setPageNum(et_page.getText().toString());
        dictionary_book.setPublisher(et_publisher.getText().toString());

        //각 상태별로 다양한 내용을 더 넣어준다.
        if(reading){

            dictionary_book.setStartDate(tv_addBook_read_startDate.getText().toString());
        }else if(read) {
            dictionary_book.setStartDate(tv_addBook_read_startDate.getText().toString());
            dictionary_book.setFinishedDate(tv_addBook_read_finishDate.getText().toString());
            dictionary_book.setRating(rating_addBook_read.getRating());
            dictionary_book.setReview( et_addBook_read_ALineReview.getText().toString());

        }else if(interested){

            dictionary_book.setMemo(et_addBook_interested_memo.getText().toString());
        }

    }


    private ArrayList<Dictionary_book> getList(String status){
        //저장된 읽는 상태에 따라서 원래 리스트를 가져온다
        Gson gson = new Gson();
        String json ="EMPTY";


        switch (status){
            case "reading":
                json = pref.getString("reading","EMPTY");
                break;
            case "read":
                json = pref.getString("read","EMPTY");
                break;
            case "interested":
                json = pref.getString("interested","EMPTY");
        }

        Type type = new TypeToken<ArrayList<Dictionary_book>>() {
        }.getType();
        ArrayList<Dictionary_book> list = gson.fromJson(json,type);
        return list;
    }


    private void getChangedList(){
        //저장된 읽는 상태에 따라서 원래 리스트를 가져온다
        Gson gson = new Gson();
        String json ="EMPTY";


        switch (changedStatus){
            case "reading":
                json = pref.getString("reading","EMPTY");
                break;
            case "read":
                json = pref.getString("read","EMPTY");
                break;
            case "interested":
                json = pref.getString("interested","EMPTY");
        }

        Type type = new TypeToken<ArrayList<Dictionary_book>>() {
        }.getType();
        changedList = gson.fromJson(json,type);

    }

    private void getOriginalList(){
        //저장된 읽는 상태에 따라서 원래 리스트를 가져온다
        Gson gson = new Gson();
        String json ="EMPTY";


        switch (originalStatus){
            case "reading":
                json = pref.getString("reading","EMPTY");
                break;
            case "read":
                json = pref.getString("read","EMPTY");
                break;
            case "interested":
                json = pref.getString("interested","EMPTY");
        }

            Type type = new TypeToken<ArrayList<Dictionary_book>>() {
            }.getType();
            originalList = gson.fromJson(json,type);

    }



    //어레이리스트 쉐어드에 저장하기

    private void saveBookArrayToPref(ArrayList<Dictionary_book> arrayList, String status) {
        Gson gson = new Gson();
        String json = gson.toJson(arrayList);

        switch (status){
            case "read" :
                editor.putString("read",json);
                break;
            case "reading":
                editor.putString("reading",json);
                break;
            case "interested":
                editor.putString("interested",json);
                break;
        }
        editor.apply();

    }

    //어레이리스트 쉐어드에 저장하기 끝






    @Override
    protected void onPostResume() {
        super.onPostResume();

        //이미지 추가 버튼 눌렀을 때
        imageV_addBook_addBookCover.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        //클릭하면 배경 사진이 바뀌고 텍스트는 없어짐
//                        btn_addBook_addBookCover.setBackground(getDrawable(R.drawable.book_jieun));
//                        btn_addBook_addBookCover.setText("");


                        //카메라 불러오는 메소드
                        camGalleryPermissionCheck(); //카메라,앨범 권한이 승인돼있는지 확인하는 메소드

                        AlertDialog.Builder builder = new AlertDialog.Builder(EditBook.this);

                        builder.setTitle("이미지 추가")
                                .setItems(cameraOrGallery, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        switch(i) {
                                            case 0: //사진찍기인 경우
                                                takePicture();


                                                break;
                                            case 1: //갤러리에서 불러오기인 경우

                                                Intent getImgIntent = new Intent();
                                                getImgIntent.setAction(Intent.ACTION_PICK);
                                                getImgIntent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                                                startActivityForResult(getImgIntent,PICK_FROM_ALBUM);
                                                break;
                                        }

                                    }
                                });

                        AlertDialog imagePopUp = builder.create();
                        imagePopUp.show();
                    }
                }
        );
    }


    /////사진찍기
    protected void takePicture() {

        // 인텐트의 ACTIONImageCapture 속성을 이용해 카메라를 띄운다.
        // 그런 뒤 찍은 사진을 intent에 담아서 startActivityForResult를 이용해서 보낸다
        // 보낸 intent는 onActivityResult에서 받는다. 여기서 사이즈를 줄여준다.
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(takePictureIntent.resolveActivity(getPackageManager())!=null){//카메라 기능이 있는지, 사용가능한지 확인

            File photoFile = null;
            //사진을 새로 찍을 경우 빈 포토 파일을 만들어주기
            try{

                photoFile = createImageFile();

            }catch(IOException e){
                Toast.makeText(EditBook.this, "이미치 처리 오류 발생. 다시 시도해주세요",Toast.LENGTH_LONG).show();
                finish();
                e.printStackTrace();
            }

            if(photoFile!=null){
                //
                Uri photoURI = FileProvider.getUriForFile(EditBook.this,
                        "com.example.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, PICK_FROM_CAMERA);
            }


        }else{
            Toast.makeText(EditBook.this,"사용할 수 있는 카메라가 없습니다",Toast.LENGTH_LONG).show();
        }
    }
    //////////사진 찍기


    //카메라에서 찍은 사진 저장하기

    private File createImageFile() throws IOException{
        //이미지 파일 이름 지정
        //지금 시간을 가져다가 마이 이미지_시간_이렇게 저장되게 함
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "myImage_"+timeStamp+"_";

        //이미지가 저장될 폴더 이름 (remi)
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        if(!storageDir.exists())storageDir.mkdirs();

        //빈 파일 생성
        File image = File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir
        );

        currentPhotoPath = image.getAbsolutePath();
        //파일에 이름이랑 양식이랑 넣어서 반환
        return image;
    }

    //카메라에서 찍은 사진 저장하기


    ///인텐트에 정보가 포함되어 넘어온다
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode!= Activity.RESULT_OK) {
            //예외상황 발생 처리 ex) 이동했지만 선택하지 않고 뒤로 간 경우, 카메라로 촬영 후 저장하지 않고 뒤로가기를 누른 경우.
            //사진 촬영중 취소하면 tempFile이 빈 섬네일로 저장되기 때문에 삭제해줘야한다.
            Toast.makeText(this,"취소되었습니다.", Toast.LENGTH_LONG).show();

            if(tempFile!=null){
                if(tempFile.exists()){
                    if(tempFile.delete()){

                        // Toast.makeText(this,"삭제성공", Toast.LENGTH_LONG).show();
                        tempFile=null;
                    }
                }
            }
            return;
            //진행하지 마세요
        }


        if(requestCode==PICK_FROM_ALBUM){

            //갤러리에서 선택한 이미지의 Uri를 받아온다.
            Uri photoUri = data.getData();

            //커서를 통해 스키마를 content://에서 file://로 바꿔준다. 사진이 저장된 절대경로를 받아오는 과정이다.
            Cursor cursor=null;
            try{
                String[] proj = {MediaStore.Images.Media.DATA};

                assert photoUri !=null;
                cursor = getContentResolver().query(photoUri, proj,null,null,null);

                assert cursor!=null;
                int columm_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

                cursor.moveToFirst();

                //여기에 이미지를 저장한다.
                tempFile = new File(cursor.getString(columm_index));
                currentPhotoPath = tempFile.getAbsolutePath();

            }finally {

            }
            setPic();

        }else if(requestCode==PICK_FROM_CAMERA){

            setPic();

        }
    }




    //찍거나 갤러리에서 가져온 사진 이미지 크기 줄여서 넣기
    private void setPic(){
        //사진 들어갈 자리 크기를 구한다
        int targetW = imageV_addBook_addBookCover.getWidth();
        int targetH = imageV_addBook_addBookCover.getHeight();

        //비트맵의 디멘션을 구한다.
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;

        BitmapFactory.decodeFile(currentPhotoPath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        //얼마나 줄일건지를 정한다
        int scaleFactor = Math.max(1, Math.min(photoW/targetW, photoH/targetH));

        //이미지 파일을 뷰에 딱 맞는 사이즈로 줄어든 비트맵으로 만든다
        bmOptions.inJustDecodeBounds=false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable=true;

        Bitmap bitmap = BitmapFactory.decodeFile(currentPhotoPath, bmOptions);
        imageV_addBook_addBookCover.setImageBitmap(bitmap);
        tv_addBookCover.setText("");
        coverBitmap = bitmap;

    }
    //찍어온 사진 이미지 크기 줄여서 넣기





    //권한승인
    public void camGalleryPermissionCheck() {
        PermissionListener permissionListener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                Toast.makeText(EditBook.this,"Permission Granted",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onPermissionDenied(ArrayList<String> deniedPermissions) {
                Toast.makeText(EditBook.this, "Permission Denied\n" +deniedPermissions.toString(), Toast.LENGTH_LONG).show();
            }
        };

        TedPermission.with(EditBook.this)
                .setPermissionListener(permissionListener)
                .setRationaleMessage("책 표지를 직접 입력하려면 카메라, 갤러리 접근 권한이 필요합니다.")
                .setDeniedMessage("책 표지를 직접 입력하시려면 \n[설정]-[권한]에서 권한을 승인해주세요")
                .setPermissions(Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .check();
    }

    //권한승인 끝
}