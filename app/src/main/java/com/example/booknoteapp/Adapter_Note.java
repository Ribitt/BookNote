package com.example.booknoteapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;

public class Adapter_Note extends RecyclerView.Adapter<Adapter_Note.noteViewHolder> {

    ArrayList<Dictionary_note> mList = null;
    View.OnClickListener mEditListener;

    @NonNull
    @Override
    public noteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.item_note,parent,false);

        Adapter_Note.noteViewHolder holder = new Adapter_Note.noteViewHolder(view);

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


        holder.btn_edit.setTag(holder.getAdapterPosition());
       // holder.btn_edit.setOnClickListener(mEditListener);
        //태그 달고 외부에서 리스너를 받아 설정

        //태그를 달아라. 걍 달고 다닐 수 있고 부를 수 있는 뭐 그런건갑지?

        holder.btn_delete.setTag(holder.getAdapterPosition());
        //딜리트 버튼에 이벤트 리스너
        holder.btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = (int)view.getTag();
                mList.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position,mList.size());

            }
        });



    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class noteViewHolder extends RecyclerView.ViewHolder{

        TextView pageNum;
        TextView date;
        TextView note;
        ImageButton btn_delete;
        ImageButton btn_edit;
        TextView quote;

        public noteViewHolder(@NonNull View itemView) {
            super(itemView);

            pageNum = itemView.findViewById(R.id.tv_note_pageNum);
            date = itemView.findViewById(R.id.tv_note_date);
            note = itemView.findViewById(R.id.tv_note_userNote);
            btn_delete = itemView.findViewById(R.id.btn_note_delete);
            quote = itemView.findViewById(R.id.tv_note_quote);
            btn_edit = itemView.findViewById(R.id.btn_note_edit);
        }
    }


    public Adapter_Note(ArrayList<Dictionary_note> mList) {
        this.mList = mList;
      //  this.mEditListener = mEditListener;
    }
}
