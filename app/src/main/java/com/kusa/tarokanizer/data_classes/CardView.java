package com.kusa.tarokanizer.data_classes;

import java.util.ArrayList;

public class CardView {

    private String mTitle;
    private ArrayList<Player> mPlayers;
    private ArrayList<Round> rounds;
    private ArrayList<ArrayList<String>> mScore = new ArrayList<>();
    private int [] mRadlci;
    private int [] mSums;

    public CardView(String title, ArrayList<Player> players){
        mTitle = title;
        mPlayers = players;
        mRadlci = new int[mPlayers.size()];
        mSums = new int[mPlayers.size()];
        for(int i=0; i<mPlayers.size(); i++) {
            mScore.add(new ArrayList<String>());
            mRadlci[i] = 0;
            mSums[i] = 0;
        }
        rounds = new ArrayList<>();
    }

    public String getTitle() {
        return mTitle;
    }

    public ArrayList<Player> getPlayers(){
        return mPlayers;
    }

    public ArrayList<Round> getRounds(){
        return rounds;
    }

    public ArrayList<ArrayList<String>> getScore() {
        return mScore;
    }

    public int[] getRadlci() {
        return mRadlci;
    }

    public int[] getmSums() { return mSums; }


}
