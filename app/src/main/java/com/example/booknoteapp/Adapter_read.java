package com.example.booknoteapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.google.gson.Gson;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;


public class Adapter_read extends RecyclerView.Adapter<Adapter_read.readViewHolder> {


    public Adapter_read(ArrayList<Dictionary_book> mList) {
        this.mList = mList;
    }

    ArrayList<Dictionary_book> mList;

    Context mContext;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    CharSequence[] list_edit_or_delete = {"책 정보 수정하기","책 삭제하기"};

    int position;



    public class readViewHolder extends RecyclerView.ViewHolder{

        ImageView bookCover;
        TextView title;
        RatingBar ratingBar;
        TextView ALineReview;
        TextView endDate;
        ImageView edit_or_delete;
        LinearLayout layout;

        public readViewHolder(@NonNull final View itemView) {
            super(itemView);
            bookCover = itemView.findViewById(R.id.btn_readD_bookCover);
            title = itemView.findViewById(R.id.tv_readD_bookTitle);
            ALineReview = itemView.findViewById(R.id.tv_readD_ALineReview);
            ratingBar = itemView.findViewById(R.id.rating_readD);
            endDate = itemView.findViewById(R.id.tv_readD_endDate);
            edit_or_delete = itemView.findViewById(R.id.btn_edit_or_delete);
            layout = itemView.findViewById(R.id.layout_read_toBook);


            //책 상세로 이동하는 클릭 리스너
            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext,BookLog_Notes.class);
                    Dictionary_book selectedBook = mList.get(getAdapterPosition());
                    intent.putExtra("selectedBook",selectedBook);
                    intent.putExtra("position",getAdapterPosition());
                   // intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);

                    editor.putString("bookNow",selectedBook.getTitle());
                    editor.commit();
                    mContext.startActivity(intent);
                }
            });

            //책 상세로 이동하는 클릭 리스너

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
                                            Intent intent = new Intent(mContext,EditBook.class);
                                            Dictionary_book selectedBook = mList.get(getAdapterPosition());
                                            intent.putExtra("selectedBook",selectedBook);
                                            intent.putExtra("position",getAdapterPosition());
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
            ////수정삭제 클릭 리스너

        }
    }//////뷰홀더는 여기까지

    private void alert() {
        AlertDialog.Builder reallyGoOutAlert = new AlertDialog.Builder(mContext);
        reallyGoOutAlert.setTitle("정말 나가시겠습니까?")
                .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("나가기", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        mList.remove(position);
                        notifyItemRemoved(position);
                        notifyDataSetChanged();
                        saveBookArrayToPref(mList);
                    }
                }).show();
    }




    //지금 어레이를 쉐어드에 저장하기
    private void saveBookArrayToPref(ArrayList<Dictionary_book> arrayList) {
        Gson gson = new Gson();
        String json = gson.toJson(arrayList);
        editor.putString("read",json);
        editor.apply();
    }


    @NonNull
    @Override
    public readViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_read, parent, false);
        Adapter_read.readViewHolder holder = new Adapter_read.readViewHolder(view);

        //쉐어드를 쓰고 싶다면 온크리에이트 뷰홀더에~~
        String currentEmail = mContext.getSharedPreferences("users", Context.MODE_PRIVATE).getString("currentUser","");
        pref = mContext.getSharedPreferences(currentEmail,mContext.MODE_PRIVATE);
        editor = pref.edit();


        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull readViewHolder holder, int position) {

        Dictionary_book dic = mList.get(position);

        holder.title.setText(dic.getTitle());
        holder.ratingBar.setRating(dic.getRating());
        holder.ALineReview.setText(dic.getReview());
        holder.endDate.setText(dic.getFinishedDate());

        if(dic.bookCover==null){

        }else{
            holder.bookCover.setImageBitmap(dic.getBookCover());
        }






    }

    @Override
    public int getItemCount() {
        return mList.size();
    }





}
