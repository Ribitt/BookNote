package com.example.booknoteapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class EditBook extends AppCompatActivity {
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

    boolean read =false;
    boolean reading = false;
    boolean interested = false;

    ////이미지/카메라 받아오기
    private static final int PICK_FROM_ALBUM =185;
    private static final int PICK_FROM_CAMERA = 195;
    private File tempFile;
    ////이미지/카메라 받아오기

    Button btn_addBook_addBookCover;
    EditText et_title;


    Button btn_addBook_status_read;
    Button btn_addBook_status_reading;
    Button btn_addBook_status_interested;

    LinearLayout layout_addBook_read_date;
    LinearLayout layout_addBook_reading_date;
    LinearLayout layout_addBook_reading_readPage;


    EditText et_addBook_read_ALineReview;
    RatingBar rating_addBook_read;
    TextView tv_addBook_read_endDate;

    TextView tv_addBook_reading_lastDate;

    EditText et_addBook_interested_memo;


    Button btn_addBook_cancel;
    Button btn_addBook_done;


    private final int CAMERA_REQUEST = 19;
    final CharSequence[] cameraOrGallery = {"사진 찍기","갤러리에서 불러오기"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_book);


        //수정 가능한 뷰 초기화

        layout_addBook_read_date =findViewById(R.id.layout_addBook_read_date);
        layout_addBook_reading_date =findViewById(R.id.layout_addBook_reading_date);
        layout_addBook_reading_readPage =findViewById(R.id.layout_addBook_reading_readPage);

        btn_addBook_addBookCover = findViewById(R.id.imageV_addBook_addBookCover);
        btn_addBook_status_read = findViewById(R.id.btn_addBook_status_read);
        btn_addBook_status_reading = findViewById(R.id.btn_addBook_status_reading);
        btn_addBook_status_interested = findViewById(R.id.btn_addBook_status_interested);

        btn_addBook_done = findViewById(R.id.btn_addBook_done);
        btn_addBook_cancel = findViewById(R.id.btn_addBook_cancel);

        //입력창 초기화
        et_title = findViewById(R.id.et_addBook_title);

        rating_addBook_read = findViewById(R.id.rating_addBook_read);
        et_addBook_read_ALineReview = findViewById(R.id. et_addBook_read_ALineReview);
        tv_addBook_read_endDate = findViewById(R.id.tv_addBook_read_endDate);

        tv_addBook_reading_lastDate =findViewById(R.id.tv_addBook_reading_lastDate);

        et_addBook_interested_memo = findViewById(R.id.et_addBook_interested_memo);



        //뷰 초기화 끝


        //읽은 책 골랐을 때
        btn_addBook_status_read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                read = true;
                reading = false;
                interested = false;

                btn_addBook_status_read.setBackground(getDrawable(R.drawable.underline_green_selected));
                btn_addBook_status_reading.setBackground(getDrawable(R.drawable.underline_green));
                btn_addBook_status_interested.setBackground(getDrawable(R.drawable.underline_green));

                btn_addBook_status_read.setTextColor(getColor(R.color.myBlack));
                btn_addBook_status_reading.setTextColor(getColor(R.color.border));
                btn_addBook_status_interested.setTextColor(getColor(R.color.border));

                et_addBook_interested_memo.setVisibility(View.INVISIBLE);
                layout_addBook_reading_date.setVisibility(View.INVISIBLE);
                layout_addBook_reading_readPage.setVisibility(View.INVISIBLE);
                layout_addBook_read_date.setVisibility(View.VISIBLE);
                et_addBook_read_ALineReview.setVisibility(View.VISIBLE);
                rating_addBook_read.setVisibility(View.VISIBLE);

            }
        });

        //읽는 중인 책일 때

        btn_addBook_status_reading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                read = false;
                reading = true;
                interested = false;

                btn_addBook_status_read.setBackground(getDrawable(R.drawable.underline_green));
                btn_addBook_status_reading.setBackground(getDrawable(R.drawable.underline_green_selected));
                btn_addBook_status_interested.setBackground(getDrawable(R.drawable.underline_green));

                btn_addBook_status_read.setTextColor(getColor(R.color.border));
                btn_addBook_status_reading.setTextColor(getColor(R.color.myBlack));
                btn_addBook_status_interested.setTextColor(getColor(R.color.border));


                et_addBook_interested_memo.setVisibility(View.INVISIBLE);
                layout_addBook_reading_date.setVisibility(View.VISIBLE);
                layout_addBook_reading_readPage.setVisibility(View.VISIBLE);
                layout_addBook_read_date.setVisibility(View.INVISIBLE);
                et_addBook_read_ALineReview.setVisibility(View.INVISIBLE);
                rating_addBook_read.setVisibility(View.INVISIBLE);

            }
        });

        //관심 책일 때
        btn_addBook_status_interested.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                read = false;
                reading = false;
                interested = true;

                btn_addBook_status_read.setBackground(getDrawable(R.drawable.underline_green));
                btn_addBook_status_reading.setBackground(getDrawable(R.drawable.underline_green));
                btn_addBook_status_interested.setBackground(getDrawable(R.drawable.underline_green_selected));

                btn_addBook_status_read.setTextColor(getColor(R.color.border));
                btn_addBook_status_reading.setTextColor(getColor(R.color.border));
                btn_addBook_status_interested.setTextColor(getColor(R.color.myBlack));


                et_addBook_interested_memo.setVisibility(View.VISIBLE);
                layout_addBook_reading_date.setVisibility(View.INVISIBLE);
                layout_addBook_reading_readPage.setVisibility(View.INVISIBLE);
                layout_addBook_read_date.setVisibility(View.INVISIBLE);
                et_addBook_read_ALineReview.setVisibility(View.INVISIBLE);
                rating_addBook_read.setVisibility(View.INVISIBLE);

            }
        });

        /////////////////////////////////////////////////읽은 책 클릭 리스너
        //다 읽은 날짜 일단 지금 날짜로 해두기
        tv_addBook_read_endDate.setHint(time);
        tv_addBook_read_endDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatePickerDialog dialog = new DatePickerDialog(EditBook.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int date) {

                        tv_addBook_read_endDate.setText(year+"."+(int)(month+1)+"."+date);
                    }
                }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE));

                dialog.getDatePicker().setMaxDate(new Date().getTime());    //입력한 날짜 이후로 클릭 안되게 옵션
                dialog.show();


            }
        });


        /////////////////////////////////////////////////읽은 책 클릭 리스너 끝


        /////////////////////////////////////////////////읽는중 책 클릭 리스너
        tv_addBook_reading_lastDate.setHint(time);
        tv_addBook_reading_lastDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatePickerDialog dialog = new DatePickerDialog(EditBook.this, new DatePickerDialog.OnDateSetListener() {
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

        /////////////////////////////////////////////////읽는중 책 클릭 리스너 끝


        /////////////////////////////////////////////////관심 책 클릭 리스너

        /////////////////////////////////////////////////관심 책 클릭 리스너 끝

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

                if(et_title.getText().toString().equals("")){
                    Toast.makeText(EditBook.this, "책 제목을 입력해주세요", Toast.LENGTH_LONG).show();


                }else if(btn_addBook_addBookCover.getBackground().toString()=="android.graphics.drawable.ColorDrawable@e37edef"){
                    //이미지가 없는 경우에도 하고 싶은데 어떻게 잡아내야 할지 모르겠다.
                    Toast.makeText(EditBook.this,"지금 이미지 경로"+btn_addBook_addBookCover.getBackground(),Toast.LENGTH_LONG).show();
                }
                else{

                    Intent intent;
                    intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.putExtra("title", et_title.getText().toString());
                    //이미지 백그라운드에 있는 드로어블 이미지를 비트맵으로 가져오기
                    Bitmap readCover = ((BitmapDrawable)btn_addBook_addBookCover.getBackground()).getBitmap();
                    //비트맵 리소스 지정 코드
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    //스트림이 무슨 뜻인지 모르겠네
                    readCover.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                    byte[] byteArray = stream.toByteArray();
                    intent.putExtra("image", byteArray);
                    setResult(RESULT_OK,intent);
                    finish();
                }

                //startActivity(intent);



//                if(read){
//                intent = new Intent(getApplicationContext(), Drawer_read.class);
//                intent.putExtra("title", et_title.getText().toString());
//                intent.putExtra("rating", rating_addBook_read.getRating());
//                intent.putExtra("review", et_addBook_read_ALineReview.getText().toString());
//                intent.putExtra("endDate",tv_addBook_read_endDate.getText().toString());
//                    Bitmap sendBitmap = BitmapFactory.decodeResource(getResources(),R.drawable.book_jieun);
//                //이미지 소스를 비트맵 소스로 바꾸기
//                Bitmap readCover = ((BitmapDrawable)btn_addBook_addBookCover.getBackground()).getBitmap();
//                //백그라운드에 있는 드로어블 이미지를 비트맵으로 가져오기
//                //비트맵 리소스 지정 코드
//                ByteArrayOutputStream stream = new ByteArrayOutputStream();
//                //스트림이 무슨 뜻인지 모르겠네
//                readCover.compress(Bitmap.CompressFormat.JPEG, 100, stream);
//                byte[] byteArray = stream.toByteArray();
//                intent.putExtra("image", byteArray);
//                    startActivity(intent);
//
//                }else if(reading){
//                    intent = new Intent(getApplicationContext(), MainActivity.class);
//                    intent.putExtra("title", et_title.getText().toString());
//                    startActivity(intent);
//                }
//                else if(interested){
//                    intent = new Intent(getApplicationContext(), Drawer_interested.class);
//                    intent.putExtra("title", et_title.getText().toString());
//                    startActivity(intent);
//                }



//                String title="";
//                if(!et_title.equals("")){ //뭔가 입력한 내용이 있다면
//                    title = et_title.getText().toString();
//                }
//
//                Intent resultIntent = new Intent();
//                resultIntent.putExtra("title", title);
//
//                //Bitmap sendBitmap = BitmapFactory.decodeResource(getResources(),R.drawable.book_jieun);
//                //이미지 소스를 비트맵 소스로 바꾸기
//                Bitmap sendBitmap = ((BitmapDrawable)btn_addBook_addBookCover.getBackground()).getBitmap();
//                //백그라운드에 있는 드로어블 이미지를 비트맵으로 가져오기
//                //비트맵 리소스 지정 코드
//                ByteArrayOutputStream stream = new ByteArrayOutputStream();
//                //스트림이 무슨 뜻인지 모르겠네
//                sendBitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
//                byte[] byteArray = stream.toByteArray();
//                resultIntent.putExtra("image", byteArray);
//                setResult(RESULT_OK, resultIntent);
//
//                finish();
            }
        });



    }




    @Override
    protected void onPostResume() {
        super.onPostResume();

        //이미지 추가 버튼 눌렀을 때
        btn_addBook_addBookCover.setOnClickListener(
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
                                                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                                if(takePictureIntent.resolveActivity(getPackageManager())!=null){//카메라 기능이 있는지, 사용가능한지 확인

                                                    try{
                                                        tempFile = createImageFile();

                                                    }catch(IOException e){
                                                        Toast.makeText(EditBook.this, "이미치 처리 오류 발생. 다시 시도해주세요",Toast.LENGTH_LONG).show();
                                                        finish();
                                                        e.printStackTrace();
                                                    }

                                                    if(tempFile!=null){
                                                        //카메라에서 찍은 이미지가 저장될 주소
//                                                        Uri photoUri = Uri.fromFile(tempFile);
//                                                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,photoUri);
                                                        startActivityForResult(takePictureIntent,PICK_FROM_CAMERA);
                                                    }



                                                }else{
                                                    Toast.makeText(EditBook.this,"사용할 수 있는 카메라가 없습니다",Toast.LENGTH_LONG).show();
                                                }

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

    private File createImageFile() throws IOException{
        //이미지 파일 이름 지정
        //지금 시간을 가져다가 마이 이미지_시간_이렇게 저장되게 함
        String timeStamp = new SimpleDateFormat("HHmmss").format(new Date());
        String imageFileName = "myImage_"+timeStamp+"_";

        //이미지가 저장될 폴더 이름 (remi)
        File strorageDir = new File(Environment.getExternalStorageDirectory()+"/remi/");
        if(!strorageDir.exists())strorageDir.mkdirs();

        //빈 파일 생성
        File image = File.createTempFile(imageFileName, "jpg", strorageDir);

        //파일에 이름이랑 양식이랑 넣어서 반환
        return image;
    }


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

                        Toast.makeText(this,"삭제성공", Toast.LENGTH_LONG).show();
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

            }finally {

            }
            setImage();

        }else if(requestCode==PICK_FROM_CAMERA){

            if(data.hasExtra("data")){
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                if(bitmap!=null) {

                    Drawable bookCover = new BitmapDrawable(bitmap);

                    btn_addBook_addBookCover.setText("");
                    btn_addBook_addBookCover.setBackground(bookCover);
                }
            }
            // setImage();

        }
    }

    //갤러리에서 받아온 이미지 넣기
    private void setImage() {

        BitmapFactory.Options options = new BitmapFactory.Options();
        Bitmap originalBm = BitmapFactory.decodeFile(tempFile.getAbsolutePath(),options);
        Drawable bookCover = new BitmapDrawable(originalBm);

        btn_addBook_addBookCover.setText("");
        btn_addBook_addBookCover.setBackground(bookCover);
    }


}