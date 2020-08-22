package com.example.booknoteapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.zip.Inflater;

import static android.content.Context.MODE_PRIVATE;

public class Adapter_Essay extends androidx.recyclerview.widget.RecyclerView.Adapter<Adapter_Essay.essayViewHolder> {



    ArrayList<Dictionary_Essay> mList =null;
    Context mContext;

    SharedPreferences currentUserPref;
    SharedPreferences pref;
    String nickname;


    Adapter_Essay(ArrayList<Dictionary_Essay> list) {
        this.mList = list;


    }

    public class essayViewHolder extends androidx.recyclerview.widget.RecyclerView.ViewHolder{

        TextView nickname;
        ImageView btn_edit_or_delete;
        TextView bookTitle;
        TextView bookAuthor;
        ImageView bookCover;
        TextView essayTitle;
        TextView essayContent;
        TextView date;
        TextView likeNum;
        ImageView like;
        TextView tv_isOpen;



        public essayViewHolder(@NonNull View view) {
            super(view);

            nickname = view.findViewById(R.id.tv_nickname);
            btn_edit_or_delete = view.findViewById(R.id.iv_more);
            bookTitle = view.findViewById(R.id.tv_book_title);
            bookAuthor=view.findViewById(R.id.tv_book_author);
            bookCover = view.findViewById(R.id.iv_book_cover);
            essayTitle  =view.findViewById(R.id.tv_essayTitle);
            essayContent =view.findViewById(R.id.tv_essayContent);
            date =view.findViewById(R.id.tv_date);
            likeNum =view.findViewById(R.id.tv_likeNum);
            like =view.findViewById(R.id.iv_like_btn);
            tv_isOpen = view.findViewById(R.id.tv_isOpen);
        }
    }


    @NonNull
    @Override
    public essayViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        mContext = parent.getContext();
        LayoutInflater inflater =  (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.item_essay,parent,false);
        Adapter_Essay.essayViewHolder holder = new Adapter_Essay.essayViewHolder(view);

       pref = parent.getContext().getSharedPreferences("users", MODE_PRIVATE);
        String userEmail = pref.getString("currentUser","");
        currentUserPref= parent.getContext().getSharedPreferences(userEmail,MODE_PRIVATE);
        //저장되어있는 닉네임을 불러온다
        nickname = currentUserPref.getString("nickname","");

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull essayViewHolder holder, int position) {

        Dictionary_Essay dictionary_essay = mList.get(position);

        holder.nickname.setText(nickname);

        holder.bookCover.setImageBitmap(dictionary_essay.getBookCover());
        holder.bookAuthor.setText(dictionary_essay.getBookAuthor());
        holder.bookTitle.setText(dictionary_essay.getBookTitle());

        holder.likeNum.setText(dictionary_essay.getLikeNum());
        holder.essayContent.setText(dictionary_essay.getEssayContent());
        holder.essayTitle.setText(dictionary_essay.getEssayTitle());

        holder.date.setText(dictionary_essay.getDate());

        if(dictionary_essay.isOpen){
            holder.tv_isOpen.setText("공개 에세이");
        }else{
            holder.tv_isOpen.setText("비공개 에세이");
        }




    }

    @Override
    public int getItemCount() {
        return mList.size();
    }




}
