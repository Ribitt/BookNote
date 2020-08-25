package com.example.booknoteapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import java.util.ArrayList;

public class Adapter_LogOnCalendar  extends RecyclerView.Adapter<Adapter_LogOnCalendar.logOnCalViewHolder> {

    ArrayList<Dictionary_pageLog> mList;
    Context mContext;

    SharedPreferences.Editor userEditor;

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

            //북 커버 그냥 클릭 리스너
            iv_bookCover.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(mContext,BookLog_Notes.class);

                    Dictionary_book bookNow = mList.get(getAdapterPosition()).getDictionary_book();
                    saveBookDictToPref(bookNow);

                    mContext.startActivity(intent);

                }
            });

            //북 커버 클릭 리스너 끝

        }
    }

    //지금 어레이를 쉐어드에 저장하기
    private void saveBookDictToPref(Dictionary_book bookNow) {
        Gson gson = new Gson();
        String json = gson.toJson(bookNow);
        userEditor.putString("bookNow",json);
        userEditor.apply();
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
        holder.tv_title.setText(dictionary_pageLog.getDictionary_book().getTitle());
        holder.tv_pages.setText(String.valueOf(dictionary_pageLog.getReadPageNum())+" pages");

        //쉐어드를 쓰고 싶다면 온크리에이트 뷰홀더에~~
        String currentEmail = mContext.getSharedPreferences("users", Context.MODE_PRIVATE).getString("currentUser","");
        SharedPreferences pref = mContext.getSharedPreferences(currentEmail,mContext.MODE_PRIVATE);
        userEditor = pref.edit();

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}


