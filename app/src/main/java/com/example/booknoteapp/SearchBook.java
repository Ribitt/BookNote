package com.example.booknoteapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class SearchBook extends AppCompatActivity {

//    private static Handler mHandler;

    ArrayList<Dictionary_book> bookList = new ArrayList<>();
    RecyclerView recyclerView =null;
    Adapter_SearchBook adapter_searchBook = null;

    android.widget.SearchView searchView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_book);


        initialize();
        allListener();

    }



    private void initialize() {

        searchView = findViewById(R.id.search_searchBook);


        //////툴바 적용하기
        Toolbar toolbar = (Toolbar)findViewById(R.id.app_toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.green));
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("도서 검색");

        //////////////리사이클러뷰
        recyclerView = findViewById(R.id.recycler_bookSearch);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        //////////////리사이클러뷰


    }////이니셜라이저 끝


    private void allListener() {

        ///검색창 검색 클릭 이벤트
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(final String query) {
              //  Toast.makeText(getApplicationContext(), "검색버튼 누름 : "+query, Toast.LENGTH_SHORT).show();

                //별도의 스레드를 돌려서 결과를 가져와야 한다.
                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        Bundle bun = new Bundle();
                        bun.putSerializable("list",getNaverBookSearch(query));
                        Message msg = handler.obtainMessage();
                        msg.setData(bun);
                        handler.sendMessage(msg);

                    }
                }).start();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
        ///////////검색창 검색 클릭 끝



    }//올리스너 끝




    private String removeHTMLTag(String string){
        Spanned mText = Html.fromHtml(string);
        String text = mText.toString();
        return text;
    }

    private Bitmap urlToBitmap(String string){
        URL imgUrl;
        HttpURLConnection connection = null;
        InputStream is;
        Bitmap bookCover =null;

        try{
            imgUrl = new URL(string);
            connection = (HttpURLConnection) imgUrl.openConnection();
            connection.setDoInput(true); //url로 input받는 flag 허용
            connection.connect();
            is = connection.getInputStream();
            bookCover = BitmapFactory.decodeStream(is);
        }catch (Exception e){
            e.printStackTrace();
            return null;

        }finally {
            if(connection!=null){
                connection.disconnect();
            }
            return bookCover;
        }
    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            Bundle bun = msg.getData();
            bookList = (ArrayList<Dictionary_book>)bun.getSerializable("list");
            adapter_searchBook = new Adapter_SearchBook(bookList);
            recyclerView.setAdapter(adapter_searchBook);

        }
    };

    private ArrayList<Dictionary_book> getNaverBookSearch(String query) {
        String clientID = "5Jz3oA5urXwB2YOWdz67";
        String clientSecret = "Wsl94OqRUN";
        ArrayList<Dictionary_book> list = new ArrayList<>();

        try{
            String text = URLEncoder.encode(query, "UTF-8");
            String apiURL = "https://openapi.naver.com/v1/search/book.xml?query=" + text;


            Dictionary_book dictionary_book = null;

            URL url = new URL(apiURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("X-Naver-Client-Id", clientID);
            conn.setRequestProperty("X-Naver-Client-Secret", clientSecret);

            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser xpp = factory.newPullParser();
            String tag;
            xpp.setInput(new InputStreamReader(conn.getInputStream(), "UTF-8"));

            xpp.next();
            int eventType = xpp.getEventType();

            while(eventType!=XmlPullParser.END_DOCUMENT){
                switch (eventType){
                    case XmlPullParser.END_DOCUMENT://검색 결과 끝
                        list = new ArrayList<>();
                        break;

                    case XmlPullParser.START_TAG:{
                        tag = xpp.getName();
                        switch (tag){
                            case "item":
                                dictionary_book = new Dictionary_book("reading","title","author");
                                break;
                            case "title":
                                if(dictionary_book!=null)
                                    dictionary_book.setTitle(removeHTMLTag(xpp.nextText()));
                                break;
                            case "author":
                                if(dictionary_book!=null)
                                    dictionary_book.setAuthor(removeHTMLTag(xpp.nextText()));
                                break;
                            case "publisher":
                                if(dictionary_book!=null)
                                    dictionary_book.setPublisher(removeHTMLTag(xpp.nextText()));
                                break;

                            case "image":
                                if(dictionary_book!=null)
                                    dictionary_book.setBookCover(urlToBitmap(xpp.nextText()));
                        }
                        break;
                    }///스타트 태그인 경우 끝

                    case XmlPullParser.END_TAG:{
                        tag = xpp.getName();
                        if(tag.equals("item")){

                            list.add(dictionary_book);
                            dictionary_book=null;
                        }
                    }
                }
                eventType = xpp.next();

            }



        }catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            System.out.println("이건가//////////////////////////////////Malformed");
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            System.out.println("이건가//////////////////////////////////Unsupported");
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            System.out.println("이건가//////////////////////////////////IOE");
            Toast.makeText(this, "인터넷 연결을 확인하세요", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            System.out.println("이건가//////////////////////////////////XmlPullParser");
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    return list;

    }

}


////////////////////
//시계예제

//     온 크리에이트 안에 선언
//    NewRunnable nr = new NewRunnable();
//    Thread t = new Thread(nr);
//        t.start();

//  이니셜라이저에 선언
//   mHandler = new Handler(){
//@Override
//public void handleMessage(@NonNull Message msg) {
//        Calendar cal = Calendar.getInstance();
//        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
//        String strTime = sdf.format(cal.getTime());
//        tv.setText(strTime);
//        }
//
//        };

//아무데나 외부에 선언
//class NewRunnable implements Runnable{
//
//    @Override
//    public void run() {
//        while (true){
//            try{
//                Thread.sleep(1000);
//            }catch(Exception e){
//                e.printStackTrace();
//            }
//            mHandler.sendEmptyMessage(0);
//        }
//    }
//}
////////////////////////