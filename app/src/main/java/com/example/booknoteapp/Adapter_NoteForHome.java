package com.example.booknoteapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
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

    public Adapter_NoteForHome(ArrayList<Dictionary_note> recentTenNotesList) {
        this.mList = recentTenNotesList;
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
        // 최신 노트를 클릭하면 해당 책 상세 페이지로 넘어가게 하기 위해 프레프 필요. 터치 시 바로 해당 책이 bookNow에 저장되어야 하기 때문.

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
        holder.title.setText(dic.dictionary_book.getTitle());



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
        TextView tv_see_more;


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
            tv_see_more = itemView.findViewById(R.id.tv_see_more);


            //뷰 클릭하면 책 상세로 넘어가기
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("클릭되는지 여기까지 오는지 확인","됨?");

                    Intent intent = new Intent(mContext,BookLog_Notes.class);

                    Dictionary_book bookNow = mList.get(getAdapterPosition()).dictionary_book;
                    saveBookDictToPref(bookNow);

                    mContext.startActivity(intent);

                }
            });

            //북 커버 클릭 리스너 끝

            //내용 길어지면 더보기 접기
            final int maxLine = note.getMaxLines();

            note.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    Log.d("지금 에세이 내용 줄 수는?", String.valueOf(note.getLineCount()));
                    if(note.getLineCount()>=maxLine){
                        tv_see_more.setVisibility(View.VISIBLE);
                    }else{
                        tv_see_more.setVisibility(View.GONE);
                    }
                }
            });

            tv_see_more.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(tv_see_more.getText().toString().equals("더보기")) {
                        note.setMaxLines(Integer.MAX_VALUE);
                        tv_see_more.setText("접기");
                    }else{
                        note.setMaxLines(maxLine);
                        tv_see_more.setText("더보기");
                    }
                }
            });
            //내용 길어지면 더보기 접기 끝

            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });




        }//뷰홀더 생성자 끝
    }//뷰홀더 끝

    //지금 어레이를 쉐어드에 저장하기
    private void saveBookDictToPref(Dictionary_book bookNow) {
        Gson gson = new Gson();
        String json = gson.toJson(bookNow);
        userEditor.putString("bookNow",json);
        userEditor.apply();
    }

}
