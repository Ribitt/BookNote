package com.example.booknoteapp;


import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.se.omapi.Session;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Properties;
import java.util.Random;
import java.util.regex.Pattern;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SignUp extends AppCompatActivity {


    String certNum="";
    String NICKNAMES="nicknames";
    String NICKNAME = "nickname";
    String allNicknames = "";

    EditText email;
    EditText et_certNum;
    EditText password;
    EditText passwordCheck;
    EditText et_nickname;

    Button sendConfirmBtn;
    Button checkConfirmBtn;
    Button checkNicknameBtn;
    Button doneBtn;

    TextView warning_confirmNum;
    TextView warning_password;
    TextView warning_nickname;
    TextView warning_existedEmail;


    Boolean isEmailNew = false;
    Boolean isCertSame = false;
    Boolean isPasswordSame = false;
    Boolean isNicknameNew = false;

    SharedPreferences pref;
    SharedPreferences.Editor editor;

    SharedPreferences usersPref;
    SharedPreferences.Editor usersEditor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
        .permitDiskReads()
        .permitDiskWrites()
        .permitNetwork().build());

        Toolbar toolbar = (Toolbar)findViewById(R.id.app_toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.green));
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("회원가입");

        initialize();
        allClickListener();






    }//온크리에이트 끝

    protected void initialize() {

        //쉐어드 프리퍼런스 불러오기
        pref = getSharedPreferences("mine", Activity.MODE_PRIVATE);
        editor = pref.edit();

        //유저 관리용 프리퍼런스 불러오기
        usersPref = getSharedPreferences("users", Activity.MODE_PRIVATE);
        usersEditor = usersPref.edit();

        usersEditor.putString("bittnuri@gmail.com","1234");
        usersEditor.commit();
        //잘 걸러지는지 확인을 위해 임의로 내용을 집어넣는다.

        //쉐어드 프리퍼런스

        //뷰 불러오기

        email = findViewById(R.id.et_signUp_email);
        et_certNum = findViewById(R.id.et_signUp_confirmNum);
        password = findViewById(R.id.et_signUp_password);
        passwordCheck =findViewById(R.id.et_signUp_passwordCheck);
        et_nickname = findViewById(R.id.et_signIp_nickname);

        doneBtn = findViewById(R.id.btn_signUp_done);
        sendConfirmBtn = findViewById(R.id.btn_signUp_sendConfirmNum);
        checkConfirmBtn = findViewById(R.id.btn_signUp_checkConfirmNum);
        checkNicknameBtn = findViewById(R.id.btn_sugnUp_nicknameCheck);

        warning_password = findViewById(R.id.tv_signUp_passwordNotSame);
        warning_confirmNum = findViewById(R.id.tv_signUp_numNotSame);
        warning_nickname = findViewById(R.id.tv_signUp_nickNameAlreadyUsed);
        warning_existedEmail = findViewById(R.id.tv_signUp_emailAlreadyExist);

        //뷰 불러오기 끝

    }//이니셜라이저 끝

    protected void allClickListener() {


        //이미 가입한 이메일 주소인지 확인
        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //유저가 입력한 이메일 주소
                String newEmail = email.getText().toString();
                isEmailNew = false;
                //이미 인증을 끝내놓고 이메일을 바꾸는 경우를 대비해서 텍스트가 바뀔 때마다 인증을 다시 받아야 하도록 설정한다.

                if(usersPref.getString(newEmail,"").equals("")){
                    //유저의 이메일 주소를 키값으로 저장된 데이터가 없는 경우 새로 저장할 수 있다
                    //이미 존재하는 이메일이라는 경고창은 없어진다
                    warning_existedEmail.setVisibility(View.GONE);
                    //새로운 이메일주소가 맞다고 플래그를 설정한다.
                    isEmailNew =true;

                }else{
                    //이미 존재하는 이메일이라는 경고창이 뜬다
                    warning_existedEmail.setVisibility(View.VISIBLE);
                    isEmailNew = false;

                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        //이미 가입한 이메일 주소인지 확인 끝




        //인증번호 보내기 버튼
        sendConfirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //쉐어드에 이메일 주소를 저장한다.
              if(isEmailNew){
                  //중복이 아니기 때문에 다음 단계를 진행한다.

                  //형식에 맞는 이메일 주소인지 확인한다
                 if(checkEmail(email.getText().toString())){
                    // gmailSend(email.getText().toString(),certNum);
                     Toast.makeText(SignUp.this, "인증 번호를 전송했습니다", Toast.LENGTH_SHORT).show();
                     makeCertNum();
                     et_certNum.setHint(certNum);

                 }else{
                     Toast.makeText(SignUp.this, "올바른 이메일 주소를 입력해주세요", Toast.LENGTH_SHORT).show();
                 }
                 //형식에 맞는 이메일 주소인지 확인 끝

              }else{
                  //중복되는 이메일이기 때문에 다음 단계를 진행할 수 없다.
                  Toast.makeText(SignUp.this, "이메일 주소를 확인하세요", Toast.LENGTH_SHORT).show();
              }
            }
        });

        //인증번호 보내기 버튼 끝


        //인증번호 확인 버튼
        checkConfirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               // Log.d("비밀번호 일치 확인용", et_certNum)
                if(et_certNum.getText().toString().equals(certNum)){
                    //에딧텍스트랑 스트링 비교하는 뻘짓 하지 말자 ^^!

                    //인증번호 일치
                    Toast.makeText(SignUp.this, "인증 완료", Toast.LENGTH_SHORT).show();
                    isCertSame = true;

                }else{
                    //인증번호 일치하지 않음
                    Toast.makeText(SignUp.this, "인증번호가 일치하지 않습니다", Toast.LENGTH_SHORT).show();
                    isCertSame = false;
                }
            }
        });
        //인증번호 확인 버튼 끝



        //비밀번호 일치여부 확인하기
        passwordCheck.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(password.getText().toString().equals(passwordCheck.getText().toString())) {
                    warning_password.setVisibility(View.GONE);
                    isPasswordSame = true;
                }else {
                    warning_password.setVisibility(View.VISIBLE);
                    isPasswordSame = false;
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        //비밀번호 일치 확인 끝

        //닉네임 중복 확인
        checkNicknameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //닉네임은 모두 nicknames 라는 키값 안에 #으로 들어간다.
                allNicknames = usersPref.getString(NICKNAMES,"");
                String[] array = allNicknames.split("#");

                for(int j =0; j<array.length; j++){
                    if(array[j].equals(et_nickname.getText().toString())){
                        Toast.makeText(SignUp.this, "이미 사용 중인 닉네임입니다", Toast.LENGTH_SHORT).show();
                        isNicknameNew = false;
                        break;
                    }
                    isNicknameNew = true;
                }

                if(isNicknameNew) {
                    Toast.makeText(SignUp.this, "사용할 수 있는 닉네임입니다", Toast.LENGTH_SHORT).show();
                }


            }
        });
        //닉네임 중복 확인 끝

        //회원가입 버튼
        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(isCertSame){
                    //인증이 완료 되었는지?

                    if(isPasswordSame){
                        //비밀번호 일치하는지?

                        if(isNicknameNew){
                            //닉네임 중복 없는지?

                            //유저의 이메일 주소를 키값으로 비밀번호를 스트링으로 저장해준다.
                            usersEditor.putString(email.getText().toString(),passwordCheck.getText().toString());
                            //유저의 닉네임을 새로 추가해 준다.
                            usersEditor.putString(NICKNAMES, allNicknames+"#"+et_nickname.getText().toString());
                            usersEditor.commit();

                            //새로 유저가 회원가입을 하게 되면 유저 이메일이 패키지명인 새로운 쉐어드 프리퍼런스를 만들고 그 안에 닉네임 값을 저장한다.
                            //다른 액티비티에서 불러올 수 있게 하기 위함이다.
                            SharedPreferences newPref = getSharedPreferences(email.getText().toString(),Activity.MODE_PRIVATE);
                            SharedPreferences.Editor newEditor = newPref.edit();
                            Toast.makeText(SignUp.this, allNicknames, Toast.LENGTH_SHORT).show();
                            newEditor.putString(NICKNAME,et_nickname.getText().toString());
                            newEditor.apply();



                            Toast.makeText(SignUp.this, "회원가입이 완료되었습니다.", Toast.LENGTH_SHORT).show();
                            finish();
                        }else{
                            Toast.makeText(SignUp.this, "닉네임을 확인하세요.", Toast.LENGTH_SHORT).show();
                        }


                    }else{
                        Toast.makeText(SignUp.this, "비밀번호를 확인하세요.", Toast.LENGTH_SHORT).show();
                    }

                }else{
                    Toast.makeText(SignUp.this, "이메일 인증을 완료해주세요", Toast.LENGTH_SHORT).show();
                }


            }
        });
        //회원가입 버튼 끝



    }//올 클릭 리스너 끝

    //랜덤한 인증번호 만들기 메소드
    private void makeCertNum() {

       // long seed = System.currentTimeMillis();
        Random rand = new Random();
        //시드를 변경하고 싶다면 rand.setSeed(System.currentTimeMillils())를 해주면 된다
        //따로 seed를 설정해주지 않으면 디폴트 시드값이 현재 시간이다. 음
        //nextInt()<- 이 속에 있는 숫자보다 작은 무작위 숫자값이 나온다. 나는 6자리가 필요하니까 999999라고 하면...5자리도 나오고 6자리도 나오네.

        int len = 6; //몇 자리 수를 만들고 싶은지 정한다
        certNum=""; //기존에 있던 인증 번호는 비운다

        for(int i=0;i<len;i++){
            //원하는 길이만큼 돌린다
            String ran = String.valueOf(rand.nextInt(10));
            //0~9까지의 정수를 만들어서
            certNum += ran;
            //차례로 더해서 만들어준다
        }

    //  Toast.makeText(SignUp.this, certNum, Toast.LENGTH_SHORT).show();


    }
    //랜덤한 인증번호 만들기 메소드 끝

    //이메일 보내기
    public static void gmailSend(String userEmail, String certNum){
        final  String user = "bittnuri@gmail.com";
        final String password = "asdf0528"; //내 계정과 비밀번호

        Properties prop = new Properties();
        prop.put("mail.smtp.host","smtp.gmail.com");
        prop.put("mail.smtp.port",465);
        prop.put("mail.smtp.auth","true");
        prop.put("mail.smtp.ssl.enable","true");
        prop.put("mail.smtp.ssl.trust","smtp.gmail.com");


       javax.mail.Session session = javax.mail.Session.getDefaultInstance(prop,new javax.mail.Authenticator(){
            protected PasswordAuthentication getPasswordAuthentication() {

                return new PasswordAuthentication(user,password);
            }
        });

       try{
           MimeMessage message = new MimeMessage(session);
           message.setFrom(new InternetAddress(user));

           message.addRecipients(Message.RecipientType.TO, String.valueOf(new InternetAddress(userEmail)));

           message.setSubject("[BookNoteApp] 인증번호");

           message.setText("BookNoteApp을 사용하기 위해 인증번호를 입력해주세요./n" +
                   "인증번호 : "+certNum);

           Transport.send(message);
           Log.d("이메일이 잘 갔는지 보겠습니다", "잘 간듯");

       }catch (AddressException e){
           e.printStackTrace();
       }catch (MessagingException e){
           e.printStackTrace();
       }


    }

    //이메일 보내기끝

    //이메일 형식 체커
    public static final Pattern EMAIL_ADDRESS_PATTERN = Pattern.compile(
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                    "\\@" +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(" +
                    "\\." +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                    ")+"
    );

    private boolean checkEmail(String email) {
        return EMAIL_ADDRESS_PATTERN.matcher(email).matches();
    }

    //이메일 형식 체커 끝


    @Override
    protected void onResume() {
        super.onResume();

    }
}