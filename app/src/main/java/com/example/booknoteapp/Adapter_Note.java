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
import androidx.recyclerview.widget.RecyclerView;


import com.google.gson.Gson;

import java.util.ArrayList;

public class Adapter_Note extends RecyclerView.Adapter<Adapter_Note.noteViewHolder> {

    ArrayList<Dictionary_note> mList; //해당 책 노트 리스트
    ArrayList<Dictionary_note> wholeExceptNowList; //전체에서 지금것만 뺀 리스트
    ArrayList<Dictionary_note> wholeList = new ArrayList<>(); //전체 노트 리스트

    SharedPreferences userPref;
    SharedPreferences.Editor userEditor;

    int position;
    Context mContext;

    public Adapter_Note(ArrayList<Dictionary_note> wholeExceptNowList, ArrayList<Dictionary_note> mList) {
        this.wholeExceptNowList = wholeExceptNowList;
        this.mList = mList;

    }

    @NonNull
    @Override
    public noteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.item_note,parent,false);

        Adapter_Note.noteViewHolder holder = new Adapter_Note.noteViewHolder(view);

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
        CharSequence[] list_edit_or_delete = {"노트 수정하기","삭제하기"};

        public noteViewHolder(@NonNull View itemView) {
            super(itemView);



            pageNum = itemView.findViewById(R.id.tv_note_pageNum);
            date = itemView.findViewById(R.id.tv_note_date);
            note = itemView.findViewById(R.id.tv_note_userNote);
            edit_or_delete = itemView.findViewById(R.id.edit_or_delete);
            quote = itemView.findViewById(R.id.tv_note_quote);

            ////수정삭제 클릭 리스너
            edit_or_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);

                    builder
                            .setItems(list_edit_or_delete, new DialogInterface.OnClickListener() {
                                //선택목록이랑 클릭 이벤트 리스너를 같이 주는군
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    switch (i) {
                                        case 0:
                                            //수정하기
                                            Intent intent = new Intent(mContext,EditNote.class);
                                            Dictionary_note selectedBook = mList.get(getAdapterPosition());
                                            intent.putExtra("selectedNote",selectedBook);
                                            intent.putExtra("position",getAdapterPosition());
                                            intent.putExtra("mList",mList);
                                            intent.putExtra("wholeExceptNowList",wholeExceptNowList);
                                            mContext.startActivity(intent);
                                            break;
                                        case 1:
                                            //삭제하기
                                            position = getAdapterPosition();
                                            alert();

                                            break;
                                    }
                                }
                            });

                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();

                }
            });

        }//뷰홀더 생성자 끝
    }//뷰홀더 끝

    //정말 삭제하시겠습니까?
    private void alert() {
        AlertDialog.Builder reallyGoOutAlert = new AlertDialog.Builder(mContext);
        reallyGoOutAlert.setTitle("정말 삭제하시겠습니까?")
                .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                })
                .setPositiveButton("삭제하기", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        mList.remove(position);
                        saveArrayToPref();
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position,mList.size());



                    }
                }).show();
    }
    //정말 삭제하시겠습니까? 끝

    //수정된 노트 어레이와 나머지 노트 어레이 합쳐서 저장
    private void saveArrayToPref() {

        wholeList.addAll(wholeExceptNowList);
        wholeList.addAll(mList);
        Gson gson = new Gson();
        String json = gson.toJson(wholeList);
        userEditor.putString("note",json);
        userEditor.apply();
    }
    //어레이 저장 끝

}
