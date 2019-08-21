package com.example.tarokanizer;

import android.widget.TextView;

import java.util.ArrayList;

public class CardView {

    private String mTitle;
    private ArrayList<String> mPlayers;
    private ArrayList<ArrayList<String>> mScore = new ArrayList<>();
    private int [] mRadlci = {0,0,0,0,0,0,0,0};
    private int [] mSums = {0,0,0,0,0,0,0,0};

    CardView(String title, ArrayList<String> players){
        mTitle = title;
        mPlayers = players;
        for(int i=0; i<mPlayers.size(); i++){
            mScore.add(new ArrayList<String>());
        }
    }

    public String getTitle() {
        return mTitle;
    }

    public ArrayList<String> getPlayers(){
        return mPlayers;
    }

    public ArrayList<ArrayList<String>> getScore() {
        return mScore;
    }

    public int[] getRadlci() {
        return mRadlci;
    }

    public int[] getmSums() { return mSums; }
}
