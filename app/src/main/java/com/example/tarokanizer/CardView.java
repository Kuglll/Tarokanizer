package com.example.tarokanizer;

public class CardView {

    private String mText1;

    CardView(String text1){
        mText1 = text1;
    }

    public String getmText1() {
        return mText1;
    }

    public void OpenCardBoard(String text){
        mText1 = text;
    }
}
