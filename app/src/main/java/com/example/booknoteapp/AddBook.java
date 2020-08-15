package com.example.booknoteapp;

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
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
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

public class AddBook extends AppCompatActivity {
    // private final int MY_PERMISSIONS_REQUEST_CAMERA = 1001;

    //현재시간 가져오는 메소드
    long now = System.currentTimeMillis();
    Date date = new Date(now);
    SimpleDateFormat mFormat = new SimpleDateFormat("yyyy.MM.dd");
    String time = mFormat.format(date);
    //현재시간 가져오는 메소드

    //달력
    Calendar cal = Calendar.getInstance();
    //달력

    Bitmap backBitmap;

    //책 상태
    Spinner status_spinner;
    boolean read =true;
    boolean reading = false;
    boolean interested = false;
    String status = "read";
    //책 상태

    Boolean isStatusSelected=false;

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

    LinearLayout layout_reading;
    LinearLayout layout_read;
    LinearLayout layout_interested;

    TextView tv_addBookCover;
    EditText et_addBook_read_ALineReview;
    RatingBar rating_addBook_read;
    TextView tv_addBook_read_endDate;

    TextView tv_addBook_reading_lastDate;
    EditText et_addBook_interested_memo;

    Button btn_addBook_cancel;
    Button btn_addBook_done;

    SharedPreferences pref;
    SharedPreferences.Editor editor;

    ArrayList<Dictionary_book> bookList = new ArrayList<>();


    private final int CAMERA_REQUEST = 19;
    final CharSequence[] cameraOrGallery = {"사진 찍기","갤러리에서 불러오기"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);

        //////툴바 적용하기
        Toolbar toolbar = (Toolbar)findViewById(R.id.app_toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.green));
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("책 추가하기");


        initialize();
        allListener();


       /////////////////////////////////////////////////////책 정보 수정하기 버튼으로 온 경우 / 그냥 책 추가하기 경우
        Intent intent = getIntent();
        if(intent.getExtras()==null) {
//            Toast.makeText(this, "든 게 없음", Toast.LENGTH_LONG).show();
            //책 추가하기 버튼으로 이 화면을 부른 경우다
        }else{
//            Toast.makeText(this, "어, 뭐가 왔는데", Toast.LENGTH_LONG).show();
            //책 수정하기 버튼으로 온 경우다
//            tv_addBookCover.setVisibility(View.INVISIBLE);
//
//            Dictionary_reading dic = intent.getParcelableExtra("dict");
//            //byte[] arr = intent.getByteArrayExtra("image");
//            //Bitmap image = BitmapFactory.decodeByteArray(arr, 0, arr.length);
//            Bitmap image = dic.getBookCover();
//            backBitmap = image;
//           // Drawable bookCover = new BitmapDrawable(image);
//            Drawable bookCover = new BitmapDrawable(image);
//            imageV_addBook_addBookCover.setBackground(bookCover);
//            //백그라운드는 드로어블밖에 지정이 안된다. 짜증
//            String title = dic.getBookTitle();
//            et_title.setText(title);
            actionBar.setTitle("책 수정하기");
            btn_addBook_done.setText("수정하기");
        }


    }//온크리에이트 여기까지



    private void initialize() {

        pref = getSharedPreferences("book", MODE_PRIVATE);
        editor = pref.edit();


        //수정 가능한 뷰 초기화

        layout_reading = findViewById(R.id.layout_addBook_reading);
        layout_read =findViewById(R.id.layout_addBook_read);
        layout_interested = findViewById(R.id.layout_addBook_interested);

        status_spinner = findViewById(R.id.spinner_addBook);

        imageV_addBook_addBookCover = findViewById(R.id.imageV_addBook_addBookCover);
//        btn_addBook_status_read = findViewById(R.id.btn_addBook_status_read);
//        btn_addBook_status_reading = findViewById(R.id.btn_addBook_status_reading);
//        btn_addBook_status_interested = findViewById(R.id.btn_addBook_status_interested);

        btn_addBook_done = findViewById(R.id.btn_addBook_done);
        btn_addBook_cancel = findViewById(R.id.btn_addBook_cancel);

        //입력창 초기화
        et_title = findViewById(R.id.et_addBook_title);
        et_author = findViewById(R.id.et_addBook_author);

        rating_addBook_read = findViewById(R.id.rating_addBook_read);
        et_addBook_read_ALineReview = findViewById(R.id. et_addBook_read_ALineReview);
        tv_addBook_read_endDate = findViewById(R.id.tv_addBook_read_finishedDate);

        tv_addBook_reading_lastDate =findViewById(R.id.tv_addBook_reading_lastDate);

        et_addBook_interested_memo = findViewById(R.id.et_addBook_interested_memo);

        tv_addBookCover = findViewById(R.id.tv_addBookCover);

        //뷰 초기화 끝

        //날짜 표시되는 부분 오늘 날짜로 세팅
        tv_addBook_read_endDate.setHint(time);
        tv_addBook_reading_lastDate.setHint(time);
        //날짜 표시되는 부분 오늘 날짜로 세팅

    }//이니셜라이저 끝

    private void allListener() {

        //책 상태 고르는 스피너 클릭 이벤트
        status_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                isStatusSelected=true;
                switch (position){

                    case 0:
                        //읽은 책
                        reading = false;
                        read=true;
                        interested =false;
                        status = "read";
                        layout_reading.setVisibility(View.INVISIBLE);
                        layout_read.setVisibility(View.VISIBLE);
                        layout_interested.setVisibility(View.INVISIBLE);
                        break;
                    case 1:
                        //읽는 중인 책
                        reading = true;
                        read=false;
                        interested =false;
                        status ="reading";
                        layout_reading.setVisibility(View.VISIBLE);
                        layout_read.setVisibility(View.INVISIBLE);
                        layout_interested.setVisibility(View.INVISIBLE);

                        break;
                    case 2:
                        reading = false;
                        read=false;
                        interested =true;
                        status ="interested";
                        layout_reading.setVisibility(View.INVISIBLE);
                        layout_read.setVisibility(View.INVISIBLE);
                        layout_interested.setVisibility(View.VISIBLE);
                        //읽고싶은 책
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                isStatusSelected=false;
            }
        });
        //////////////////////////////책 상태 고르는 스피너 클릭 이벤트


        //다 읽은 날짜 클릭 리스너

        tv_addBook_read_endDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatePickerDialog dialog = new DatePickerDialog(AddBook.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int date) {

                        tv_addBook_read_endDate.setText(year+"."+(int)(month+1)+"."+date);
                    }
                }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE));

                dialog.getDatePicker().setMaxDate(new Date().getTime());    //오늘 날짜 이후의 날짜는 선택 안되게 하기
                dialog.show();


            }
        });
        /////////////////////////////////다 읽은 날짜 클릭 리스너


        //////////////////////////////읽고 있는 책 마지막으로 읽은 날 클릭 리스너

        tv_addBook_reading_lastDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatePickerDialog dialog = new DatePickerDialog(AddBook.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int date) {

                        tv_addBook_reading_lastDate.setText(year+"."+(int)(month+1)+"."+date);
                        //이 데이터값을 어디에 따로 표시해두는 게 나중에 편하겠다. 이미 텍스트가 된 날짜값을 다시 구해오는 것보다 훨씬 낫겠지
                    }
                }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE));

                dialog.getDatePicker().setMaxDate(new Date().getTime());    //오늘 날짜 이후로 클릭 안되게 옵션
                dialog.show();


            }
        });
        //읽고 있는 책 마지막으로 읽은 날 클릭 리스너

        //취소 버튼 눌렀을 때
        btn_addBook_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //확인 버튼을 눌렀을 때
        btn_addBook_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!et_title.getText().toString().equals("") && !et_author.getText().toString().equals("")){
                    /////////////////////////////////////////////////////////////////////////////////////////이러면 책을 저장하고 어쩌고를 할 수 있다. 아니라면 썩 꺼져라

                    Intent intent = new Intent(getApplicationContext(), DrawerTap.class);
                    //필수는 제목이랑 저자
                    Dictionary_book dictionary_book = new Dictionary_book(status,et_title.getText().toString(),et_author.getText().toString());

                    if(reading){

                        dictionary_book.setBookCover(coverBitmap);
                    }else if(read) {

                        dictionary_book.setFinishedDate(tv_addBook_read_endDate.getText().toString());
                        dictionary_book.setRating(rating_addBook_read.getRating());
                        dictionary_book.setReview( et_addBook_read_ALineReview.getText().toString());
                        dictionary_book.setBookCover(coverBitmap);

                    }else if(interested){
                        dictionary_book.setBookCover(coverBitmap);
                        dictionary_book.setMemo(et_addBook_interested_memo.getText().toString());
                    }

                    //쉐어드에 저장된 어레이 리스트를 가져온 다음에 맨 위에 지금 리스트를 넣어주기
                    getPrefToArray();
                    bookList.add(0,dictionary_book);
                    saveBookArrayToPref(bookList);

                    startActivity(intent);
                    finish();
                    /////////////////////////////////////////////////////////////////////////////////////////////////////책 저장하기 완료 
            }else{
                    ///////////////////////////////////////////////////책 제목이나 저자를 입력하지 않은 경우에는 넘어갈 수 없음.
                if(et_title.getText().toString().equals("")){
                    Toast.makeText(AddBook.this,"책 제목을 입력해주세요",Toast.LENGTH_LONG);
                }else if(et_author.getText().toString().equals("")){
                    Toast.makeText(AddBook.this,"저자를 입력해주세요",Toast.LENGTH_LONG);
                }
            }


            }
        });
        ////////////////////////////확인 버튼 눌렀을 때 끝



    }//이벤트 리스너 끝

    ///쉐어드 프리퍼런스 가져와서 어레이 리스트로 바꿔주기
    private void getPrefToArray() {
        Gson gson = new Gson();
        String json = pref.getString("books","EMPTY");
        if(!json.equals("EMPTY")){
            Type type = new TypeToken<ArrayList<Dictionary_book>>() {
            }.getType();
            bookList = gson.fromJson(json,type);
            Log.d("들어오는지 확인", json);
            //   Log.d("대체 어레이 리스트가 존재는 하는지 확인 ", String.valueOf(getArrayList.size()));
            //  Log.d("대체 어레이 리스트가 존재는 하는지 확인 ", getArrayList.get(0).getTitle());
        }else{
            // 내용이 없으면 가져오지 않음
        }


    }
    ///쉐어드 프리퍼런스 가져와서 어레이 리스트로 바꿔주기 끝

    //어레이리스트 쉐어드에 저장하기

    private void saveBookArrayToPref(ArrayList<Dictionary_book> arrayList) {
        Gson gson = new Gson();
        String json = gson.toJson(arrayList);
        editor.putString("books",json);
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

                        AlertDialog.Builder builder = new AlertDialog.Builder(AddBook.this);

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
                Toast.makeText(AddBook.this, "이미치 처리 오류 발생. 다시 시도해주세요",Toast.LENGTH_LONG).show();
                finish();
                e.printStackTrace();
            }

            if(photoFile!=null){
                //
                Uri photoURI = FileProvider.getUriForFile(AddBook.this,
                        "com.example.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, PICK_FROM_CAMERA);
            }


        }else{
            Toast.makeText(AddBook.this,"사용할 수 있는 카메라가 없습니다",Toast.LENGTH_LONG).show();
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
                Toast.makeText(AddBook.this,"Permission Granted",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onPermissionDenied(ArrayList<String> deniedPermissions) {
                Toast.makeText(AddBook.this, "Permission Denied\n" +deniedPermissions.toString(), Toast.LENGTH_LONG).show();
            }
        };

        TedPermission.with(AddBook.this)
                .setPermissionListener(permissionListener)
                .setRationaleMessage("책 표지를 직접 입력하려면 카메라, 갤러리 접근 권한이 필요합니다.")
                .setDeniedMessage("책 표지를 직접 입력하시려면 \n[설정]-[권한]에서 권한을 승인해주세요")
                .setPermissions(Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .check();
    }

    //권한승인 끝
}