package com.example.booknoteapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;


import java.util.ArrayList;

public class Adapter_SearchBook extends androidx.recyclerview.widget.RecyclerView.Adapter<Adapter_SearchBook.searchBookViewHolder> {

    ArrayList<Dictionary_book> mList = null;
    Context mContext;

    String from = "";


    public Adapter_SearchBook(ArrayList<Dictionary_book> mList, String from) {
        this.mList = mList;
        this.from = from;

    }


    public class searchBookViewHolder extends androidx.recyclerview.widget.RecyclerView.ViewHolder{

        ImageView bookCover;
        TextView title;
        TextView author;
        TextView publisher;
        Button btn_addBook;


        public searchBookViewHolder(@NonNull View view) {
            super(view);

            bookCover = view.findViewById(R.id.imageV_search_bookCover);
            title = view.findViewById(R.id.tv_search_title);
            author =view.findViewById(R.id.tv_search_author);
            publisher =view.findViewById(R.id.tv_search_publisher);
            btn_addBook =view.findViewById(R.id.btn_search_addBook);

            if(from.equals("add")){
                ////책 추가 버튼 클릭 리스너
                btn_addBook.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Dictionary_book dict = mList.get(getAdapterPosition());
                        Intent intent = new Intent(mContext,AddBook.class);
                        intent.putExtra("searchedBook",dict);
                        mContext.startActivity(intent);
                        ((Activity)mContext).finish();

                    }
                });
                ////책 추가 버튼 클릭 리스너
            }else if(from.equals("essay")){
                ////책 추가 버튼 클릭 리스너
                btn_addBook.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Dictionary_book dict = mList.get(getAdapterPosition());
                        Intent intent = new Intent(mContext,AddEssay.class);
                        intent.putExtra("from","add");
                        intent.putExtra("bookDict",dict);
                        mContext.startActivity(intent);
                       // ((Activity)mContext).finish();

                    }
                });
                ////책 추가 버튼 클릭 리스너
            }


        }///뷰홀더 생성자 여기까지
    }

    @NonNull
    @Override
    public searchBookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        mContext = parent.getContext();
        //리사이클러 뷰가 들어갈 페어런트 컨텍스트

        LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        // 그 컨텍스트에서 레이아웃 인플레이터를 쓸거고

        View view = inflater.inflate(R.layout.item_search_book,parent,false);
        //내가 열심히 만든 게 들어갈 뷰 자리는 만들어둔 리사이클러뷰 자리고, 그거는 페어런트 컨텍스트 내부에 있어

        Adapter_SearchBook.searchBookViewHolder viewHolder = new Adapter_SearchBook.searchBookViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull searchBookViewHolder holder, int position) {

        Dictionary_book book = mList.get(position);

        if(book.bookCover==null){
            //북커버가 아무것도 없으면 암것도 하지 않는다..
        }else{
            holder.bookCover.setImageBitmap(book.getBookCover());
        }

        holder.title.setText(book.getTitle());
        holder.author.setText(book.getAuthor());
        holder.publisher.setText(book.getPublisher());

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


}
