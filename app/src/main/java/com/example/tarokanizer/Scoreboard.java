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
import android.text.InputType;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tarokanizer.data_classes.CardView;
import com.example.tarokanizer.data_classes.Player;
import com.example.tarokanizer.data_classes.Round;
import com.example.tarokanizer.data_classes.Settings;

import java.util.ArrayList;

import static java.lang.Math.abs;

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

    Round round;
    ArrayList<Round> rounds;

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
        // create new round
        round = new Round(players.size());

        AlertDialog.Builder builder = new AlertDialog.Builder(Scoreboard.this);
        builder.setTitle("Kdo je igral?");
        String [] mPlayers = new String[players.size()];

        for(int i=0; i<players.size(); i++){
            mPlayers[i] = players.get(i).getName();
        }

        builder.setMultiChoiceItems(mPlayers, round.getChecked(), new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i, boolean b) {

            }
        });

        builder.setPositiveButton("NAPREJ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                displayWhatGameWasPlayed();
            }
        });

        builder.setNegativeButton("PREKLIČI", null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void displayWhatGameWasPlayed(){
        AlertDialog.Builder builder = new AlertDialog.Builder(Scoreboard.this);
        final String [] games = {"ena", "dva", "tri", "klop", "berac", "pikolo", "solo ena", "solo dva", "solo tri", "solo brez", "valat", "barvni valat"};
        builder.setTitle("Katera igra je bila igrana?")
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

        builder.setNegativeButton("PREKLIČI", null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void selectDialogForGame(String game){
        switch(game){
            //display razlika + 30/20/10 + radlci
            case "ena": pointsDialog(settings.getEna()); break;
            case "dva": pointsDialog(settings.getDva()); break;
            case "tri": pointsDialog(settings.getTri()); break;
            //set number of points for each player + radlci
            case "klop": System.out.println(game+ "was selected"); break;
            case "berac": System.out.println(game+ "was selected"); break;
            case "pikolo": System.out.println(game+ "was selected"); break;
            //display razlika + 60/50/40 + radlci
            case "solo ena":  pointsDialog(settings.getSoloEna()); break;
            case "solo dva":  pointsDialog(settings.getSoloDva()); break;
            case "solo tri":  pointsDialog(settings.getSoloTri()); break;
            case "solo brez": pointsDialog(settings.getSoloBrez()); break;

            case "valat": System.out.println(game+ "was selected"); break;
            case "barvni valat": System.out.println(game+ "was selected"); break;
        }
    }

    public void pointsDialog(final int tocke){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Kakšna je bila razlika?");

        final EditText editext = new EditText(this);
        editext.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_SIGNED);
        builder.setView(editext);

        builder.setPositiveButton("NAPREJ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int input=0;
                try {
                    input = Integer.parseInt(editext.getText().toString());
                    if(input < 0) round.setRazlikaPozitivna(false);
                    else round.setRazlikaPozitivna(true);

                    round.setPoints(abs(input) + tocke);
                    Log.d("SUM", Integer.toString(round.getPoints()));
                    displayDodatki();
                }catch (Exception e){
                    Toast.makeText(Scoreboard.this, "Vnos je bil napačen!", Toast.LENGTH_LONG).show();
                    dialog.cancel();
                }
            }
        });
        builder.setNegativeButton("PREKLIČI", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    public void displayDodatki(){
        AlertDialog.Builder builder = new AlertDialog.Builder(Scoreboard.this);
        final String [] addons = {"Trula", "Napovedana Trula", "Kralji", "Napovedani Kralji", "Špička", "Napovedana Špička", "Kralj Zadnja Runda ", "Napovedan Kralj Zadnja Runda"};
        final boolean [] check = {false, false, false, false, false, false, false, false};
        builder.setTitle("Kaj si pobral?")
                .setMultiChoiceItems(addons, check, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i, boolean b) {

                    }
                });

        builder.setPositiveButton("NAPREJ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                int tmp = 0;
                for(int k=0; k<check.length; k++) {
                    if(check[k]){
                        switch (k){
                            case 0: tmp = settings.getTrula(); break;
                            case 1: tmp = settings.getNapovedanaTrula(); break;
                            case 2: tmp = settings.getKralji(); break;
                            case 3: tmp = settings.getNapovedaniKralji(); break;
                            case 4: tmp = settings.getSpicka(); break;
                            case 5: tmp = settings.getNapovedanaSpicka(); break;
                            case 6: tmp = settings.getKralj(); break;
                            case 7: tmp = settings.getNapovedanKralj(); break;
                        }
                    }
                }
                if(round.isRazlikaPozitivna()){
                    round.addPoints(tmp);
                }else{
                    round.addPoints(-tmp);
                }
                displayKontraDodatki();
            }
        });

        builder.setNegativeButton("PREKLIČI", null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void displayKontraDodatki(){
        AlertDialog.Builder builder = new AlertDialog.Builder(Scoreboard.this);
        final String [] addons = {"Trula", "Napovedana Trula", "Kralji", "Napovedani Kralji", "Špička", "Napovedana Špička", "Kralj Zadnja Runda ", "Napovedan Kralj Zadnja Runda"};
        final boolean [] check = {false, false, false, false, false, false, false, false};
        builder.setTitle("Kaj je pobrala nasprotna ekipa?")
                .setMultiChoiceItems(addons, check, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i, boolean b) {

                    }
                });

        builder.setPositiveButton("NAPREJ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                int tmp = 0;
                for(int k=0; k<check.length; k++) {
                    if(check[k]){
                        switch (k){
                            case 0: tmp = -settings.getTrula(); break;
                            case 1: tmp = -settings.getNapovedanaTrula(); break;
                            case 2: tmp = -settings.getKralji(); break;
                            case 3: tmp = -settings.getNapovedaniKralji(); break;
                            case 4: tmp = -settings.getSpicka(); break;
                            case 5: tmp = -settings.getNapovedanaSpicka(); break;
                            case 6: tmp = -settings.getKralj(); break;
                            case 7: tmp = -settings.getNapovedanKralj(); break;
                        }
                    }
                }
                if(round.isRazlikaPozitivna()){
                    round.addPoints(tmp);
                }else{
                    round.addPoints(-tmp);
                }
                finalizeScore();
            }
        });

        builder.setNegativeButton("PREKLIČI", null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    void finalizeScore(){
        if(!round.isRazlikaPozitivna()) round.setPoints(round.getPoints()*-1);

        if(round.getPoints() < 0) round.setWon(false);
        else round.setWon(true);

        if(round.isWon()){
            Log.d("SUMara win", Integer.toString(round.getPoints()));
        }else{
            Log.d("SUMara lose", Integer.toString(round.getPoints()));
        }

        for(int i=0; i<round.getChecked().length; i++){
            if(round.getChecked()[i]){
                //create textview with score
                TextView tv = createTextViewScore(Integer.toString(round.getPoints()));
                //add score visually
                scores.get(i).addView(tv);
                //add score to store
                mScores.get(i).add(Integer.toString(round.getPoints()));

                //updating sum in the cardview
                mSums[i] += round.getPoints();

                //get current textview and override with new sum
                tv = sums.get(i);
                tv.setText("" + mSums[i]);
            }else{ // add blank score so every game is its own row
                //create textview with score
                TextView tv = createTextViewScore("0");
                //add score visually
                scores.get(i).addView(tv);
                //add score to store
                mScores.get(i).add("0");
            }
        }

        //scroll down everytime a result is added
        (findViewById(R.id.scrollViewInScoreBoard)).post(new Runnable() {
            public void run() {
                ((ScrollView) findViewById(R.id.scrollViewInScoreBoard)).fullScroll(View.FOCUS_DOWN);
            }
        });

        rounds.add(round);

        //TODO: first look if there is radlc and count it
        //TODO: then add it if the game is correct

        //TODO: radlci
    }

    public void initializeUi(){
        Intent intent = getIntent();

        position = intent.getIntExtra("position", -1);
        cardView = MainActivity.getCardViewList().get(position);

        players = cardView.getPlayers();
        mScores = cardView.getScore();
        rounds = cardView.getRounds();
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

        loadRounds();
        loadRadlci();
        loadSums();
    }

    public void loadRounds(){
        TextView tv;
        for(int i=0; i<rounds.size(); i++) {
            String points = Integer.toString(rounds.get(i).getPoints());
            for(int k=0; k<rounds.get(i).getChecked().length; k++){
                if(rounds.get(i).getChecked()[k]){
                    tv = createTextViewScore(points);
                } else{
                    tv = createTextViewScore("0");
                }
                scores.get(k).addView(tv);
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
        textView.setTextColor(ContextCompat.getColor(Scoreboard.this, R.color.brightGray));
        textView.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT, 1f));
        textView.setGravity(Gravity.CENTER);
        textView.setBackgroundResource(R.drawable.border);

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
        ll.setBackgroundResource(R.drawable.border);
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
        ll.setBackgroundResource(R.drawable.border);
        scores.add(ll);
        return ll;
    }

    public TextView createTextViewScore(String score){
        final TextView textView = new TextView(this);
        textView.setText(score);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24);
        textView.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        textView.setGravity(Gravity.CENTER_HORIZONTAL);
        textView.setTextColor(ContextCompat.getColor(Scoreboard.this, R.color.brightGray));
        textView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Dialog dialog = new Dialog(Scoreboard.this);
                if(dialog.DeleteScoreDialog(Scoreboard.this, textView)){
                    textView.setVisibility(View.GONE);
                    int score = Integer.parseInt(textView.getText().toString());
                    view = (View)view.getParent();
                    TextView tv = createTextViewScore(mScore);

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
        textView.setTextColor(ContextCompat.getColor(Scoreboard.this, R.color.brightGray));
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30);
        textView.setTypeface(Typeface.DEFAULT_BOLD);
        textView.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT, 1f));
        textView.setGravity(Gravity.CENTER_HORIZONTAL);
        textView.setBackgroundResource(R.drawable.border2);

        sums.add(textView);
        return textView;
    }

}


//TODO: first time setup - quick tutorial

//TODO: settings screen
//TODO: delete whole round (1 row)

//TODO: FIX deleting score on long press

//TEST: pressing back button and closing app from recycler view
//TEST: closing app from scoreboard
//TEST: tilting the phone