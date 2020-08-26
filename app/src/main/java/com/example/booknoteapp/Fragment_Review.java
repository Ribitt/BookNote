package com.example.booknoteapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_Review#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_Review extends Fragment {

    View rootView;

    SharedPreferences userPref;
    SharedPreferences.Editor userEditor;

    RatingBar ratingBar;
    TextView tv_review;

    Dictionary_book bookNow;

    ArrayList<Dictionary_book> bookList = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = (ViewGroup)inflater.inflate(R.layout.fragment__review,container,false);

        initialize();

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        getBookFromPref();
        setBookReview();
        allListener();
    }

    private void initialize(){

        String currentEmail = this.getActivity().getSharedPreferences("users", Context.MODE_PRIVATE).getString("currentUser","");
        userPref = this.getActivity().getSharedPreferences(currentEmail,this.getActivity().MODE_PRIVATE);
        userEditor = userPref.edit();

        ratingBar = rootView.findViewById(R.id.rating_bar);
        tv_review = rootView.findViewById(R.id.tv_review);
    }

    private void allListener(){

//        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
//            @Override
//            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
//                String status = bookNow.getStatus();
//                changeSavedRating(status, v);
//            }
//        });

    }

    private void changeSavedRating(String status, float rating){

        Gson gson = new Gson();
        String json = userPref.getString(status,"EMPTY");
        Type type = new TypeToken<ArrayList<Dictionary_book>>() {
        }.getType();
        if(!json.equals("EMPTY")){
            bookList= gson.fromJson(json,type);
        }//지금 책 상태에 해당하는 책 리스트 전체를 받아와서

        for(int i=0; i<bookList.size(); i++){
            if(bookList.get(i).getTitle().equals(bookNow.getTitle())){
                bookList.get(i).setRating(rating);
            }
        }//지금 책을 찾아서 별점을 바꿔준다

        json = gson.toJson(bookList);
        userEditor.putString(status,json);
        userEditor.commit();
        //전체 리스트를 다시 저장해줌
    }





    private void getBookFromPref(){
        Gson gson = new Gson();
        String json = userPref.getString("bookNow","EMPTY");

        Type type = new TypeToken<Dictionary_book>() {
        }.getType();
        if(!json.equals("EMPTY")){
            bookNow = gson.fromJson(json,type);
        }
    }

    private void setBookReview(){
        ratingBar.setRating(bookNow.getRating());
        tv_review.setText(bookNow.getReview());
    }





    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Fragment_Review() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_Review.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_Review newInstance(String param1, String param2) {
        Fragment_Review fragment = new Fragment_Review();
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