package com.example.booknoteapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class Adapter_Reading extends androidx.recyclerview.widget.RecyclerView.Adapter<Adapter_Reading.readingViewHolder> {

    //1. 어레이 리스트 정하고 그 애는 일단 비어있다. 이 리스트는 딕셔너리 객체들이 들어간다.
    ArrayList<Dictionary_book> readingArrayList = null;

    View.OnClickListener editListener;


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
            editBtn = (ImageButton) itemView.findViewById(R.id.btn_reading_edit);
            deleteBtn = (ImageButton) itemView.findViewById(R.id.btn_reading_delete);

            bookCover.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                        Intent intent = new Intent(view.getContext(),BookLog_Notes.class);
                        BitmapDrawable d = (BitmapDrawable)bookCover.getDrawable();
                        Bitmap b = d.getBitmap();
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    //스트림이 무슨 뜻인지 모르겠네
                    b.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                    byte[] byteArray = stream.toByteArray();
                    intent.putExtra("image", byteArray);

                        intent.putExtra("title", bookTitle.getText());
                        view.getContext().startActivity(intent);
                }
            });

            bookCover.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    Toast.makeText(itemView.getContext(),"롱클릭 되었다",Toast.LENGTH_LONG).show();
                    return true;
                }
            });

            deleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    readingArrayList.remove(position);
                    notifyItemRemoved(position);
                    notifyDataSetChanged();
                }
            });



        }


    }


   //5. 어댑터의 상속자. 어레이 리스트를 받는다.
    public Adapter_Reading(ArrayList<Dictionary_book> mList) {
        this.readingArrayList = mList;
        //, View.OnClickListener editListener
      //  this.editListener = editListener;
    }

    @NonNull

    //6. 온크리에이트 뷰 홀더. 여기서 뷰 홀더 객체를 생성해서 리턴한다.
    @Override
    public readingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        //리사이클러 뷰가 들어갈 페어런트 컨텍스트

        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        // 그 컨텍스트에서 레이아웃 인플레이터를 쓸거고

        View view = inflater.inflate(R.layout.item_reading,parent,false);
        //내가 열심히 만든 게 들어갈 뷰 자리는 만들어둔 리사이클러뷰 자리고, 그거는 페어런트 컨텍스트 내부에 있어

        Adapter_Reading.readingViewHolder viewHolder = new Adapter_Reading.readingViewHolder(view);
       //그 뷰 자리를 가지고 있는 뷰 홀더를 만들어서 리턴

        return viewHolder;
    }

    //7. 뷰홀더에 묶기
    @Override
    public void onBindViewHolder(@NonNull readingViewHolder holder, int position) {

        //실제 데이터를 뷰홀더의 아이템뷰에 표시해준다.
        Dictionary_book readingItem = readingArrayList.get(position);


        holder.bookCover.setImageBitmap(readingItem.getBookCover());
       // holder.bookCover.setImageDrawable(readingItem.getBookCover());
        holder.bookTitle.setText(readingItem.getTitle());
        holder.editBtn.setTag(holder.getAdapterPosition());
      //  holder.editBtn.setOnClickListener(editListener);

    }

    //전체 데이터 갯수 리턴
    @Override
    public int getItemCount() {
        return readingArrayList.size();
    }




}
