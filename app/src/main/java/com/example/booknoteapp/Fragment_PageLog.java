package com.example.booknoteapp;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_PageLog#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_PageLog extends Fragment {


    Button btn_addLog;

    ////다이얼로그에 필요한 녀석들
    java.util.Calendar calendar = java.util.Calendar.getInstance();
    Dictionary_pageLog dic;
    private Dialog_insertPage dialog;
    /////다이얼로그 끝

    RecyclerView recyclerView;
    Adapter_PageLog adapter_pageLog;

    ViewGroup rootView;

    TextView tv_bookmark;
    SeekBar seekBar;


    String bookNow ="";
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    ArrayList<Dictionary_pageLog> mList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = (ViewGroup)inflater.inflate(R.layout.fragment__page_log,container,false);

        initialize();
        allListener();

        return rootView;
    }

    private void initialize() {
        String currentEmail = this.getActivity().getSharedPreferences("users", Context.MODE_PRIVATE).getString("currentUser","");
        pref = this.getActivity().getSharedPreferences(currentEmail,this.getActivity().MODE_PRIVATE);
        editor = pref.edit();

        bookNow = pref.getString("bookNow","");

        recyclerView = rootView.findViewById(R.id.recycler_pageLog);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        btn_addLog = rootView.findViewById(R.id.btn_addLog);
        tv_bookmark = rootView.findViewById(R.id.tv_bookLog_bookmark);
        seekBar = rootView.findViewById(R.id.seekBar);
    }

    private void allListener() {

        //로그 추가버튼
        btn_addLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog = new Dialog_insertPage(rootView.getContext(), dateListener, positive, negative);
                dialog.show();

            }
        });
        //로그 추가버튼

    }

    ////////////////////////////페이지 추가 다이얼로그 생성자에 해당하는 커스텀 클릭 리스너들
    private View.OnClickListener dateListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            showDialog();
        }
    };

    private void showDialog(){

        DatePickerDialog pickerDialog = new DatePickerDialog(rootView.getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int date) {
                dialog.setDate(String.format("%2d.%02d.%02d",year,month+1,date));
            }
        }, calendar.get(java.util.Calendar.YEAR), calendar.get(java.util.Calendar.MONTH), calendar.get(java.util.Calendar.DAY_OF_MONTH));
        pickerDialog.getDatePicker().setMaxDate(new Date().getTime());
        pickerDialog.show();
    }


    private View.OnClickListener positive = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            if(!dialog.getStartP().equals("") && !dialog.getEndP().equals("")){//둘 다 값이 있을 때만 일하자 !!

                //ArrayList에 넣어준다
                dic = new Dictionary_pageLog(bookNow,dialog.getDate(),dialog.getStartP(),dialog.getEndP());
                mList.add(0,dic);
                //새로 추가한 내용이 맨 위로 가도록 한다. 그냥 dict넣어주면 맨 밑으로 간다.
                adapter_pageLog = new Adapter_PageLog(mList);
                recyclerView.setAdapter(adapter_pageLog);

                tv_bookmark.setText(dialog.getEndP());
                seekBar.setProgress(Integer.parseInt(dialog.getEndP()),true);
                saveArrayToPref();


                //  adapter_pageLog.notifyItemInserted(0); //0번 위치에 새로운 애가 왔다 잘 봐라
                //맨 밑으로 추가하면 그냥 notifyDataSetChanged호출


//                //유저가 입력한 마지막 페이지 값을 프로그레스 바에도 적용한다. 입력값은 문자열이므로 정수로 변환한다.
//                userReadPageNum = Integer.parseInt(dialog.getEndP());
//                progressBar_bookLog.setProgress(userReadPageNum);
//
//                //유저가 입력한 값을 프로그레스 바 하단에 있는 숫자에도 반영해준다
//                tv_progress_readPageNum.setText(dialog.getEndP());
//
//                //리스트에도 넣어준다
//                PreferenceManager.setString(getApplicationContext(),"pageNum",dialog.getEndP());


            }else{//하나라도 없으면 아무 일 없이 넘어가기
                Toast.makeText(rootView.getContext(),"시작 페이지와 마지막 페이지를 입력해주세요", Toast.LENGTH_LONG).show();
            }

            dialog.dismiss();
        }
    };

    private View.OnClickListener negative = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            dialog.dismiss();
        }
    };
////////////////////////////페이지 추가 다이얼로그 생성자에 해당하는 커스텀 클릭 리스너들 끝


    private void saveArrayToPref() {

        Gson gson = new Gson();
        String json = gson.toJson(mList);
        editor.putString("pageLog",json);
        editor.commit();
    }



    private void getArrayFromPref(){

        Gson gson = new Gson();
        String json = pref.getString("pageLog","EMPTY");
        mList.clear();
        //클리어를 해줘야 반복해서 내용이 저장되지 않는다.


        if(!json.equals("EMPTY")){
            Type type = new TypeToken<ArrayList<Dictionary_pageLog>>() {
            }.getType();

            ArrayList<Dictionary_pageLog> allList = gson.fromJson(json,type);


            for(int i=0; i<allList.size();i++){
                if(allList.get(i).getTitle().equals(bookNow)){
                    mList.add(allList.get(i));
                }

            }
        }

        adapter_pageLog = new Adapter_PageLog(mList);
        recyclerView.setAdapter(adapter_pageLog);


    }


    @Override
    public void onResume() {
        super.onResume();
        getArrayFromPref();

    }

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Fragment_PageLog() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_PageLog.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_PageLog newInstance(String param1, String param2) {
        Fragment_PageLog fragment = new Fragment_PageLog();
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