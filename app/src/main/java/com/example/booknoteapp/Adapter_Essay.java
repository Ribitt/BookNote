package com.example.booknoteapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class Adapter_Essay extends androidx.recyclerview.widget.RecyclerView.Adapter<Adapter_Essay.essayViewHolder> {



    ArrayList<Dictionary_Essay> mList =new ArrayList<>();
    ArrayList<Dictionary_Essay> wholeList=new ArrayList<>();
    Context mContext;

    SharedPreferences userPref;
    SharedPreferences.Editor userPrefEditor;
    SharedPreferences devPref;
    SharedPreferences.Editor devPrefEditor;

    Boolean isOpen;

    String nickname;
    String userEmail;

    int position;
    int positionInWhole;

    CharSequence[] list_edit_or_delete = {"에세이 수정하기","삭제하기"};



    Adapter_Essay(ArrayList<Dictionary_Essay> list) {
        this.mList = list;


    }

    public class essayViewHolder extends androidx.recyclerview.widget.RecyclerView.ViewHolder{

        TextView nickname;
        ImageView edit_or_delete;
        TextView bookTitle;
        TextView bookAuthor;
        ImageView bookCover;
        TextView essayTitle;
        TextView essayContent;
        TextView date;
        TextView likeNum;
        ImageView like;
        TextView tv_isOpen;



        public essayViewHolder(@NonNull View view) {
            super(view);


            nickname = view.findViewById(R.id.tv_nickname);
            edit_or_delete = view.findViewById(R.id.iv_more);
            bookTitle = view.findViewById(R.id.tv_book_title);
            bookAuthor=view.findViewById(R.id.tv_book_author);
            bookCover = view.findViewById(R.id.iv_book_cover);
            essayTitle  =view.findViewById(R.id.tv_essayTitle);
            essayContent =view.findViewById(R.id.tv_essayContent);
            date =view.findViewById(R.id.tv_date);
            likeNum =view.findViewById(R.id.tv_likeNum);
            like =view.findViewById(R.id.iv_like_btn);
            tv_isOpen = view.findViewById(R.id.tv_isOpen);

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
//                                            Intent intent = new Intent(mContext,EditBook.class);
//                                            Dictionary_Essay selectedBook = mList.get(getAdapterPosition());
//                                            intent.putExtra("selectedBook",selectedBook);
//                                            intent.putExtra("position",getAdapterPosition());
//                                            mContext.startActivity(intent);
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
    }

    //지금 어레이를 쉐어드에 저장하기
    private void saveArrayToPref(ArrayList<Dictionary_Essay> arrayList, SharedPreferences.Editor editor) {
        Gson gson = new Gson();
        String json = gson.toJson(arrayList);
        editor.putString("essay",json);
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

                        if(mList.get(position).isOpen){
                            //공개인 경우
                            //이미 리스트에서 해당 아이템을 지운 다음에는 포지션 값이 이상해진다. 먼저 공개리스트부터 처리
                            getArrayFromPref(devPref);
                            wholeList.remove(positionInWhole);
                            saveArrayToPref(wholeList, devPrefEditor);
                        }
                        //비공개인 경우
                        mList.remove(position);
                        notifyItemRemoved(position);
                        notifyDataSetChanged();
                        saveArrayToPref(mList, userPrefEditor);


                    }
                }).show();
    }
    //정말 삭제하시겠습니까? 끝


    private void getArrayFromPref(SharedPreferences pref){
        Gson gson = new Gson();
        String json = pref.getString("essay","EMPTY");

        Type type = new TypeToken<ArrayList<Dictionary_Essay>>() {
        }.getType();

        if(!json.equals("EMPTY")){
            wholeList = gson.fromJson(json,type);
            for(int i=0; i<wholeList.size(); i++){
                if(mList.get(position).getEssayTitle().equals(wholeList.get(i).getEssayTitle())){
                    positionInWhole = i;
                }
            }
        }
    }


    @NonNull
    @Override
    public essayViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        mContext = parent.getContext();
        LayoutInflater inflater =  (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.item_essay,parent,false);
        Adapter_Essay.essayViewHolder holder = new Adapter_Essay.essayViewHolder(view);

       devPref = parent.getContext().getSharedPreferences("users", MODE_PRIVATE);
       devPrefEditor = devPref.edit();
       userEmail = devPref.getString("currentUser","");
        userPref= parent.getContext().getSharedPreferences(userEmail,MODE_PRIVATE);
        //저장되어있는 닉네임을 불러온다
        userPrefEditor = userPref.edit();
        nickname = userPref.getString("nickname","");



        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull essayViewHolder holder, int position) {

        Dictionary_Essay dictionary_essay = mList.get(position);

        holder.nickname.setText(nickname);

        holder.bookCover.setImageBitmap(dictionary_essay.getBookCover());
        holder.bookAuthor.setText(dictionary_essay.getBookAuthor());
        holder.bookTitle.setText(dictionary_essay.getBookTitle());

        holder.likeNum.setText(dictionary_essay.getLikeNum());
        holder.essayContent.setText(dictionary_essay.getEssayContent());
        holder.essayTitle.setText(dictionary_essay.getEssayTitle());

        holder.date.setText(dictionary_essay.getDate());

        if(dictionary_essay.isOpen){
            holder.tv_isOpen.setText("공개 에세이");
        }else{
            holder.tv_isOpen.setText("비공개 에세이");
        }

        if(!userEmail.equals(dictionary_essay.getUserEmail())){
            holder.edit_or_delete.setVisibility(View.GONE);
        }else{
            holder.edit_or_delete.setVisibility(View.VISIBLE);
        }




    }

    @Override
    public int getItemCount() {
        return mList.size();
    }




}
