package com.example.booknoteapp;
import android.app.DatePickerDialog;
import android.content.Context;
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

import java.util.ArrayList;
import java.util.Date;

import javax.crypto.ShortBufferException;

public class Adapter_PageLog extends RecyclerView.Adapter<Adapter_PageLog.pageLogViewHolder>{

    ArrayList<Dictionary_pageLog> mList =null;
    java.util.Calendar calendar = java.util.Calendar.getInstance();
    private Dialog_insertPage dialog;
    private Context mContext;
    int position;


    private View.OnClickListener dateListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            showDialog();
        }
    };

    private void showDialog(){

        DatePickerDialog pickerDialog = new DatePickerDialog(mContext, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                dialog.setDate(year+"."+(int)(month+1)+"."+dayOfMonth);
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
                Dictionary_pageLog dic = new Dictionary_pageLog(dialog.getDate(),dialog.getStartP(),dialog.getEndP());
                mList.set(position,dic);
                //수정한 내용이 해당 자리에 반영되도록 한다.
                notifyItemChanged(position);


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
                        Dictionary_pageLog temp = mList.get(position);
                        dialog = new Dialog_insertPage(mContext, dateListener, positive, negative);
                        dialog.show();
                        dialog.startP.setText(temp.getStartP());
                        dialog.endP.setText(temp.getEndP());
                        dialog.date.setText(temp.getDate());
                      ///  itemView.setBackgroundColor(ContextCompat.getColor(mContext,R.color.almostWhite));

                        break;
                    case 1002:
                        //삭제를 고른 경우
                        System.out.println("지금 리스트 길이/////////////////////////////////////////// "+mList.size());
                        mList.remove(getAdapterPosition());
                        notifyItemRemoved(getAdapterPosition());
                        notifyDataSetChanged();
                      //  itemView.setBackgroundColor(ContextCompat.getColor(mContext,R.color.almostWhite));

                        break;
                }
                return true;
            }
        };
    }

    public Adapter_PageLog(ArrayList<Dictionary_pageLog> mList ) {

        this.mList = mList;
    }

    @NonNull
    @Override
    public pageLogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        mContext=parent.getContext();
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_page_log, parent, false);

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
