package com.example.booknoteapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class Adapter_Interested extends androidx.recyclerview.widget.RecyclerView.Adapter<Adapter_Interested.interestedViewHolder> {

    ArrayList<Dictionary_book> myList =null;



    public class interestedViewHolder extends androidx.recyclerview.widget.RecyclerView.ViewHolder {

        ImageButton bookCover;
        TextView title;
        TextView memo;

        public interestedViewHolder(@NonNull View itemView) {
            super(itemView);
            bookCover = itemView.findViewById(R.id.btn_interestedD_bookCover);
            title = itemView.findViewById(R.id.tv_interestedD_bookTitle);
            memo = itemView.findViewById(R.id.tv_interestedD_memo);



        }
    }



    public Adapter_Interested(ArrayList<Dictionary_book> mList) {
        this.myList = mList;
    }

    @NonNull
    @Override
    public Adapter_Interested.interestedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.item_interested,parent,false);
        Adapter_Interested.interestedViewHolder holder = new Adapter_Interested.interestedViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_Interested.interestedViewHolder holder, int position) {

        Dictionary_book dic = myList.get(position);

        holder.bookCover.setImageBitmap(dic.getBookCover());
        holder.title.setText(dic.getTitle());
        holder.memo.setText(dic.getMemo());

    }

    @Override
    public int getItemCount() {
        return myList.size();
    }


}
