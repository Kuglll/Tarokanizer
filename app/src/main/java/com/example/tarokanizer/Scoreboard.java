package com.example.tarokanizer;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.widget.TextViewCompat;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tarokanizer.data_classes.CardView;
import com.example.tarokanizer.data_classes.Player;
import com.example.tarokanizer.data_classes.Settings;

import java.util.ArrayList;

public class Scoreboard extends AppCompatActivity {

    LinearLayout linearLayoutPlayers;
    LinearLayout linearLayoutRadlci;
    LinearLayout linearLayoutScore;
    LinearLayout linearLayoutSum;
    LinearLayout ll;
    CardView cardView;
    private Toolbar toolbar;
    private Button buttonNew;

    ArrayList<Player> players;

    ArrayList<LinearLayout> scores = new ArrayList<>();
    ArrayList<ArrayList<String>> mScores;

    ArrayList<TextView> sums = new ArrayList<>();
    int[] mSums = {0,0,0,0,0,0,0,0};

    ArrayList<LinearLayout> radlci = new ArrayList<>();
    int[] mRadlci;

    String mScore;
    int position;

    Settings settings;

    //add game variables
    boolean [] checked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scoreboard);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        settings = new Settings();

        initViews();

        initializeUi();
        initializeOnClickListeners();
    }

    public void initViews(){
        linearLayoutPlayers = findViewById(R.id.names);
        linearLayoutRadlci = findViewById(R.id.radlci);
        linearLayoutScore = findViewById(R.id.score);
        linearLayoutSum = findViewById(R.id.sum);

        buttonNew = findViewById(R.id.button_new);
    }

    public void initializeOnClickListeners(){
        buttonNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayWhoPlayedDialog();
            }
        });
    }

    public void displayWhoPlayedDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(Scoreboard.this);
        builder.setTitle("Who played the game?");
        String [] mPlayers = new String[players.size()];
        checked = new boolean[players.size()];

        for(int i=0; i<players.size(); i++){
            mPlayers[i] = players.get(i).getName();
            checked[i] = false;
            Log.d("PLAYERS", mPlayers[i]);
        }

        builder.setMultiChoiceItems(mPlayers, checked, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i, boolean b) {

            }
        });

        builder.setPositiveButton("NEXT", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                displayWhatGameWasPlayed();
                for(int k=0; k<checked.length; k++) {
                    Log.d("CHECKED", "" + checked[k]);
                }
            }
        });

        builder.setNegativeButton("CANCEL", null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void displayWhatGameWasPlayed(){
        AlertDialog.Builder builder = new AlertDialog.Builder(Scoreboard.this);
        final String [] games = {"ena", "dva", "tri", "klop", "berac", "pikolo", "solo ena", "solo dva", "solo tri", "solo brez", "valat", "barvni valat"};
        builder.setTitle("Select the game that was played")
                .setItems(games, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case 0: selectDialogForGame(games[0]); break;
                            case 1: selectDialogForGame(games[1]); break;
                            case 2: selectDialogForGame(games[2]); break;
                            case 3: selectDialogForGame(games[3]); break;
                            case 4: selectDialogForGame(games[4]); break;
                            case 5: selectDialogForGame(games[5]); break;
                            case 6: selectDialogForGame(games[6]); break;
                            case 7: selectDialogForGame(games[7]); break;
                            case 8: selectDialogForGame(games[8]); break;
                            case 9: selectDialogForGame(games[9]); break;
                            case 10: selectDialogForGame(games[10]); break;
                            case 11: selectDialogForGame(games[11]); break;
                        }
                    }
                });

        builder.setNegativeButton("CANCEL", null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void selectDialogForGame(String game){
        int tocke = 0;
        switch(game){
            //display razlika + 30/20/10 + radlci
            case "ena": tocke = settings.getEna(); System.out.println(game+ "was selected"); break;
            case "dva": tocke = settings.getDva(); System.out.println(game+ "was selected"); break;
            case "tri": tocke = settings.getTri(); System.out.println(game+ "was selected"); break;
            //set number of points for each player + radlci
            case "klop": System.out.println(game+ "was selected"); break;
            case "berac": System.out.println(game+ "was selected"); break;
            case "pikolo": System.out.println(game+ "was selected"); break;
            //display razlika + 60/50/40 + radlci
            case "solo ena": tocke = settings.getSoloEna(); System.out.println(game+ "was selected"); break;
            case "solo dva": tocke = settings.getSoloDva(); System.out.println(game+ "was selected"); break;
            case "solo tri": tocke = settings.getSoloTri(); System.out.println(game+ "was selected"); break;
            case "solo brez": tocke = settings.getSoloBrez(); System.out.println(game+ "was selected"); break;

            case "valat": System.out.println(game+ "was selected"); break;
            case "barvni valat": System.out.println(game+ "was selected"); break;
        }
    }

    public void displayDodatki(){
        /*
        TODO: checkbox
        trula 10
        napovedana trula 20
        krali 10
        napovedani krali 20
        špička 25
        napovedana špička 50
        kralj 10
        napovedan kralj 20
         */
    }

    public void initializeUi(){
        Intent intent = getIntent();

        position = intent.getIntExtra("position", -1);
        cardView = MainActivity.getCardViewList().get(position);

        players = cardView.getPlayers();
        mScores = cardView.getScore();
        mSums = cardView.getmSums();
        mRadlci = cardView.getRadlci();

        for(int i=0; i<players.size(); i++) {
            TextView tv = createTextViewPlayer(players.get(i).getName(), i);
            TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(tv, 1, 200, 1,
                    TypedValue.COMPLEX_UNIT_DIP);
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
        textView.setTextColor(ContextCompat.getColor(Scoreboard.this, R.color.colorAccent));
        textView.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT, 1f));
        textView.setGravity(Gravity.CENTER);
        textView.setBackgroundResource(R.drawable.black);

        return textView;
    }

    public void AddRadlcOnClick(View view, Dialog scoreDialog){
        //updating number of radlci for player
        mRadlci[view.getId()] += scoreDialog.RadlcDialog(view, Scoreboard.this);

        //if radlci < 0, radlci = 0
        if(mRadlci[view.getId()] < 0){
            mRadlci[view.getId()] = 0;
        }
        int a = mRadlci[view.getId()];
        radlci.get(view.getId()).removeAllViews(); //removing all
        LinearLayout ll = radlci.get(view.getId());
        for (int i = 0; i < a; i++) { //adding number of current radlci
            ll.addView(createRadlc());
        }

    }

    public LinearLayout createPlayersRadlcLayout(int i){
        LinearLayout ll = new LinearLayout(this);
        ll.setId(i);
        ll.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                50,1f));
        ll.setGravity(Gravity.CENTER_HORIZONTAL);
        ll.setBackgroundResource(R.drawable.black);
        ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog scoreDialog = new Dialog(Scoreboard.this);
                AddRadlcOnClick(view, scoreDialog);
            }
        });
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
        ll = new LinearLayout(this);
        ll.setId(id);
        ll.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT,1f));
        ll.setGravity(Gravity.CENTER_HORIZONTAL);
        ll.setOrientation(LinearLayout.VERTICAL);
        ll.setBackgroundResource(R.drawable.black);
        scores.add(ll);
        return ll;
    }

    public TextView createTextViewScore(int id, String score){
        final TextView textView = new TextView(this);
        textView.setText(score);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24);
        textView.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        textView.setGravity(Gravity.CENTER_HORIZONTAL);
        textView.setTextColor(ContextCompat.getColor(Scoreboard.this, R.color.colorAccent));
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddScoreDialog(view, textView);
            }
        });
        textView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Dialog dialog = new Dialog(Scoreboard.this);
                if(dialog.DeleteScoreDialog(Scoreboard.this, textView)){
                    textView.setVisibility(View.GONE);
                    int score = Integer.parseInt(textView.getText().toString());
                    view = (View)view.getParent();
                    TextView tv = createTextViewScore(view.getId(), mScore);

                    //updating sum at the end
                    mSums[view.getId()] += score * -1;
                    tv = sums.get(view.getId());
                    tv.setText("" + mSums[view.getId()]);
                }

                return true;
            }
        });

        return textView;
    }

    public TextView createTextViewSum(int id){
        TextView textView = new TextView(this);
        textView.setText("0");
        textView.setId(id);
        textView.setTextColor(ContextCompat.getColor(Scoreboard.this, R.color.colorAccent));
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

    public void AddScoreDialog(View view, TextView textView){
        Dialog scoreDialog = new Dialog(Scoreboard.this);
        java.lang.Integer score = scoreDialog.ScoreDialog(view, Scoreboard.this);
        if(score != null) mScore = Integer.toString(score);
        if(mScore != null) {
            if(mScore.trim().isEmpty()){
                Toast.makeText(Scoreboard.this,"Invalid Score", Toast.LENGTH_LONG).show(); }
            else {
                if (textView != null) {
                    view = (View) view.getParent();
                }
                //create textview with score
                TextView tv = createTextViewScore(view.getId(), mScore);
                //add textview to scores (Linear layout)
                scores.get(view.getId()).addView(tv);
                //add score to array of scores in cardview
                mScores.get(view.getId()).add(mScore);

                //updating sum in the cardview
                mSums[view.getId()] += score;

                //get current textview and override with new sum
                tv = sums.get(view.getId());
                tv.setText("" + mSums[view.getId()]);
            }
        }
        score = null;
        mScore = null;

        //scroll down everytime a result is added
        (findViewById(R.id.scrollViewInScoreBoard)).post(new Runnable() {
            public void run() {
                ((ScrollView) findViewById(R.id.scrollViewInScoreBoard)).fullScroll(View.FOCUS_DOWN);
            }
        });
    }

}


//TODO: first time setup - quick tutorial

//TEST: pressing back button and closing app from recycler view
//TEST: closing app from scoreboard
//TEST: tilting the phone