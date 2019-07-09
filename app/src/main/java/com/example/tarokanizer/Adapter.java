package com.example.tarokanizer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    private ArrayList<CardView> mCardViewList;

    public  static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView mTextView1;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mTextView1 = itemView.findViewById(R.id.textView);
        }
    }

    public Adapter(ArrayList<CardView> cardViewList){
        mCardViewList = cardViewList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view, parent, false);
        ViewHolder evh = new ViewHolder(v);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CardView currentItem = mCardViewList.get(position);
        holder.mTextView1.setText(currentItem.getmText1());
    }

    @Override
    public int getItemCount() {
        return mCardViewList.size();
    }
}
