package com.example.booknoteapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.drawable.GradientDrawable;
import android.text.Html;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;


import com.abdulhakeem.seemoretextview.SeeMoreTextView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.w3c.dom.Text;

import java.lang.reflect.Type;
import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class Adapter_Essay extends androidx.recyclerview.widget.RecyclerView.Adapter<Adapter_Essay.essayViewHolder> {



    ArrayList<Dictionary_Essay> showList;
    ArrayList<Dictionary_Essay> wholeList;
    Context mContext;

    SharedPreferences userPref;
    SharedPreferences.Editor userPrefEditor;
    SharedPreferences devPref;
    SharedPreferences.Editor devPrefEditor;

    Boolean likeClicked = false;

    Boolean expandable=true;
    String userEmail;

    int position;
    int positionInWhole;
    private int lastPosition = -1;

    CharSequence[] list_edit_or_delete = {"에세이 수정하기","삭제하기"};



    Adapter_Essay(ArrayList<Dictionary_Essay> showList, ArrayList<Dictionary_Essay> wholeList) {
        this.showList = showList;
        this.wholeList = wholeList;

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

        TextView tv_see_more;





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
            tv_see_more = (TextView) view.findViewById(R.id.tv_see_more);

            final int maxLine = essayContent.getMaxLines();

            essayContent.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    Log.d("지금 에세이 내용 줄 수는?", String.valueOf(essayContent.getLineCount()));
                    if(essayContent.getLineCount()>=maxLine){
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
                        essayContent.setMaxLines(Integer.MAX_VALUE);
                        tv_see_more.setText("접기");
                    }else{
                        essayContent.setMaxLines(maxLine);
                        tv_see_more.setText("더보기");
                    }
                }
            });


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

                        wholeList.remove(showList.get(position).getPositionInWhole());
                        //전체 리스트에서 지운다
                        showList.remove(position);
                        //보여줄 리스트에서도 지운다
                        //showList에서 먼저 지우면 인덱스 오류난다.
                        //Log.d("보여줄 리스트에서 지우고 난 다음에 전체 리스트 사이즈 : ", String.valueOf(wholeList.size()));

                        saveArrayToPref(wholeList, devPrefEditor);
                        //전체 리스트를 저장한다
                        notifyDataSetChanged();


                    }
                }).show();
    }
    //정말 삭제하시겠습니까? 끝


    //지금 어레이를 쉐어드에 저장하기
    private void saveArrayToPref(ArrayList<Dictionary_Essay> arrayList, SharedPreferences.Editor editor) {
        Gson gson = new Gson();
        String json = gson.toJson(arrayList);
        editor.putString("essay",json);
        editor.apply();
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
    public void onBindViewHolder(@NonNull final essayViewHolder holder, final int position) {

        Dictionary_Essay dictionary_essay = showList.get(position);


        holder.nickname.setText(dictionary_essay.getNickname());
        holder.bookCover.setImageBitmap(dictionary_essay.getBookCover());
        holder.bookAuthor.setText(dictionary_essay.getBookAuthor());
        holder.bookTitle.setText(dictionary_essay.getBookTitle());

        holder.likeNum.setText(dictionary_essay.getLikeNum());
        holder.essayContent.setText(dictionary_essay.getEssayContent());
        holder.essayTitle.setText(dictionary_essay.getEssayTitle());

        holder.date.setText(dictionary_essay.getDate());


        holder.like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int likeNumber;
                likeNumber = Integer.parseInt(holder.likeNum.getText().toString());

                if(!likeClicked){
                    likeClicked=true;
                    likeNumber= likeNumber+1;
                    holder.like.setImageDrawable(mContext.getDrawable(R.drawable.ic_like));
                    int color = mContext.getColor(R.color.yellowGreen);
                    holder.like.setColorFilter(color);

                }else{
                    likeClicked=false;
                    likeNumber= likeNumber-1;
                    holder.like.setImageDrawable(mContext.getDrawable(R.drawable.ic_empty_like));
                    int color = mContext.getColor(R.color.myBlack);
                    holder.like.setColorFilter(color);
                }

                holder.likeNum.setText(String.valueOf(likeNumber));
                showList.get(position).setLikeNum(String.valueOf(likeNumber));

            }
        });

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


//
//    public static void makeTextViewResizable(final TextView tv, final int maxLine, final String expandText, final boolean viewMore) {
//
//        if (tv.getTag() == null) {
//            tv.setTag(tv.getText());
//        }
//        ViewTreeObserver vto = tv.getViewTreeObserver();
//        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//
//            @SuppressWarnings("deprecation")
//            @Override
//            public void onGlobalLayout() {
//                String text;
//                int lineEndIndex;
//                ViewTreeObserver obs = tv.getViewTreeObserver();
//                obs.removeGlobalOnLayoutListener(this);
//                if (maxLine == 0) {
//                    lineEndIndex = tv.getLayout().getLineEnd(0);
//                    text = tv.getText().subSequence(0, lineEndIndex - expandText.length() + 1) + " " + expandText;
//                } else if (maxLine > 0 && tv.getLineCount() >= maxLine) {
//                    lineEndIndex = tv.getLayout().getLineEnd(maxLine - 1);
//                    text = tv.getText().subSequence(0, lineEndIndex - expandText.length() + 1) + " " + expandText;
//                } else {
//                    lineEndIndex = tv.getLayout().getLineEnd(tv.getLayout().getLineCount() - 1);
//                    text = tv.getText().subSequence(0, lineEndIndex) + " " + expandText;
//                }
//                tv.setText(text);
//                tv.setMovementMethod(LinkMovementMethod.getInstance());
//                tv.setText(
//                        addClickablePartTextViewResizable(Html.fromHtml(tv.getText().toString()), tv, lineEndIndex, expandText,
//                                viewMore), TextView.BufferType.SPANNABLE);
//            }
//        });
//
//    }
//
//    private static SpannableStringBuilder addClickablePartTextViewResizable(final Spanned strSpanned, final TextView tv,
//                                                                            final int maxLine, final String spanableText, final boolean viewMore) {
//        String str = strSpanned.toString();
//        SpannableStringBuilder ssb = new SpannableStringBuilder(strSpanned);
//
//        if (str.contains(spanableText)) {
//            ssb.setSpan(new ClickableSpan() {
//
//                @Override
//                public void onClick(View widget) {
//                    tv.setLayoutParams(tv.getLayoutParams());
//                    tv.setText(tv.getTag().toString(), TextView.BufferType.SPANNABLE);
//                    tv.invalidate();
//                    if (viewMore) {
//                        makeTextViewResizable(tv, maxLine, "접기", false);
//                    } else {
//                        makeTextViewResizable(tv, maxLine-2, "더보기", true);
//                    }
//
//                }
//            }, str.indexOf(spanableText), str.indexOf(spanableText) + spanableText.length(), 0);
//
//        }
//        return ssb;
//
//    }
//
//
//
//    public static void setReadMore(final TextView view, final String text, final int maxLine) {
//        final Context context = view.getContext();
//        final String expanedText = " ... 더보기";
//
//        if (view.getTag() != null && view.getTag().equals(text)) { //Tag로 전값 의 text를 비교하여똑같으면 실행하지 않음.
//            return;
//        }
//        view.setTag(text); //Tag에 text 저장
//        view.setText(text); // setText를 미리 하셔야  getLineCount()를 호출가능
//        view.post(new Runnable() { //getLineCount()는 UI 백그라운드에서만 가져올수 있음
//            @Override
//            public void run() {
//                if (view.getLineCount() >= maxLine) { //Line Count가 설정한 MaxLine의 값보다 크다면 처리시작
//
//                    int lineEndIndex = view.getLayout().getLineVisibleEnd(maxLine - 1); //Max Line 까지의 text length
//
//                    String[] split = text.split("\n"); //text를 자름
//                    int splitLength = 0;
//
//                    String lessText = "";
//                    for (String item : split) {
//                        splitLength += item.length() + 1;
//                        if (splitLength >= lineEndIndex) { //마지막 줄일때!
//                            if (item.length() >= expanedText.length()) {
//                                lessText += item.substring(0, item.length() - (expanedText.length())) + expanedText;
//                            } else {
//                                lessText += item + expanedText;
//                            }
//                            break; //종료
//                        }
//                        lessText += item + "\n";
//                    }
//                    SpannableString spannableString = new SpannableString(lessText);
//                    spannableString.setSpan(new ClickableSpan() {//클릭이벤트
//                        @Override
//                        public void onClick(View v) {
//                            view.setText(text);
//                        }
//
//                        @Override
//                        public void updateDrawState(TextPaint ds) { //컬러 처리
//                            ds.setColor(ContextCompat.getColor(context, R.color.green));
//                        }
//                    }, spannableString.length() - expanedText.length(), spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//                    view.setText(spannableString);
//                    view.setMovementMethod(LinkMovementMethod.getInstance());
//                }
//            }
//        });
//    }
//
