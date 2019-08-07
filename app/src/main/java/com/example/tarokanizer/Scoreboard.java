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
    ArrayList<String> players;
    ArrayList<TextView> scores = new ArrayList<>();
    ArrayList<TextView> sums = new ArrayList<>();
    ArrayList<LinearLayout> radlci = new ArrayList<>();
    String mScore;

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
            TextView tv = createTextViewScore(i);
            linearLayoutScore.addView(tv);

            tv = createTextViewSum(i);
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
                int score = scoreDialog.ScoreDialog(view, Scoreboard.this);
                mScore = Integer.toString(score);
                if(mScore != null) {
                    if(mScore.trim().isEmpty()){Toast.makeText(Scoreboard.this,
                            "Invalid Score", Toast.LENGTH_LONG).show(); }
                    else {
                        tv.setText(s + mScore + "\n");

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

}

//TODO: add +/- to ScoreDialog
//TODO: automatic scrolling to bottom of scrollview when updating it
//TODO: adding and removing radlci
//TODO: storing radlci + scores in CardView class
//TODO: storing CardView class locally on the phone