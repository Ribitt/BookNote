package com.example.booknoteapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;


public class Adapter_read extends RecyclerView.Adapter<Adapter_read.readViewHolder> {


    public Adapter_read(ArrayList<Dictionary_read> mList) {
        this.mList = mList;
    }
        ArrayList<Dictionary_read> mList =null;



    public class readViewHolder extends RecyclerView.ViewHolder{

        ImageButton bookCover;
        TextView title;
        RatingBar ratingBar;
        TextView ALineReview;
        TextView endDate;

        public readViewHolder(@NonNull View itemView) {
            super(itemView);
            bookCover = itemView.findViewById(R.id.btn_readD_bookCover);
            title = itemView.findViewById(R.id.tv_readD_bookTitle);
            ALineReview = itemView.findViewById(R.id.tv_readD_ALineReview);
            ratingBar = itemView.findViewById(R.id.rating_readD);
            endDate = itemView.findViewById(R.id.tv_readD_endDate);

        }
    }

    @NonNull
    @Override
    public readViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_read, parent, false);
        Adapter_read.readViewHolder holder = new Adapter_read.readViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull readViewHolder holder, int position) {

        Dictionary_read dic = mList.get(position);

      //  holder.bookCover.setImageDrawable(dic.getBookCover());
        holder.title.setText(dic.getTitle());
        holder.ratingBar.setRating(dic.getRating());
        holder.ALineReview.setText(dic.getReview());
        holder.endDate.setText(dic.getEndDate());

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }





}
