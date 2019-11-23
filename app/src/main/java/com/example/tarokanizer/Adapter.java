package com.example.tarokanizer;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tarokanizer.data_classes.CardView;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    private ArrayList<CardView> mCardViewList;
    private Activity mActivity;
    public OnDeleteButtonClickListener mListener;
    static final int INTENT_REQUEST = 1;

    public interface OnDeleteButtonClickListener {
        void onDeleteClick(int position);
    }

    public Adapter(Activity activity, ArrayList<CardView> cardViewList) {
        mCardViewList = cardViewList;
        mActivity = activity;
    }

    public void setOnCardBoardClickListener (final OnDeleteButtonClickListener listener){
        mListener = listener;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView title;
        RelativeLayout relativeLayout;
        public ImageView mDeleteImage;

        public ViewHolder(@NonNull View itemView, final OnDeleteButtonClickListener listener) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            mDeleteImage = itemView.findViewById(R.id.image_delete);
            relativeLayout = itemView.findViewById(R.id.parent_layout);

            mDeleteImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onDeleteClick(position);
                        }
                    }
                }
            });
        }
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view, parent, false);
        ViewHolder evh = new ViewHolder(v, mListener);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final CardView currentItem = mCardViewList.get(position);
        holder.title.setText(currentItem.getTitle());

        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //actions that happen on cardboardclick
                notifyDataSetChanged();
                Intent intent = new Intent(mActivity, Scoreboard.class);
                intent.putExtra("position", position);
                if(!(mActivity == null)) {
                    mActivity.startActivityForResult(intent, INTENT_REQUEST);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mCardViewList.size();
    }

}
