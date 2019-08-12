package com.example.tarokanizer;

import android.widget.TextView;

import java.util.ArrayList;

public class CardView {

    private String mTitle;
    private ArrayList<String> mPlayers;
    private ArrayList <TextView> mScore = new ArrayList<>();
    private ArrayList<ArrayList> mRadlci = new ArrayList<>();
    private ArrayList<TextView> mSums = new ArrayList<>();

    CardView(String title, ArrayList<String> players){
        mTitle = title;
        mPlayers = players;
    }

    public String getTitle() {
        return mTitle;
    }

    public ArrayList<String> getPlayers(){
        return mPlayers;
    }

    public ArrayList<TextView> getScore() {
        return mScore;
    }

    public ArrayList<ArrayList> getRadlci() {
        return mRadlci;
    }

    public ArrayList<TextView> getmSums() { return mSums; }
}
