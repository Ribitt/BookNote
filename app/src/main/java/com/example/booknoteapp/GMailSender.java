package com.example.booknoteapp;


import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;


import javax.activation.DataSource;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import javax.activation.DataHandler;

public class GMailSender extends javax.mail.Authenticator{
    private String mailhost = "smtp.gmail.com";
    private String user;
    private String password;
    private Session session;
    private String emailCode;

    public GMailSender(final String user, final String password, String certNum) {
        this.user =user;
        this.password = password;
        this.emailCode = certNum;
        Properties props = new Properties();
        props.setProperty("mail.transport.protocol","smtp");
        props.setProperty("mail.host", mailhost);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.socketFactory.fallback", "false");
        props.setProperty("mail.smtp.quitwait", "false");

        session = Session.getDefaultInstance(props,new javax.mail.Authenticator(){
            protected PasswordAuthentication getPasswordAuthentication(){
                return new PasswordAuthentication(user,password);
            }
        });
    }




    public synchronized void sendMail(String subject, String body, String recipients) throws Exception{

        MimeMessage message = new MimeMessage(session);
        DataHandler handler = new DataHandler(new ByteArrayDataSource(body.getBytes(),
            "text/plain")); //본문 내용을 byte단위로 쪼개 전달하는 부분
        message.setSender(new InternetAddress(user)); //내 이메일
        message.setSubject(subject);//본문 내용 설정
        message.setDataHandler(handler);
        if(recipients.indexOf(",")>0)
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipients));
        else
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipients));
        Transport.send(message); //메시지 전달
    }

    public class ByteArrayDataSource implements DataSource { private byte[] data; private String type;
    public ByteArrayDataSource(byte[] data, String type) { super(); this.data = data; this.type = type;
    } public ByteArrayDataSource(byte[] data) { super(); this.data = data; } public void setType(String type) { this.type = type; } public String getContentType() { if (type == null) return "application/octet-stream"; else return type; } public InputStream getInputStream() throws IOException { return new ByteArrayInputStream(data); } public String getName() { return "ByteArrayDataSource"; } public OutputStream getOutputStream() throws IOException { throw new IOException("Not Supported"); } }



}
