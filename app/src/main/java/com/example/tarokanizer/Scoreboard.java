package com.example.tarokanizer;

import androidx.annotation.DrawableRes;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Scoreboard extends AppCompatActivity {

    LinearLayout linearLayoutPlayers;
    LinearLayout linearLayoutRadlci;
    LinearLayout linearLayoutScore;
    LinearLayout linearLayoutSum;
    ArrayList<String> players;
    ArrayList<TextView> scores = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scoreboard);

        linearLayoutPlayers = findViewById(R.id.names);
        linearLayoutRadlci = findViewById(R.id.radlci);
        linearLayoutScore = findViewById(R.id.score);
        linearLayoutSum = findViewById(R.id.sum);

        initialize();
    }

    public void initialize(){
        Intent intent = getIntent();

        players = intent.getStringArrayListExtra("playerNames");
        for (String player: players) {
            TextView tv = createTextViewPlayer(player);
            linearLayoutPlayers.addView(tv);
        }
        for(int i=0; i<players.size(); i++){
            createTextViewScore(i);
        }

    }


    public TextView createTextViewPlayer(String player){
        //params = params are set here rather than in xml in layout
        TextView textView = new TextView(this);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 36);
        textView.setText(player);
        textView.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT, 1f));
        textView.setGravity(Gravity.CENTER);
        textView.setBackgroundResource(R.drawable.black);
        return textView;
    }

    public TextView createTextViewRadlci(){
        return null;
    }

    public void createTextViewScore(int textViewId){
        TextView textView = new TextView(this);
        textView.setId(textViewId);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24);
        textView.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT, 1f));
        textView.setGravity(Gravity.CENTER_HORIZONTAL);
        textView.setBackgroundResource(R.drawable.black);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView tv = scores.get(view.getId());
                String s = tv.getText().toString();
                //Tim please provide dialog here
                tv.setText(s + "10\n");
            }
        });

        linearLayoutScore.addView(textView);
        scores.add(textView);
    }

}
