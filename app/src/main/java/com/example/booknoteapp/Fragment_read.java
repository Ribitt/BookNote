package com.example.booknoteapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
    private ArrayList<Dictionary_read> sendArrayList = new ArrayList<>();
    private ArrayList<Dictionary_read> getArrayList = new ArrayList<>();
    private Dictionary_read dic;
    ViewGroup rootView;
    SharedPreferences pref;
    SharedPreferences.Editor editor;

    Button btn_addBook;
    CharSequence[] list_howToAddBook = {"직접 입력","책 검색하기","바코드 스캔"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = (ViewGroup)inflater.inflate(R.layout.fragment_read,container,false);

        recyclerView_read = (RecyclerView)rootView.findViewById(R.id.recycler_read);
        adapter_read = new Adapter_read(getArrayList);


        recyclerView_read.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView_read.setAdapter(adapter_read);
        btn_addBook = rootView.findViewById(R.id.btn_addBook);
        pref = this.getActivity().getSharedPreferences("book",this.getActivity().MODE_PRIVATE);
        editor = pref.edit();

        allListener();





        return rootView;
    }//온 크리에이트뷰 끝

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
                                                Intent intent = new Intent(rootView.getContext(), AddBook_toReading.class);
                                                startActivity(intent);
                                                break;
                                            case 1:
                                                Toast.makeText(rootView.getContext(),list_howToAddBook[1]+"를 골랐습니다.",Toast.LENGTH_LONG).show();
                                                for(int j=0; j<5; j++){
                                                    dic = new Dictionary_read("김지은입니다",
                                                            "2020.8.4",(float) 4.5, "한줄 평이 들어가는 자리");
                                                    sendArrayList.add(dic);
                                                }
                                                Toast.makeText(rootView.getContext(),String.valueOf(sendArrayList.size()),Toast.LENGTH_LONG).show();
                                                saveArrayToPref(sendArrayList);
                                                //일단 무작위로 5개의 리사이클러뷰 데이터를 만들어서 어레이 리스트에 집어 넣는다



//                                                JSONArray jsonArray = null;
//
//                                                try {
//                                                    jsonArray = new JSONArray();
//                                                    for(int k=0 ; k<sendArrayList.size(); k++){
//                                                        JSONObject jsonObject = new JSONObject(); //배열 내에 들어갈 json
//                                                        jsonObject.put("bookCover",sendArrayList.get(i).bookCover);
//                                                        jsonObject.put("title", sendArrayList.get(i).title);
//                                                        jsonObject.put("date",sendArrayList.get(i).endDate);
//                                                        jsonObject.put("rating",sendArrayList.get(i).rating);
//                                                        jsonObject.put("review",sendArrayList.get(i).review);
//                                                        jsonArray.put(jsonObject);
//
//                                                    }
//
//                                                }catch (JSONException e){
//                                                    e.printStackTrace();
//                                                }
//                                                Log.d("JSON Test",jsonArray.toString());
//                                                editor.putString("test",jsonArray.toString());
//                                                editor.commit();
                                                break;
                                            case 2:
                                                Toast.makeText(rootView.getContext(),list_howToAddBook[2]+"을 골랐습니다.",Toast.LENGTH_LONG).show();

//                                                for(int j=0; j<5; j++){
//                                                    dic = new Dictionary_read("김지은입니다",
//                                                            "2020.8.4",(float) 4.5, "자칭 진보면서 민주주의를 지향한다는 이들의 더럽고 허무한 이면");
//                                                    getArrayList.add(dic);
//                                                }
//
//                                                adapter_read.notifyDataSetChanged();


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


    private void saveArrayToPref(ArrayList<Dictionary_read> arrayList) {
        Gson gson = new Gson();
        String json = gson.toJson(arrayList);
        editor.putString("bookList",json);
        editor.apply();
    }

    private void getPrefToArray() {
        Gson gson = new Gson();
        String json = pref.getString("bookList","EMPTY");
        Type type = new TypeToken<ArrayList<Dictionary_read>>() {
        }.getType();
        getArrayList = gson.fromJson(json,type);
        Log.d("들어오는지 확인", json);
        Log.d("대체 어레이 리스트가 존재는 하는지 확인 ", String.valueOf(getArrayList.size()));
        Log.d("대체 어레이 리스트가 존재는 하는지 확인 ", getArrayList.get(0).getTitle());
        adapter_read = new Adapter_read(getArrayList);
        recyclerView_read.setAdapter(adapter_read);
        adapter_read.notifyDataSetChanged();


    }

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

    @Override
    public void onResume() {
        super.onResume();
        getPrefToArray();
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