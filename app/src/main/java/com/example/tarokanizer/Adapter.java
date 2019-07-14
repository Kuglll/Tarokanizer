package com.example.tarokanizer;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.AdapterView;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    private ArrayList<CardView> mCardViewList;
    private Context mContext;
    public OnDeleteButtonClickListener mListener;

    public interface OnDeleteButtonClickListener {
        void onDeleteClick(int position);
    }

    public Adapter(Context context, ArrayList<CardView> cardViewList) {
        mCardViewList = cardViewList;
        mContext = context;
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
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CardView currentItem = mCardViewList.get(position);
        holder.title.setText(currentItem.getmText1());

        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //actions that happen on cardboardclick
                Intent intent = new Intent(mContext, Scoreboard.class);

                //change this
                String [] players = {"tim", "mark", "okorn"};

                intent.putExtra("playerNames", players);
                if(!(mContext == null)) {
                    mContext.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mCardViewList.size();
    }
}
