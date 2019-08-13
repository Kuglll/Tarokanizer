package com.example.tarokanizer;

import androidx.annotation.DrawableRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.Image;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Scoreboard extends AppCompatActivity {

    LinearLayout linearLayoutPlayers;
    LinearLayout linearLayoutRadlci;
    LinearLayout linearLayoutScore;
    LinearLayout linearLayoutSum;
    CardView cardView;
    ArrayList<String> players;
    ArrayList<TextView> scores;
    ArrayList<TextView> sums;
    ArrayList<LinearLayout> radlci = new ArrayList<>();
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
        scores = cardView.getScore();
        sums = cardView.getmSums();

        for (String player: players) {
            TextView tv = createTextViewPlayer(player);
            linearLayoutPlayers.addView(tv);
        }
        for(int i=0; i<players.size(); i++) {
            TextView tv = null;
            if(scores.size() != players.size()) {
                tv = createTextViewScore(i);
            } else{
                tv = scores.get(i);
                ((ViewGroup)tv.getParent()).removeView(tv);
            }
            //tv.setText(scores.get(i).getText());
            linearLayoutScore.addView(tv);

            if(sums.size() != players.size()) {
                tv = createTextViewSum(i);
            } else{
                tv = sums.get(i);
                ((ViewGroup)tv.getParent()).removeView(tv);
            }
            linearLayoutSum.addView(tv);

            LinearLayout ll = createPlayersRadlcLayout(i);
            linearLayoutRadlci.addView(ll);
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
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog scoreDialog = new Dialog(Scoreboard.this);
                scoreDialog.RadlcDialog(view, Scoreboard.this);
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

    public TextView createTextViewScore(int id){
        TextView textView = new TextView(this);
        textView.setId(id);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24);
        textView.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT, 1f));
        textView.setGravity(Gravity.CENTER_HORIZONTAL);
        textView.setBackgroundResource(R.drawable.black);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //adding new row to score
                TextView tv = scores.get(view.getId());
                String s = tv.getText().toString();

                Dialog scoreDialog = new Dialog(Scoreboard.this);
                java.lang.Integer score = scoreDialog.ScoreDialog(view, Scoreboard.this);
                if(score != null) mScore = Integer.toString(score);
                score = null;
                if(mScore != null) {
                    if(mScore.trim().isEmpty()){
                        Toast.makeText(Scoreboard.this,"Invalid Score", Toast.LENGTH_LONG).show(); }
                    else {
                        s = s + mScore + "\n";
                        tv.setText(s);
                        saveData(view.getId(), tv);

                        //updating sum at the end
                        tv = sums.get(view.getId());
                        int i = Integer.parseInt(tv.getText().toString());
                        i += Integer.parseInt(mScore);
                        tv.setText("" + i);

                        //test - adding radlc when clicking on textview to add score
                        LinearLayout ll = radlci.get(view.getId());
                        ll.addView(createRadlc());
                    }
                }
                mScore = null;
            }
        });

        scores.add(textView);
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

    public void saveData(int id, TextView tv){
        TextView t = cardView.getScore().get(id);
        t = tv;
    }

}

//Sark
//TODO: automatic scrolling to bottom of scrollview when updating it
//TODO: adding and removing radlci

//Kugl
//TODO: storing radlci in CardView class
//TODO: storing CardView class locally on the phone
//TODO: FIX crashing of app, when deleting certain item (game)


//TEST: pressing back button and closing app
//TEST: closing app from scoreboard
//TEST: tilting the phone