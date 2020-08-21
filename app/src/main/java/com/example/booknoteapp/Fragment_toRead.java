package com.example.booknoteapp;

import android.app.AlertDialog;
import android.content.Context;
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
 * Use the {@link Fragment_toRead#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_toRead extends Fragment {

    private RecyclerView recyclerView_toRead;
    private Adapter_Interested adapter_toRead;

    ViewGroup rootView;
    SharedPreferences pref;
    SharedPreferences.Editor editor;

    private ArrayList<Dictionary_book> toReadBooksList = new ArrayList<>();


    Button btn_addBook;
    CharSequence[] list_howToAddBook = {"직접 입력","책 검색하기","바코드 스캔"};


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = (ViewGroup)inflater.inflate(R.layout.fragment_to_read,container,false);

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

        recyclerView_toRead = (RecyclerView)rootView.findViewById(R.id.recycler_interested);
        adapter_toRead = new Adapter_Interested(toReadBooksList);

        recyclerView_toRead.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView_toRead.setAdapter(adapter_toRead);
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
                                                Intent intent1 = new Intent(rootView.getContext(), Search.class);
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
        String json = pref.getString("interested","EMPTY");
        Type type = new TypeToken<ArrayList<Dictionary_book>>() {
        }.getType();
        if(!json.equals("EMPTY")){
            toReadBooksList = gson.fromJson(json,type);
        }
        //매번 새롭게 안해주면 창 나갔다가 들어올때마다 추가된다.

        adapter_toRead = new Adapter_Interested(toReadBooksList);
        recyclerView_toRead.setAdapter(adapter_toRead);
        adapter_toRead.notifyDataSetChanged();

    }


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Fragment_toRead() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_toRead.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_toRead newInstance(String param1, String param2) {
        Fragment_toRead fragment = new Fragment_toRead();
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