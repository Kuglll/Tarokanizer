package com.example.tarokanizer;

import java.util.ArrayList;

public class CardView {

    private String mTitle;
    private ArrayList<String> mPlayers;

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
}
