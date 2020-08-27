package com.example.booknoteapp;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Date;

import javax.crypto.ShortBufferException;

import static android.content.Context.MODE_PRIVATE;

public class Adapter_PageLog extends RecyclerView.Adapter<Adapter_PageLog.pageLogViewHolder>{

    ArrayList<Dictionary_pageLog> mList =null;
    java.util.Calendar calendar = java.util.Calendar.getInstance();
    private Dialog_insertPage dialog;
    private Context mContext;
    int position;


    Dictionary_pageLog editLog;
    ArrayList<Dictionary_pageLog> wholeList;
    SharedPreferences userPref;
    SharedPreferences.Editor userEditor;



    private View.OnClickListener dateListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            showDialog();
        }
    };

    private void showDialog(){

        DatePickerDialog pickerDialog = new DatePickerDialog(mContext, new DatePickerDialog.OnDateSetListener() {
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
            //수정하기 확인 버튼 눌렀을 때

            if(!dialog.getStartP().equals("") && !dialog.getEndP().equals("")){//둘 다 값이 있을 때만 일하자 !!



                editLog.setDate(dialog.getDate());
                editLog.setEndP(dialog.getEndP());
                editLog.setStartP(dialog.getStartP());
                mList.set(position,editLog);
                //수정한 내용이 해당 자리에 반영되도록 한다.
                notifyItemChanged(position);
                wholeList.set(editLog.getPositionInWholeList(),editLog);
                Log.d("지금 바뀐 거가 잘 들어는 가나 ", wholeList.get(editLog.getPositionInWholeList()).toString());
                saveArrayToPref(wholeList,userEditor);


            }else{//하나라도 없으면 아무 일 없이 넘어가기

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


    public class pageLogViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener{

        TextView date;
        TextView startP;
        TextView endP;

        public pageLogViewHolder(@NonNull View itemView) {
            super(itemView);
            date = (TextView)itemView.findViewById(R.id.tv_pageLog_date);
            startP = (TextView)itemView.findViewById(R.id.tv_pageLog_user_startPage);
            endP = (TextView)itemView.findViewById(R.id.tv_pageLog_user_endPage);
            itemView.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {

           // itemView.setBackgroundColor(ContextCompat.getColor(mContext,R.color.yellowGreen));

            MenuItem Edit = contextMenu.add(Menu.NONE, 1001, 1,"수정");
            MenuItem Delete = contextMenu.add(Menu.NONE, 1002, 2, "삭제");
            Edit.setOnMenuItemClickListener(onEditMenu);
            Delete.setOnMenuItemClickListener(onEditMenu);



        }




        private final MenuItem.OnMenuItemClickListener onEditMenu = new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {

                    case 1001:
                        //수정을 고른 경우
                        position = getAdapterPosition();
                        editLog = mList.get(position);
                        dialog = new Dialog_insertPage(mContext, dateListener, positive, negative);
                        dialog.show();
                        dialog.startP.setText(editLog.getStartP());
                        dialog.endP.setText(editLog.getEndP());
                        dialog.date.setText(editLog.getDate());
//                        //어레이리스트를 쉐어드에 저장하는 게 없네
//                        wholeList.set(editLog.getPositionInWholeList(),editLog);
//                        saveArrayToPref(wholeList,userEditor);

                      ///  itemView.setBackgroundColor(ContextCompat.getColor(mContext,R.color.almostWhite));

                        break;
                    case 1002:
                        //삭제를 고른 경우
                        position = getAdapterPosition();
                        alert();

                      //  itemView.setBackgroundColor(ContextCompat.getColor(mContext,R.color.almostWhite));

                        break;
                }
                return true;
            }
        };
    }


    //정말 삭제하시겠습니까?
    private void alert() {
        AlertDialog.Builder reallyGoOutAlert = new AlertDialog.Builder(mContext);
        reallyGoOutAlert.setTitle("정말 삭제하시겠습니까?")
                .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                })
                .setPositiveButton("삭제하기", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        wholeList.remove(mList.get(position).getPositionInWholeList());
                        //전체 리스트에서 먼저 지운다
                        mList.remove(position);
                        notifyItemRemoved(position);
                        notifyDataSetChanged();
                        saveArrayToPref(wholeList,userEditor);




                    }
                }).show();
    }
    //정말 삭제하시겠습니까? 끝


    //지금 어레이를 쉐어드에 저장하기
    private void saveArrayToPref(ArrayList<Dictionary_pageLog> arrayList, SharedPreferences.Editor editor) {
        Gson gson = new Gson();
        String json = gson.toJson(arrayList);
        editor.putString("pageLog",json);
        editor.apply();
    }

    public Adapter_PageLog(ArrayList<Dictionary_pageLog> mList, ArrayList<Dictionary_pageLog> wholeList) {

        this.mList = mList;
        this.wholeList=wholeList;
    }

    @NonNull
    @Override
    public pageLogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        mContext=parent.getContext();
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_page_log, parent, false);


        String userEmail = parent.getContext().getSharedPreferences("users", MODE_PRIVATE).getString("currentUser","");
        userPref= parent.getContext().getSharedPreferences(userEmail,MODE_PRIVATE);
        //저장되어있는 닉네임을 불러온다
        userEditor = userPref.edit();

        Adapter_PageLog.pageLogViewHolder holder = new Adapter_PageLog.pageLogViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull pageLogViewHolder holder, int position) {

        Dictionary_pageLog dic = mList.get(position);

        holder.date.setText(dic.getDate());
        holder.startP.setText(dic.getStartP());
        holder.endP.setText(dic.getEndP());

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }




}
