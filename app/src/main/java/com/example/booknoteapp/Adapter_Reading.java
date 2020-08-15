package com.example.booknoteapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class Adapter_Reading extends androidx.recyclerview.widget.RecyclerView.Adapter<Adapter_Reading.readingViewHolder> {

    //1. 어레이 리스트 정하고 그 애는 일단 비어있다. 이 리스트는 딕셔너리 객체들이 들어간다.
    ArrayList<Dictionary_book> readingArrayList = null;

    SharedPreferences pref;
    SharedPreferences.Editor editor;

    View.OnClickListener editListener;
    Context mContext;


    //2. 뷰홀더를 상속하는 뷰홀더를 만든다.
    public class readingViewHolder extends androidx.recyclerview.widget.RecyclerView.ViewHolder
                                    implements View.OnCreateContextMenuListener{



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

            view.setOnCreateContextMenuListener(this);



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


        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
            MenuItem Edit = contextMenu.add(Menu.NONE, 1001, 1, "수정하기");
            MenuItem Delete = contextMenu.add(Menu.NONE, 1002, 1, "삭제하기");
            Edit.setOnMenuItemClickListener(onEditMenu);
            Delete.setOnMenuItemClickListener(onEditMenu);
        }

        private final MenuItem.OnMenuItemClickListener onEditMenu = new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                switch (item.getItemId()){
                    case 1001:
                        //수정하기
                        Intent intent = new Intent(mContext,AddBook.class);
                        mContext.startActivity(intent);
                        break;

                    case 1002:
                           readingArrayList.remove(getAdapterPosition());
                           notifyItemRemoved(getAdapterPosition());
                           notifyDataSetChanged();
                           saveBookArrayToPref(readingArrayList);
                    break;
                }

                return true;
            }
        };

    }

    private void saveBookArrayToPref(ArrayList<Dictionary_book> arrayList) {
        Gson gson = new Gson();
        String json = gson.toJson(arrayList);
        editor.putString("books",json);
        editor.apply();
    }


   //5. 어댑터의 생성자 어레이 리스트를 받는다.

    public Adapter_Reading(ArrayList<Dictionary_book> mList) {
        this.readingArrayList = mList;
      //  pref = mContext.getSharedPreferences("book", Activity.MODE_PRIVATE);
      //  editor = pref.edit();

        //, View.OnClickListener editListener
        //  this.editListener = editListener;
    }


    @NonNull

    //6. 온크리에이트 뷰 홀더. 여기서 뷰 홀더 객체를 생성해서 리턴한다.
    @Override
    public readingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        mContext = parent.getContext();
        //리사이클러 뷰가 들어갈 페어런트 컨텍스트
        pref = mContext.getSharedPreferences("book", MODE_PRIVATE);
        editor = pref.edit();

        LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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

        if(readingItem.bookCover==null){

        }else{
            holder.bookCover.setImageBitmap(readingItem.getBookCover());
        }
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
