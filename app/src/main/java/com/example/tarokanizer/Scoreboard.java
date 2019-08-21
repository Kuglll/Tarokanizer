package com.example.tarokanizer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Scoreboard extends AppCompatActivity {

    LinearLayout linearLayoutPlayers;
    LinearLayout linearLayoutRadlci;
    LinearLayout linearLayoutScore;
    LinearLayout linearLayoutSum;
    CardView cardView;

    ArrayList<String> players;

    ArrayList<LinearLayout> scores = new ArrayList<>();
    ArrayList<ArrayList<String>> mScores;

    ArrayList<TextView> sums = new ArrayList<>();
    int[] mSums = {0,0,0,0,0,0,0,0};

    ArrayList<LinearLayout> radlci = new ArrayList<>();
    int[] mRadlci;

    String mScore;
    int position;

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

        position = intent.getIntExtra("position", -1);
        cardView = MainActivity.getCardViewList().get(position);

        players = cardView.getPlayers();
        mScores = cardView.getScore();
        mSums = cardView.getmSums();
        mRadlci = cardView.getRadlci();

        for(int i=0; i<players.size(); i++) {
            TextView tv = createTextViewPlayer(players.get(i), i);
            linearLayoutPlayers.addView(tv);

            LinearLayout ll = createScoreLayout(i);
            linearLayoutScore.addView(ll);

            tv = createTextViewSum(i);
            linearLayoutSum.addView(tv);

            ll = createPlayersRadlcLayout(i);
            linearLayoutRadlci.addView(ll);
        }

        loadScores();
        loadRadlci();
        loadSums();
    }

    public void loadScores(){
        TextView tv;
        for(int i=0; i<players.size(); i++){
            for(int k=0; k<mScores.get(i).size(); k++) {
                tv = createTextViewScore(0, mScores.get(i).get(k));
                scores.get(i).addView(tv);
            }
        }
    }

    public void loadRadlci(){
        for(int i=0; i<players.size(); i++){
            LinearLayout ll = radlci.get(i);
            for(int k=0; k<mRadlci[i]; k++){
                ll.addView(createRadlc());
            }
        }
    }

    public void loadSums(){
        for(int i=0; i<players.size(); i++){
            TextView tv = sums.get(i);
            tv.setText(""+mSums[i]);
        }
    }

    public TextView createTextViewPlayer(String player, int id){
        //params = params are set here rather than in xml in layout
        TextView textView = new TextView(this);
        textView.setId(id);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 36);
        textView.setText(player);
        textView.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT, 1f));
        textView.setGravity(Gravity.CENTER);
        textView.setBackgroundResource(R.drawable.black);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog scoreDialog = new Dialog(Scoreboard.this);
                Integer a = scoreDialog.RadlcDialog(view, Scoreboard.this);
                if(a != null) {
                    radlci.get(view.getId()).removeAllViews();
                    LinearLayout ll = radlci.get(view.getId());
                    mRadlci[view.getId()] = a; //saving integer of radlci
                    for (int i = 0; i < a; i++) {
                        ll.addView(createRadlc());
                    }
                }
            }
        });

        return textView;
    }

    public LinearLayout createPlayersRadlcLayout(int i){
        LinearLayout ll = new LinearLayout(this);
        ll.setId(i);
        ll.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                50,1f));
        ll.setGravity(Gravity.CENTER_HORIZONTAL);
        ll.setBackgroundResource(R.drawable.black);

        radlci.add(ll);
        return ll;
    }

    public ImageView createRadlc(){
        ImageView radlc = new ImageView(this);
        radlc.setLayoutParams(new LinearLayout.LayoutParams(40,40));
        radlc.setPadding(5,10,5,0);
        radlc.setImageResource(R.color.colorAccent);

        return radlc;
    }

    public LinearLayout createScoreLayout(int id) {
        LinearLayout ll = new LinearLayout(this);
        ll.setId(id);
        ll.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT,1f));
        ll.setGravity(Gravity.CENTER_HORIZONTAL);
        ll.setBackgroundResource(R.drawable.black);
        ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Dialog scoreDialog = new Dialog(Scoreboard.this);
                java.lang.Integer score = scoreDialog.ScoreDialog(view, Scoreboard.this);
                if(score != null) mScore = Integer.toString(score);
                if(mScore != null) {
                    if(mScore.trim().isEmpty()){
                        Toast.makeText(Scoreboard.this,"Invalid Score", Toast.LENGTH_LONG).show(); }
                    else {

                        TextView tv = createTextViewScore(view.getId(), mScore);
                        scores.get(view.getId()).addView(tv);
                        mScores.get(view.getId()).add(mScore);

                        //updating sum at the end
                        mSums[view.getId()] += score;
                        tv = sums.get(view.getId());
                        tv.setText(""+mSums[view.getId()]);
                    }
                }
                score = null;
                mScore = null;
            }
        });

        scores.add(ll);
        return ll;
    }

    public TextView createTextViewScore(int id, String score){
        TextView textView = new TextView(this);
        textView.setText(score);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24);
        textView.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT, 1f));
        textView.setGravity(Gravity.CENTER_HORIZONTAL);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //on long press - edit or delete the textview
            }
        });

        return textView;
    }

    public TextView createTextViewSum(int id){
        TextView textView = new TextView(this);
        textView.setText("0");
        textView.setId(id);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30);
        textView.setTypeface(Typeface.DEFAULT_BOLD);
        textView.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT, 1f));
        textView.setGravity(Gravity.CENTER_HORIZONTAL);
        textView.setBackgroundResource(R.drawable.black2);

        sums.add(textView);
        return textView;
    }

    public void saveScore(int id, TextView tv){
        //TextView t = cardView.getScore().get(id);
        //t = tv;
    }
}

//Sark
//TODO: adding and removing radlci

//Kugl
//TODO: storing CardView class locally on the phone
//TODO: FIX crashing of app, when deleting certain item (game)

//TODO: first time setup - quick tutorial
//TODO: editing score textviews

//TEST: pressing back button and closing app
//TEST: closing app from scoreboard
//TEST: tilting the phone