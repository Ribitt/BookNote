package com.example.booknoteapp;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import javax.mail.MessagingException;
import javax.mail.SendFailedException;

public class SendMail extends AppCompatActivity {
String user = "bittnuri@gmail.com";
String passwordd = "asdf0528";
String subject = "[BookNoteApp] 인증메일";




public void sendSecurityCode(Context context, String sendTo, String certNum) {
    try{
        GMailSender gMailSender = new GMailSender(user, passwordd, certNum);
        String body = "BookNoteApp에서 보낸 인증메일입니다. \n " +
                "인증번호 : "+certNum +
                "\n 어플에서 인증번호를 입력해주세요";
        gMailSender.sendMail(subject, body, sendTo);
        Toast.makeText(context, "이메일을 성공적으로 보냈습니다", Toast.LENGTH_SHORT).show();
    }catch (SendFailedException e){
        Toast.makeText(context, "이메일 형식이 잘못되었습니다", Toast.LENGTH_SHORT).show();
    }catch (MessagingException e){
        Toast.makeText(context, "인터넷 연결을 확인해주세요", Toast.LENGTH_SHORT).show();
        System.out.println("//////////////////////////////////////////////////////////////////////////");
        e.printStackTrace();
        System.out.println("//////////////////////////////////////////////////////////////////////////");
    }catch (Exception e){
        e.printStackTrace();
    }
}

}
