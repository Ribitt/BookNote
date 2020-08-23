package com.example.booknoteapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import java.util.ArrayList;

public class Adapter_NoteForHome extends RecyclerView.Adapter<Adapter_NoteForHome.noteViewHolder> {

    ArrayList<Dictionary_note> mList; //해당 책 노트 리스트

    SharedPreferences userPref;
    SharedPreferences.Editor userEditor;

    int position;
    Context mContext;

    public Adapter_NoteForHome(ArrayList<Dictionary_note> wholeList) {
        this.mList = wholeList;
    }

    @NonNull
    @Override
    public noteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.item_note,parent,false);

        Adapter_NoteForHome.noteViewHolder holder = new Adapter_NoteForHome.noteViewHolder(view);

        String currentEmail = mContext.getSharedPreferences("users", Context.MODE_PRIVATE).getString("currentUser","");
        userPref = mContext.getSharedPreferences(currentEmail,mContext.MODE_PRIVATE);
        userEditor = userPref.edit();

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull noteViewHolder holder, int position) {
        Dictionary_note dic = mList.get(position);

        holder.pageNum.setText(dic.getPageNum());
        holder.date.setText(dic.getDate());
        holder.note.setText(dic.getNote());
        holder.quote.setText(dic.getQuote());
        holder.note.setTextColor(dic.getColor());
        holder.title.setText(dic.getTitle());


    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class noteViewHolder extends RecyclerView.ViewHolder{

        TextView pageNum;
        TextView date;
        TextView note;
        TextView quote;
        ImageView edit_or_delete;
        TextView title;
        CardView cardView;



        public noteViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.tv_title);
            title.setVisibility(View.VISIBLE);
            pageNum = itemView.findViewById(R.id.tv_note_pageNum);
            date = itemView.findViewById(R.id.tv_note_date);
            note = itemView.findViewById(R.id.tv_note_userNote);
            edit_or_delete = itemView.findViewById(R.id.edit_or_delete);
            edit_or_delete.setVisibility(View.GONE);
            quote = itemView.findViewById(R.id.tv_note_quote);
            cardView = itemView.findViewById(R.id.cardView_note);

            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });



        }//뷰홀더 생성자 끝
    }//뷰홀더 끝



}
