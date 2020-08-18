package com.example.booknoteapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_read#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_read extends Fragment {


    private RecyclerView recyclerView_read;
    private Adapter_read adapter_read;
//    private ArrayList<Dictionary_book> sendArrayList = new ArrayList<>();
//    private ArrayList<Dictionary_book> getArrayList = new ArrayList<>();

    ViewGroup rootView;
    SharedPreferences pref;
    SharedPreferences.Editor editor;

    private ArrayList<Dictionary_book> readBooksList = new ArrayList<>();


    Button btn_addBook;
    CharSequence[] list_howToAddBook = {"직접 입력","책 검색하기","바코드 스캔"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = (ViewGroup)inflater.inflate(R.layout.fragment_read,container,false);


        initialize();
        allListener();


        return rootView;
    }//온 크리에이트뷰 끝


    //이니셜라이즈
    private void initialize() {

        recyclerView_read = (RecyclerView)rootView.findViewById(R.id.recycler_read);
        adapter_read = new Adapter_read(readBooksList);

        recyclerView_read.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView_read.setAdapter(adapter_read);
        btn_addBook = rootView.findViewById(R.id.btn_addBook);
        pref = this.getActivity().getSharedPreferences("book",this.getActivity().MODE_PRIVATE);
        editor = pref.edit();

    }
    //이니셜라이즈 끝끝

    private void allListener() {


        //책 추가하기 버튼 클릭 이벤트
        btn_addBook.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(rootView.getContext());

                        builder.setTitle("책 추가하기") //팝업창 제목
                                .setItems(list_howToAddBook, new DialogInterface.OnClickListener() {
                                    //선택목록이랑 클릭 이벤트 리스너를 같이 주는군
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        switch (i) {
                                            case 0:
                                                //직접 입력
                                                Intent intent = new Intent(rootView.getContext(), AddBook.class);
                                                startActivity(intent);
                                                break;
                                            case 1:
                                                //검색해서 입력
                                                Intent intent1 = new Intent(rootView.getContext(), SearchBook.class);
                                                startActivity(intent1);

                                                break;
                                            case 2:
                                                //바코드 스캔
                                                Toast.makeText(rootView.getContext(),list_howToAddBook[2]+"을 골랐습니다.",Toast.LENGTH_LONG).show();


                                                break;
                                        }

                                    }
                                });

                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                    }
                }
        );
        //책 추가하기 버튼 클릭 이벤트

    }//올 리스너 끝

    @Override
    public void onResume() {
        super.onResume();
        getPrefToArray();
    }

    private void saveArrayToPref(ArrayList<Dictionary_read> arrayList) {
        Gson gson = new Gson();
        String json = gson.toJson(arrayList);
        editor.putString("bookList",json);
        editor.apply();
    }

/////읽은 책 목록 불러오기
    private void getPrefToArray() {
        Gson gson = new Gson();
        String json = pref.getString("read","EMPTY");
        Type type = new TypeToken<ArrayList<Dictionary_book>>() {
        }.getType();
     //   ArrayList<Dictionary_book> temp;
        if(!json.equals("EMPTY")){
            readBooksList = gson.fromJson(json,type);
        }

//        for(int i =0; i<temp.size(); i++){
//            if(temp.get(i).status.equals("read")){
//                readBooksList.add(temp.get(i));
//            }
//        }

//        Log.d("들어오는지 확인", json);
//        Log.d("대체 어레이 리스트가 존재는 하는지 확인 ", String.valueOf(getArrayList.size()));
//        Log.d("대체 어레이 리스트가 존재는 하는지 확인 ", getArrayList.get(0).getTitle());
        adapter_read = new Adapter_read(readBooksList);
        recyclerView_read.setAdapter(adapter_read);
        adapter_read.notifyDataSetChanged();
    }

    ///읽은 책 목록 불러오기 끝

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Fragment_read() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_read.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_read newInstance(String param1, String param2) {
        Fragment_read fragment = new Fragment_read();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


}

//        StringBuffer sb = new StringBuffer();
//        String str = pref.getString("test","");
//        try {
//            JSONArray jsonArray = new JSONArray(str);
//            for(int i=0; i<jsonArray.length(); i++){
//                JSONObject jsonObject = jsonArray.getJSONObject(i);
//                String bookCover = jsonObject.getString("bookCover");
//                String title = jsonObject.getString("title");
//                String date = jsonObject.getString("date");
//                String rating = jsonObject.getString("rating");
//                String review = jsonObject.getString("review");
//                sb.append("bookCover:"+bookCover+",title:"+title+", date :"+date+", rating: "+rating+",review : "+review);
//
//                Dictionary_read dict = new Dictionary_read(bookCover,title,date,rating,review);
//
//            }
//
//        }catch (JSONException e){
//            e.printStackTrace();
//        }