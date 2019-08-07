package com.example.tarokanizer;

import java.util.ArrayList;

public class CardView {

    private String mTitle;
    private ArrayList<String> mPlayers;
    private ArrayList<String> mScore;
    private ArrayList<ArrayList> mRadlci;

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

    public ArrayList<String> getScore() {
        return mScore;
    }

    public ArrayList<ArrayList> getRadlci() {
        return mRadlci;
    }
}
