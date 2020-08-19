package com.example.booknoteapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SearchView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_Note#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_Note extends Fragment {


    ViewGroup rootView;

    SearchView searchView;
    Button btn_sort_note;
    Button btn_addNote;

    RecyclerView recyclerView;

    SharedPreferences pref;
    SharedPreferences.Editor editor;
    ArrayList<Dictionary_note> noteList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = (ViewGroup)inflater.inflate(R.layout.fragment__note,container,false);

        initialize();
        allListener();

        return rootView;
    }


    private void initialize(){
        searchView = rootView.findViewById(R.id.searchView_note);
        btn_sort_note = rootView.findViewById(R.id.btn_sort_note);
        btn_addNote = rootView.findViewById(R.id.btn_addNote);

        recyclerView = rootView.findViewById(R.id.recycler_notes);

        String currentEmail = this.getActivity().getSharedPreferences("users", Context.MODE_PRIVATE).getString("currentUser","");
        pref = this.getActivity().getSharedPreferences(currentEmail,this.getActivity().MODE_PRIVATE);
        editor = pref.edit();
    }

    private void allListener() {

        //노트추가 버튼
        btn_addNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(rootView.getContext(), AddNote.class);
                startActivity(intent);

            }
        });
      //  노트추가 버튼 끝

    }





    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Fragment_Note() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_Note.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_Note newInstance(String param1, String param2) {
        Fragment_Note fragment = new Fragment_Note();
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