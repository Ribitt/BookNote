package com.example.booknoteapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
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
 * Use the {@link Fragment_reading#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_reading extends Fragment {


    private RecyclerView recyclerView_reading;
    private Adapter_Reading adapter_reading;

    ViewGroup rootView;
    SharedPreferences pref;
    SharedPreferences.Editor editor;

    private ArrayList<Dictionary_book> readingBooksList = new ArrayList<>();


    Button btn_addBook;
    CharSequence[] list_howToAddBook = {"직접 입력","책 검색하기","바코드 스캔"};


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = (ViewGroup)inflater.inflate(R.layout.fragment_reading,container,false);

        initialize();
        allListener();
        return rootView;
    }


    @Override
    public void onResume() {
        super.onResume();
        getPrefToArray();
    }

    //이니셜라이즈
    private void initialize() {

        recyclerView_reading = (RecyclerView)rootView.findViewById(R.id.recycler_reading);
        adapter_reading = new Adapter_Reading(readingBooksList);

        recyclerView_reading.setLayoutManager(new GridLayoutManager(getActivity(),3));
        recyclerView_reading.setAdapter(adapter_reading);
        btn_addBook = rootView.findViewById(R.id.btn_addBook);


        String currentEmail = this.getActivity().getSharedPreferences("users", Context.MODE_PRIVATE).getString("currentUser","");
        pref = this.getActivity().getSharedPreferences(currentEmail,this.getActivity().MODE_PRIVATE);
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
                                                Intent intent = new Intent(rootView.getContext(), AddBook.class);
                                                startActivity(intent);
                                                break;
                                            case 1:
                                                Intent intent1 = new Intent(rootView.getContext(), SearchBook.class);
                                                startActivity(intent1);


                                                break;
                                            case 2:
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


    private void getPrefToArray() {
        Gson gson = new Gson();
        String json = pref.getString("reading","EMPTY");
        Type type = new TypeToken<ArrayList<Dictionary_book>>() {
        }.getType();
        if(!json.equals("EMPTY")){
            readingBooksList= gson.fromJson(json,type);
        }


        adapter_reading = new Adapter_Reading(readingBooksList);
        recyclerView_reading.setAdapter(adapter_reading);
        adapter_reading.notifyDataSetChanged();

    }




    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Fragment_reading() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_reading.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_reading newInstance(String param1, String param2) {
        Fragment_reading fragment = new Fragment_reading();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

}