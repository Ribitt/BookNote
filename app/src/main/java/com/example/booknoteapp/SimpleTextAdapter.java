package com.example.booknoteapp;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.view.Gravity;
import android.view.LayoutInflater;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class SimpleTextAdapter extends RecyclerView.Adapter<SimpleTextAdapter.CustomViewHolder> {
//ArrayList에 있는 Dicitionary클래스 타입의 데이터를 RecyclerView에 보여주는 처리를 한다.

    private ArrayList<String> mList = null;
    //어댑터에는 데이터 리스트가 있다.


    //생성자에서 데이터 리스트 객체를 전달받는다.
    public SimpleTextAdapter(ArrayList<String> list) {
        //이 어댑터의 생성자는 문자열 타입 애들이 저장된 어레이 리스트를 받는다.
        this.mList = list;
    }


    //어댑터 내부에서 아이템 뷰를 저장하는 뷰홀더 클래스가 있다.
    public class CustomViewHolder extends androidx.recyclerview.widget.RecyclerView.ViewHolder {
        //이 뷰홀더는 andoidx.recyclerview.widget.RecylclerView.ViewhHolder를 상속받아야 한다.
        //예제에 나와있는 RecyclerView.ViewHolder하면 이거 상속받으면 안된다고 에러가 난다.

        protected TextView english;

        public CustomViewHolder(View itemView) {
            super(itemView);
            //여기서 뷰가 왜 필요한걸까 음... 여태는 findViewById만 필요했는데~

            //뷰 객체에 대한 참조.
            this.english = (TextView) itemView.findViewById(R.id.english_listItem);

        }
    }


    @NonNull

    //아이템 뷰를 위한 뷰홀더 객체 생성하여 리턴한다.
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.item_list, parent, false);
        //뷰 그룹이랑 뷰가 뭔지 좀 이해를 해야겠네

        SimpleTextAdapter.CustomViewHolder mholder = new SimpleTextAdapter.CustomViewHolder(view);

        return mholder;
    }


    //position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시한다.
    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        String text = mList.get(position);
        holder.english.setText(text);

    }

    @Override
    public int getItemCount() {
        return mList.size();
        //전체 데이터 갯수를 리턴함

    }

}
