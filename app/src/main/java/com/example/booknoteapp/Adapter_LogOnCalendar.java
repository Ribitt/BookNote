package com.example.booknoteapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adapter_LogOnCalendar  extends RecyclerView.Adapter<Adapter_LogOnCalendar.logOnCalViewHolder> {

    ArrayList<Dictionary_pageLog> mList;
    Context mContext;

    public Adapter_LogOnCalendar(ArrayList<Dictionary_pageLog> mList){
        this.mList = mList;
    }


    public class logOnCalViewHolder extends RecyclerView.ViewHolder{
        ImageView iv_bookCover;
        TextView tv_title;
        TextView tv_pages;

        public logOnCalViewHolder(@NonNull View itemView) {
            super(itemView);

            iv_bookCover = itemView.findViewById(R.id.iv_book_cover);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_pages = itemView.findViewById(R.id.tv_pages);

        }
    }

    @NonNull
    @Override
    public Adapter_LogOnCalendar.logOnCalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.item_log_on_calendar,parent,false);
        Adapter_LogOnCalendar.logOnCalViewHolder holder = new Adapter_LogOnCalendar.logOnCalViewHolder(view);


        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_LogOnCalendar.logOnCalViewHolder holder, int position) {
        Dictionary_pageLog dictionary_pageLog = mList.get(position);


        holder.iv_bookCover.setImageBitmap( dictionary_pageLog.getDictionary_book().getBookCover());
        holder.tv_pages.setText(dictionary_pageLog.getDictionary_book().getTitle());
        holder.tv_pages.setText(String.valueOf(dictionary_pageLog.getReadPageNum())+" pages");

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}


