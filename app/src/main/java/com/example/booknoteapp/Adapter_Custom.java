package com.example.booknoteapp;

import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class Adapter_Custom extends androidx.recyclerview.widget.RecyclerView.Adapter<Adapter_Custom.CustomViewHolder>{


  private ArrayList<Dictionary> myList;


  public class CustomViewHolder extends androidx.recyclerview.widget.RecyclerView.ViewHolder{

      protected TextView id;
      protected TextView english;
      protected TextView korean;


      public CustomViewHolder(@NonNull View itemView) {
          super(itemView);
          this.id = itemView.findViewById(R.id.id_listitem);
          this.english= itemView.findViewById(R.id.b_english_listitem);
          this.korean = itemView.findViewById(R.id.korean_listitem);
      }
  }

    public Adapter_Custom(ArrayList<Dictionary> myList) {

      this.myList = myList;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

      View view = LayoutInflater.from(parent.getContext())
              .inflate(R.layout.w_button_item_list, parent, false);

      CustomViewHolder customViewHolder = new CustomViewHolder(view);

        return customViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
      //글씨 크기 정하기
      holder.id.setTextSize(TypedValue.COMPLEX_UNIT_SP,25);
      holder.english.setTextSize(TypedValue.COMPLEX_UNIT_SP,25);
      holder.korean.setTextSize(TypedValue.COMPLEX_UNIT_SP,25);

      //그래비티 정하기
      holder.id.setGravity(Gravity.CENTER);
      holder.english.setGravity(Gravity.CENTER);
      holder.korean.setGravity(Gravity.CENTER);


      holder.id.setText(myList.get(position).getId());
      holder.english.setText(myList.get(position).getEnglish());
      holder.korean.setText(myList.get(position).getKorean());

    }

    @Override
    public int getItemCount() {
        return (null != myList ? myList.size():0);
    }
}
