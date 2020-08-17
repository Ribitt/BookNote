package com.example.booknoteapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;


import java.util.ArrayList;

public class Adapter_SearchBook extends androidx.recyclerview.widget.RecyclerView.Adapter<Adapter_SearchBook.searchBookViewHolder> {

    ArrayList<Dictionary_book> mList = null;
    Context mContext;


    public Adapter_SearchBook(ArrayList<Dictionary_book> mList) {
        this.mList = mList;

    }


    public class searchBookViewHolder extends androidx.recyclerview.widget.RecyclerView.ViewHolder{

        ImageView bookCover;
        TextView title;
        TextView author;
        TextView publisher;


        public searchBookViewHolder(@NonNull View view) {
            super(view);

            bookCover = view.findViewById(R.id.imageV_search_bookCover);
            title = view.findViewById(R.id.tv_search_title);
            author =view.findViewById(R.id.tv_search_author);
            publisher =view.findViewById(R.id.tv_search_publisher);
        }
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
