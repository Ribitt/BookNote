package com.example.booknoteapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class Adapter_readBooks extends RecyclerView.Adapter<Adapter_readBooks.readViewHolder> {


    public Adapter_readBooks(ArrayList<Dictionary_book> mList) {
        this.mList = mList;
    }
        ArrayList<Dictionary_book> mList =null;



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
        Adapter_readBooks.readViewHolder holder = new Adapter_readBooks.readViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull readViewHolder holder, int position) {

        Dictionary_book dic = mList.get(position);

      //  holder.bookCover.setImageDrawable(dic.getBookCover());
        holder.title.setText(dic.getTitle());
        holder.ratingBar.setRating(dic.getRating());
        holder.ALineReview.setText(dic.getReview());
        holder.endDate.setText(dic.getFinishedDate());

        if(dic.bookCoverUri.equals("")){
            //이미지 uri가 비어있으면 일단 아무 것도 하지 않는다.
        }else{
            //사진 들어갈 자리 크기를 구한다
            int targetW = holder.bookCover.getWidth();
            int targetH =  holder.bookCover.getHeight();

            //비트맵의 디멘션을 구한다.
            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            bmOptions.inJustDecodeBounds = true;

            BitmapFactory.decodeFile(dic.bookCoverUri, bmOptions);
            int photoW = bmOptions.outWidth;
            int photoH = bmOptions.outHeight;

            //얼마나 줄일건지를 정한다
            int scaleFactor = Math.max(1, Math.min(photoW/targetW, photoH/targetH));

            //이미지 파일을 뷰에 딱 맞는 사이즈로 줄어든 비트맵으로 만든다
            bmOptions.inJustDecodeBounds=false;
            bmOptions.inSampleSize = scaleFactor;
            bmOptions.inPurgeable=true;
            Bitmap bitmap = BitmapFactory.decodeFile(dic.bookCoverUri, bmOptions);
            holder.bookCover.setImageBitmap(bitmap);

        }


    }

    @Override
    public int getItemCount() {
        return mList.size();
    }





}
