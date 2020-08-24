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
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class Adapter_Essay extends androidx.recyclerview.widget.RecyclerView.Adapter<Adapter_Essay.essayViewHolder> {



    ArrayList<Dictionary_Essay> showList =new ArrayList<>();
    ArrayList<Dictionary_Essay> restList =new ArrayList<>();
    ArrayList<Dictionary_Essay> wholeList=new ArrayList<>();
    Context mContext;

    SharedPreferences userPref;
    SharedPreferences.Editor userPrefEditor;
    SharedPreferences devPref;
    SharedPreferences.Editor devPrefEditor;

    Boolean isOpen;


    String userEmail;

    int position;
    int positionInWhole;

    CharSequence[] list_edit_or_delete = {"에세이 수정하기","삭제하기"};



    Adapter_Essay(ArrayList<Dictionary_Essay> showList, ArrayList<Dictionary_Essay> restList) {
        this.showList = showList;
        this.restList = restList;

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
        LinearLayout item_essay;



        public essayViewHolder(@NonNull View view) {
            super(view);

            item_essay = view.findViewById(R.id.item_essay);


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
                                            position = getAdapterPosition();

                                            Intent intent = new Intent(mContext,AddEssay.class);
                                            Dictionary_Essay selectedEssay = showList.get(getAdapterPosition());
                                            intent.putExtra("selectedEssay", selectedEssay);
                                            intent.putExtra("position",getAdapterPosition());
                                            intent.putExtra("from","edit");
                                            intent.putExtra("showList",showList);


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


                        showList.remove(position);
                        makeWholeList();
                        saveArrayToPref(wholeList, devPrefEditor);
                        notifyDataSetChanged();

//                        if(showList.get(position).isOpen){
//                            //공개인 경우
//                            //이미 리스트에서 해당 아이템을 지운 다음에는 포지션 값이 이상해진다. 먼저 공개리스트부터 처리
//                            getArrayFromPref(devPref);
//                            wholeList.remove(positionInWhole);
//                            saveArrayToPref(wholeList, devPrefEditor);
//                        }
//                        //비공개인 경우
//                        showList.remove(position);
//                        notifyItemRemoved(position);
//                        notifyDataSetChanged();
//                        saveArrayToPref(showList, userPrefEditor);


                    }
                }).show();
    }
    //정말 삭제하시겠습니까? 끝

    private void makeWholeList() {

        for(int i=0; i<showList.size(); i++){
            //원래 전체 리스트에서 가지고 있던 포지션대로 나머지 리스트에 추가해준다.
            restList.add(showList.get(i).getPosition(), showList.get(i));
        }

        wholeList = restList;


    }

    //지금 어레이를 쉐어드에 저장하기
    private void saveArrayToPref(ArrayList<Dictionary_Essay> arrayList, SharedPreferences.Editor editor) {
        Gson gson = new Gson();
        String json = gson.toJson(arrayList);
        editor.putString("essay",json);
        editor.apply();
    }



    private void getArrayFromPref(SharedPreferences pref){
        Gson gson = new Gson();
        String json = pref.getString("essay","EMPTY");

        Type type = new TypeToken<ArrayList<Dictionary_Essay>>() {
        }.getType();

        if(!json.equals("EMPTY")){
            wholeList = gson.fromJson(json,type);
            for(int i=0; i<wholeList.size(); i++){
                if(showList.get(position).getEssayTitle().equals(wholeList.get(i).getEssayTitle())){
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


        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull essayViewHolder holder, int position) {

        Dictionary_Essay dictionary_essay = showList.get(position);


        holder.nickname.setText(dictionary_essay.getNickname());
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

        //유저 이메일을 비교해서 유저가 쓴 글이 아니면 수정 삭제 버튼을 보이지 않게 한다.
        if(!userEmail.equals(dictionary_essay.getUserEmail())){
            holder.edit_or_delete.setVisibility(View.GONE);
        }else{
            holder.edit_or_delete.setVisibility(View.VISIBLE);
        }

        //공개 여부를 봐서 비공개면 띄우지 않는다.
//        if(!dictionary_essay.getOpen()){
//            holder.item_essay.setVisibility(View.GONE);
//        }
        // 이렇게 하면 공간이 텅 비어있다. 보이지 않기는 하는데..




    }

    @Override
    public int getItemCount() {
        return showList.size();
    }


}
