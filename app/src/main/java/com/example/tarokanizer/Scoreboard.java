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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scoreboard);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        linearLayoutPlayers = findViewById(R.id.names);
        linearLayoutRadlci = findViewById(R.id.radlci);
        linearLayoutScore = findViewById(R.id.score);
        linearLayoutSum = findViewById(R.id.sum);

        initializeUi();
        initializeOnClickListeners();
    }

    public void initializeOnClickListeners(){
        buttonNew = findViewById(R.id.button_new);
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
        final boolean [] checked = new boolean[players.size()];

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
                displayWhatGameWasPlayed(checked);
                for(int k=0; k<checked.length; k++) {
                    Log.d("CHECKED", "" + checked[k]);
                }
            }
        });

        builder.setNegativeButton("CANCEL", null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void displayWhatGameWasPlayed(final boolean [] checked){
        AlertDialog.Builder builder = new AlertDialog.Builder(Scoreboard.this);
        final String [] games = {"ena", "dva", "tri", "klop", "berac", "pikolo", "solo ena", "solo dva", "solo tri", "solo brez", "valat", "barvni valat"};
        builder.setTitle("Select the game that was played")
                .setItems(games, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case 0: selectDialogForGame(checked, games[0]); break;
                            case 1: selectDialogForGame(checked, games[1]); break;
                            case 2: selectDialogForGame(checked, games[2]); break;
                            case 3: selectDialogForGame(checked, games[3]); break;
                            case 4: selectDialogForGame(checked, games[4]); break;
                            case 5: selectDialogForGame(checked, games[5]); break;
                            case 6: selectDialogForGame(checked, games[6]); break;
                            case 7: selectDialogForGame(checked, games[7]); break;
                            case 8: selectDialogForGame(checked, games[8]); break;
                            case 9: selectDialogForGame(checked, games[9]); break;
                            case 10: selectDialogForGame(checked, games[10]); break;
                            case 11: selectDialogForGame(checked, games[11]); break;
                        }
                    }
                });

        builder.setNegativeButton("CANCEL", null);

        AlertDialog dialog = builder.create();
        dialog.show();
        //klop 70, berac 70, pikolo 70 - radlci
        //ena 30, dva 20, tri 10
        //solo tri 40, solo dva 50, solo ena 60, solo brez 80
    }

    public void selectDialogForGame(boolean [] checked, String game){
        switch(game){
            //display razlika + 30/20/10 + radlci
            case "ena": System.out.println(game+ "was selected"); break;
            case "dva": System.out.println(game+ "was selected"); break;
            case "tri": System.out.println(game+ "was selected"); break;
            //set number of points for each player + radlci
            case "klop": System.out.println(game+ "was selected"); break;
            case "berac": System.out.println(game+ "was selected"); break;
            case "pikolo": System.out.println(game+ "was selected"); break;
            //display razlika + 60/50/40 + radlci
            case "solo ena": System.out.println(game+ "was selected"); break;
            case "solo dva": System.out.println(game+ "was selected"); break;
            case "solo tri": System.out.println(game+ "was selected"); break;
            case "solo brez": System.out.println(game+ "was selected"); break;

            case "valat": System.out.println(game+ "was selected"); break;
            case "barvni valat": System.out.println(game+ "was selected"); break;

        }
    }

    public void displayDodatki(){
        /*
        Trula Ekipa pobere škisa, monda ter pagata. Nenapovedana 10, napovedana 20 točk.
        Kralji Ekipa pobere vse štiri kralje. Nenapovedani 10, napovedani 20 točk.
        Kralj ultimo V zadnjem štihu ekipa nosilca igre pobere klicanega kralja, možno le pri štirih igralcih. Nenapovedan 10, napovedan 20 točk.
        Pagat ultimo Zadnji štih pobere pagat. Nenapovedan 25, napovedan 50 točk. Če štih s pagatom pobere tvoj partner, je pagat ultimo izgubljen!
        Barvni valat Če je prva karta na mizi barva, je katera koli karta te barve močnejša od taroka. Nosilec napovedi mora pobrati vse karte, možno napovedati le, ko je nosilec sam v ekipi. Možen je le napovedani barvni valat, ki je vreden 125 točk.
        Valat Ekipa pobere vse štihe. Nenapovedan je vreden 250, napovedan 500 točk.
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
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog scoreDialog = new Dialog(Scoreboard.this);
                AddRadlcOnClick(view, scoreDialog);
            }
        });

        return textView;
    }

    public void AddRadlcOnClick(View view, Dialog scoreDialog){
        mRadlci[view.getId()] += scoreDialog.RadlcDialog(view, Scoreboard.this);
        if(mRadlci[view.getId()] < 0){mRadlci[view.getId()] = 0;}
        Integer a;
        a = mRadlci[view.getId()];
        if(a != null || a != 0) {
            radlci.get(view.getId()).removeAllViews();
            LinearLayout ll = radlci.get(view.getId());
            for (int i = 0; i < a; i++) {
                ll.addView(createRadlc());
            }
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
        ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddScoreDialog(view, null); //If not null that means we clicked the textview, if null we clicked linearlayout. They use different views!
            }
        });

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