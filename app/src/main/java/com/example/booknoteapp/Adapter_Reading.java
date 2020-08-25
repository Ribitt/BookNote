package com.example.booknoteapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.gson.Gson;

import java.util.ArrayList;

public class Adapter_Reading extends androidx.recyclerview.widget.RecyclerView.Adapter<Adapter_Reading.readingViewHolder> {

    //1. 어레이 리스트 정하고 그 애는 일단 비어있다. 이 리스트는 딕셔너리 객체들이 들어간다.
    ArrayList<Dictionary_book> mList = null;

    SharedPreferences userPref;
    SharedPreferences.Editor editor;

    View.OnClickListener editListener;
    Context mContext;
    int position=0;
    String from;

    CharSequence[] list_edit_or_delete = {"책 정보 수정하기","책 삭제하기"};

    //2. 뷰홀더를 상속하는 뷰홀더를 만든다.
    public class readingViewHolder extends androidx.recyclerview.widget.RecyclerView.ViewHolder {



        //3. 뷰 홀더 내부에 뷰들을 선언한다.
        ImageButton bookCover;
        TextView bookTitle;
        ImageButton editBtn;
        ImageButton deleteBtn;

        public readingViewHolder(@NonNull View view) {
            super(view);


            //4. 뷰 홀더 생성자 내부에 뷰 객체 참조값을 넣어 초기화한다.
            bookCover = (ImageButton) itemView.findViewById(R.id.btn_readingD_bookcover);
            bookTitle = (TextView)itemView.findViewById(R.id.tv_readingD_bookTitle);




            if(from.equals("drawer")){

                bookCover.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {


                        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);

                        builder
                                .setItems(list_edit_or_delete, new DialogInterface.OnClickListener() {
                                    //선택목록이랑 클릭 이벤트 리스너를 같이 주는군
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        switch (i) {
                                            case 0:
                                                //수정하기
                                                Intent intent = new Intent(mContext,EditBook.class);
                                                Dictionary_book selectedBook = mList.get(getAdapterPosition());
                                                intent.putExtra("selectedBook",selectedBook);
                                                intent.putExtra("position",getAdapterPosition());
                                                mContext.startActivity(intent);
                                                break;
                                            case 1:
                                                //삭제하기
                                                position =getAdapterPosition();
                                                alert();


                                                break;
                                        }
                                    }
                                });

                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();

                        return true;
                    }
                });


                //북 커버 그냥 클릭 리스너
                bookCover.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent intent = new Intent(mContext,BookLog_Notes.class);

                        Dictionary_book bookNow = mList.get(getAdapterPosition());
                        saveBookDictToPref(bookNow);

                        mContext.startActivity(intent);

                    }
                });

                //북 커버 클릭 리스너 끝

            }else if(from.equals("essay")){

                bookCover.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(mContext, AddEssay.class);
                        Dictionary_book selectedBook = mList.get(getAdapterPosition());
                        intent.putExtra("bookDict",selectedBook);
                        intent.putExtra("from", "add");
                        mContext.startActivity(intent);
                    }
                });

            }



        }

    }


    //지금 책을 쉐어드에 저장하기
    private void saveBookDictToPref(Dictionary_book bookNow) {
        Gson gson = new Gson();
        String json = gson.toJson(bookNow);
        editor.putString("bookNow",json);
        editor.apply();
    }

    
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
                        notifyItemRemoved(position);
                        notifyDataSetChanged();
                        saveBookArrayToPref(mList);


                    }
                }).show();
    }
    //정말 삭제하시겠습니까? 끝

    //지금 어레이를 쉐어드에 저장하기
    private void saveBookArrayToPref(ArrayList<Dictionary_book> arrayList) {
        Gson gson = new Gson();
        String json = gson.toJson(arrayList);
        editor.putString("reading",json);
        editor.apply();
    }


   //5. 어댑터의 생성자 어레이 리스트를 받는다.

    public Adapter_Reading(ArrayList<Dictionary_book> mList, String from) {
        this.mList = mList;
        this.from = from;

        //, View.OnClickListener editListener
        //  this.editListener = editListener;
    }


    @NonNull

    //6. 온크리에이트 뷰 홀더. 여기서 뷰 홀더 객체를 생성해서 리턴한다.
    @Override
    public readingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        mContext = parent.getContext();
        //리사이클러 뷰가 들어갈 페어런트 컨텍스트

        LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        // 그 컨텍스트에서 레이아웃 인플레이터를 쓸거고

        View view = inflater.inflate(R.layout.item_reading,parent,false);
        //내가 열심히 만든 게 들어갈 뷰 자리는 만들어둔 리사이클러뷰 자리고, 그거는 페어런트 컨텍스트 내부에 있어

        Adapter_Reading.readingViewHolder viewHolder = new Adapter_Reading.readingViewHolder(view);
        //그 뷰 자리를 가지고 있는 뷰 홀더를 만들어서 리턴

        //쉐어드를 쓰고 싶다면 온크리에이트 뷰홀더에~~


        String currentEmail = mContext.getSharedPreferences("users", Context.MODE_PRIVATE).getString("currentUser","");
        userPref = mContext.getSharedPreferences(currentEmail,mContext.MODE_PRIVATE);
        editor = userPref.edit();

        return viewHolder;
    }

    //7. 뷰홀더에 묶기
    @Override
    public void onBindViewHolder(@NonNull readingViewHolder holder, int position) {

        //실제 데이터를 뷰홀더의 아이템뷰에 표시해준다.
        Dictionary_book readingItem = mList.get(position);

        if(readingItem.bookCover==null){

        }else{
            holder.bookCover.setImageBitmap(readingItem.getBookCover());
        }
       // holder.bookCover.setImageDrawable(readingItem.getBookCover());
        holder.bookTitle.setText(readingItem.getTitle());
//        holder.editBtn.setTag(holder.getAdapterPosition());
      //  holder.editBtn.setOnClickListener(editListener);

    }

    //전체 데이터 갯수 리턴
    @Override
    public int getItemCount() {
        return mList.size();
    }




}
