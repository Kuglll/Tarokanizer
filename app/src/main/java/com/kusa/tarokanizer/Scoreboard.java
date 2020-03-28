package com.kusa.tarokanizer;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.widget.TextViewCompat;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.InputType;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.kusa.tarokanizer.data_classes.CardView;
import com.kusa.tarokanizer.data_classes.Settings;
import com.kusa.tarokanizer.data_classes.Player;
import com.kusa.tarokanizer.data_classes.Round;
import com.kusa.tarokanizer.utils.ComponentFactory;

import java.util.ArrayList;
import java.util.Arrays;

import static java.lang.Math.abs;

public class Scoreboard extends AppCompatActivity {

    LinearLayout linearLayoutPlayers;
    LinearLayout linearLayoutRadlci;
    LinearLayout linearLayoutScore;
    LinearLayout linearLayoutSum;
    CardView cardView;
    private Toolbar toolbar;
    private Button buttonNew;
    private Button buttonSettings;

    ArrayList<Player> players;

    ArrayList<ArrayList<String>> mScores;

    int[] mSums = {0,0,0,0,0,0,0,0};

    int[] mRadlci;

    String mScore;
    int position;

    Settings settings;

    Round round;
    ArrayList<Round> rounds;

    //klop variable
    int [] pointsPerPlayer;

    // because there is no lambda expressions in java 7
    Function1 function1 = new Function1<Integer, Unit>() {
        @Override
        public Unit invoke(Integer integer) {
            deleteRound(integer);
            return null;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scoreboard);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        settings = Settings.getInstance();

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
        buttonSettings = findViewById(R.id.button_settings);
        buttonSettings.setVisibility(View.GONE);
    }

    public void initializeOnClickListeners(){
        buttonNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayWhatGameWasPlayed();
            }
        });
    }

    public void displayWhatGameWasPlayed(){
        // create new round
        round = new Round();

        AlertDialog.Builder builder = new AlertDialog.Builder(Scoreboard.this);
        final String [] games = {"Ena", "Dva", "Tri", "Klop", "Berac", "Pikolo", "Solo ena", "Solo dva", "Solo tri", "Solo brez", "Valat", "Barvni valat"};
        builder.setTitle("Katera igra je bila igrana?")
                .setItems(games, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            //display razlika + 30/20/10 + radlci
                            case 0: displayWhoPlayedDialog(settings.getEna()); round.setIdGame(0); break;
                            case 1: displayWhoPlayedDialog(settings.getDva()); round.setIdGame(1); break;
                            case 2: displayWhoPlayedDialog(settings.getTri()); round.setIdGame(2); break;
                            //set number of points for each player + radlci
                            case 3: displayWhoPlayedAswell(); round.setIdGame(3); break; //klop
                            case 4: displayWhoPlayedDialog(0); round.setIdGame(4); break; //berac
                            case 5: displayWhoPlayedDialog(0); round.setIdGame(5); break; //pikolo
                            //display razlika + 60/50/40 + radlci
                            case 6: displayWhoPlayedDialog(settings.getSoloEna()); round.setIdGame(6); break;
                            case 7: displayWhoPlayedDialog(settings.getSoloDva());  round.setIdGame(7); break;
                            case 8: displayWhoPlayedDialog(settings.getSoloTri()); round.setIdGame(8); break;
                            case 9: displayWhoPlayedDialog(settings.getSoloBrez()); round.setIdGame(9); break;
                            case 10: System.out.println(games[10]+ "was selected"); round.setIdGame(10); break;
                            case 11: System.out.println(games[11]+ "was selected"); round.setIdGame(11); break;
                        }
                    }
                });

        builder.setNegativeButton("PREKLIČI", null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void displayWhoPlayedDialog(final int tocke){
        String [] mPlayers = new String[players.size()];
        for(int i=0; i<players.size(); i++){
            mPlayers[i] = players.get(i).getName();
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(Scoreboard.this);
        builder.setTitle("Kdo je igral?")
                .setItems(mPlayers, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        round.setIdPlayer(which);

                        //berac ali pikolo
                        if(round.getIdGame() == 4 || round.getIdGame() == 5){
                            displayWhoPlayedAswell();
                        }else {
                            // if there is less or equal than 3 player, no one was rufed
                            if (players.size() <= 3) {
                                pointsDialog(tocke);
                            } else {
                                displayWhoGotRufed(tocke);
                            }
                        }
                    }
                });

        builder.setNegativeButton("PREKLIČI", null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void displayWhoPlayedAswell(){
        String [] mPlayers = new String[players.size()];
        final boolean [] checked = new boolean[players.size()];
        for(int i=0; i<players.size(); i++){
            mPlayers[i] = players.get(i).getName();
            checked[i] = false;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(Scoreboard.this);
        builder.setTitle("Kdo je bil udeležen v igri?")
                .setMultiChoiceItems(mPlayers, checked, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i, boolean b) {

                    }
                });

        builder.setPositiveButton("NAPREJ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //klop
                if(round.getIdGame() == 3){
                    pointsPerPlayer = new int[players.size()];
                    getPointsForPlayers(checked);
                }else{
                    displayWhoWonDialog(checked);
                }
            }
        });

        builder.setNegativeButton("PREKLIČI", null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void getPointsForPlayers(final boolean [] checked){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Vnesi točke za igralce!");
        final EditText [] fields = new EditText[players.size()];

        ScrollView scroll = new ScrollView(this);

        LinearLayout layout = new LinearLayout(this);
        LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                                                            LinearLayout.LayoutParams.WRAP_CONTENT);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setLayoutParams(parms);
        layout.setGravity(Gravity.CLIP_VERTICAL);
        layout.setPadding(2, 2, 2, 2);

        for(int i=0; i<players.size(); i++) {
            if(checked[i]) {
                TextView text = new TextView(this);
                text.setText(players.get(i).getName());
                text.setPadding(40, 40, 40, 40);
                text.setGravity(Gravity.CENTER);
                text.setTextSize(20);

                EditText editext = new EditText(this);
                editext.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_SIGNED);

                fields[i] = editext;
                layout.addView(text);
                layout.addView(editext);
            } else{
                fields[i] = null;
            }
        }

        scroll.addView(layout);
        builder.setView(scroll);

        builder.setPositiveButton("NAPREJ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String input;
                try {
                    for(int k=0; k<checked.length; k++){
                        if(checked[k]){
                            input = fields[k].getText().toString();
                            if(input.equals("")) input = "0";

                            int score = Integer.parseInt(input);
                            pointsPerPlayer[k] = score;
                        }else {
                            pointsPerPlayer[k] = 0;
                        }
                    }

                }catch (Exception e){
                    Toast.makeText(Scoreboard.this, "Vnos je bil napačen!", Toast.LENGTH_LONG).show();
                    dialog.cancel();
                }
                finalizeScoreForKlop(checked);
            }
        });
        builder.setNegativeButton("PREKLIČI", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        AlertDialog dlg = builder.show();
        dlg.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
    }

    public void finalizeScoreForKlop(boolean [] checked){
        for(int i=0; i<players.size(); i++) {
            if (mRadlci[i] > 0) {
                pointsPerPlayer[i] = pointsPerPlayer[i] * 2;
            }
            LinearLayout ll = (LinearLayout) linearLayoutScore.getChildAt(i);
            ll.addView(ComponentFactory.Companion.createTextViewScore(Integer.toString(pointsPerPlayer[i]), function1));
            //add score to store
            mScores.get(i).add(Integer.toString(pointsPerPlayer[i]));

            //updating sum in the cardview
            mSums[i] += pointsPerPlayer[i];

            //get current textview and override with new sum
            TextView tv = (TextView) linearLayoutSum.getChildAt(i);
            tv.setText("" + mSums[i]);
        }

        round.setPointPerPlayer(pointsPerPlayer);

        //scroll down everytime a result is added
        (findViewById(R.id.scrollViewInScoreBoard)).post(new Runnable() {
            public void run() {
                ((ScrollView) findViewById(R.id.scrollViewInScoreBoard)).fullScroll(View.FOCUS_DOWN);
            }
        });

        rounds.add(round);

        for(int i=0; i<checked.length; i++){
            if(checked[i]){
                //updating number of radlci for player
                mRadlci[i] = mRadlci[i] + 1;
                updateRadlciLayout(i, mRadlci);
            }
        }
    }

    public void displayWhoWonDialog(final boolean [] checked){
        String items [] = {"Da", "Ne"};
        final AlertDialog.Builder builder = new AlertDialog.Builder(Scoreboard.this);
        builder.setTitle("Ali je igralec zmagal?")
                .setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case 0: round.setPoints(70); break;
                            case 1: round.setPoints(-70); break;
                        }
                        round.setRazlikaPozitivna(true);
                        finalizeScore(checked);
                    }
                });

        builder.setNegativeButton("PREKLIČI", null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void displayWhoGotRufed(final int tocke){
        String [] mPlayers = new String[players.size()];
        for(int i=0; i<players.size(); i++){
            mPlayers[i] = players.get(i).getName();
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(Scoreboard.this);
        builder.setTitle("Kdo je bil rufan?")
                .setItems(mPlayers, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        round.setIdRufanPlayer(which);
                        pointsDialog(tocke);
                    }
                });

        builder.setPositiveButton("Naprej", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                pointsDialog(tocke);
            }
        });

        builder.setNegativeButton("PREKLIČI", null);

        AlertDialog dialog = builder.create();
        dialog.show();
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
                String input;
                try {
                    input = (editext.getText().toString());
                    if(input.equals("")) input = "0";

                    int score = Integer.parseInt(input);
                    if(score < 0) round.setRazlikaPozitivna(false);
                    else round.setRazlikaPozitivna(true);

                    round.setPoints(abs(score) + tocke);
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
        final String [] addons = {"Trula", "Napovedana Trula", "Kralji", "Napovedani Kralji", "Pagat", "Pagat Ultimo", "Kralj Zadnja Runda ", "Kralj Ultimo"};
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
        final String [] addons = {"Trula", "Napovedana Trula", "Kralji", "Napovedani Kralji", "Pagat", "Pagat Ultimo", "Kralj Zadnja Runda ", "Kralj Ultimo"};
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
                finalizeScore(new boolean[0]);
            }
        });

        builder.setNegativeButton("PREKLIČI", null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    void finalizeScore(boolean [] checked){
        //if difference < 0, multiply score with -1
        if(!round.isRazlikaPozitivna()) round.setPoints(round.getPoints()*-1);

        //radlci check
        if(mRadlci[round.getIdPlayer()] > 0){
            round.setPoints(round.getPoints()*2);
        }

        // win status
        if(round.getPoints() < 0) round.setWon(false);
        else round.setWon(true);

        // remove radlc and update ui
        if(round.isWon() && mRadlci[round.getIdPlayer()] > 0){
            mRadlci[round.getIdPlayer()]--;
            updateRadlciLayout(round.getIdPlayer(), mRadlci);
        }

        for(int i = 0; i<players.size(); i++){
            if(players.get(i).getId() == round.getIdPlayer() ||
                players.get(i).getId() == round.getIdRufanPlayer()){

                LinearLayout ll = (LinearLayout) linearLayoutScore.getChildAt(i);
                ll.addView(ComponentFactory.Companion.createTextViewScore(Integer.toString(round.getPoints()), function1));
                //add score to store
                mScores.get(i).add(Integer.toString(round.getPoints()));

                //updating sum in the cardview
                mSums[i] += round.getPoints();

                //get current textview and override with new sum
                TextView tv = (TextView) linearLayoutSum.getChildAt(i);
                tv.setText("" + mSums[i]);
            }else{ // add blank score so every game is its own row
                LinearLayout ll = (LinearLayout) linearLayoutScore.getChildAt(i);
                ll.addView(ComponentFactory.Companion.createTextViewScore("/", function1));

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

        //add radlc if the game is correct
        if(Arrays.asList("3","4","5").contains(Integer.toString(round.getIdGame()))){
            for(int i=0; i<checked.length; i++){
                if(checked[i] || round.getIdPlayer() == i){
                    //updating number of radlci for player
                    mRadlci[i] = mRadlci[i] + 1;
                    updateRadlciLayout(i, mRadlci);
                }
            }
        }
    }

    public void updateRadlciLayout(int id, int [] mRadlci){
        LinearLayout ll = (LinearLayout) linearLayoutRadlci.getChildAt(id);
        ll.removeAllViews();
        for (int k = 0; k < mRadlci[id]; k++) { //adding number of current radlci
            ll.addView(ComponentFactory.Companion.createRadlc());
        }
    }

    public void initializeUi(){
        Intent intent = getIntent();

        position = intent.getIntExtra("position", -1);

        cardView = MainActivity.Companion.getCardViewList().get(position);

        players = cardView.getPlayers();
        mScores = cardView.getScore();
        rounds = cardView.getRounds();
        mSums = cardView.getmSums();
        mRadlci = cardView.getRadlci();

        for(int i=0; i<players.size(); i++) {
            TextView tv = ComponentFactory.Companion.createTextViewPlayer(players.get(i).getName(), i);
            linearLayoutPlayers.addView(tv);

            linearLayoutScore.addView(ComponentFactory.Companion.createScoreLayout(i));
            linearLayoutSum.addView(ComponentFactory.Companion.createTextViewSum(i));
            linearLayoutRadlci.addView(ComponentFactory.Companion.createPlayersRadlcLayout(i));
        }

        loadRounds();
        loadRadlci();
        loadSums();
    }

    public void loadRounds(){
        for(int i=0; i<rounds.size(); i++) {
            //klop or manual mode
            if (rounds.get(i).getIdGame() == 3 || !rounds.get(i).isAutomaticMode()) {
                int[] ppp = rounds.get(i).getPointPerPlayer();
                for (int k = 0; k < ppp.length; k++) {
                    LinearLayout ll = (LinearLayout) linearLayoutScore.getChildAt(i);
                    ll.addView(ComponentFactory.Companion.createTextViewScore(Integer.toString(ppp[k]), function1));
                }
            } else { //everything else
                String points = Integer.toString(rounds.get(i).getPoints());
                for (int k = 0; k < players.size(); k++) {
                    TextView tv;
                    if (players.get(k).getId() == rounds.get(i).getIdPlayer() ||
                            players.get(k).getId() == rounds.get(i).getIdRufanPlayer()) {
                        tv = ComponentFactory.Companion.createTextViewScore(points, function1);
                    } else {
                        tv = ComponentFactory.Companion.createTextViewScore("/", function1);
                    }
                    LinearLayout ll = (LinearLayout) linearLayoutScore.getChildAt(k);
                    ll.addView(tv);
                }
            }
        }
    }

    public void deleteRound(final int index){
        AlertDialog.Builder builder = new AlertDialog.Builder(Scoreboard.this);
        builder.setTitle("Ali želite izbrisati rundo?");

        builder.setPositiveButton("DA", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                repairDataAfterRoundDeletion(rounds.get(index));
                rounds.remove(index);
                cleanRounds();
                loadRounds();
                loadRadlci();
                loadSums();
            }
        });

        builder.setNegativeButton("NE", null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void repairDataAfterRoundDeletion(Round round){
        //radlci correction
        /*if(Arrays.asList("3","4","5").contains(Integer.toString(round.getIdGame()))){
            for(int i=0; i<checked.length; i++){
                if(checked[i] || round.getIdPlayer() == i){
                    //updating number of radlci for player
                    mRadlci[i] = mRadlci[i] + 1;
                    updateRadlciLayout(i, mRadlci);
                }
            }
        }*/

        //sums correction

    }

    public void cleanRounds(){
        for(int i=0; i<players.size(); i++){
            LinearLayout ll = (LinearLayout) linearLayoutScore.getChildAt(i);
            ll.removeAllViews();
        }
    }

    public void loadRadlci(){
        for(int i=0; i<players.size(); i++){
            LinearLayout ll = (LinearLayout) linearLayoutRadlci.getChildAt(i);
            ll.removeAllViews();
            for(int k=0; k<mRadlci[i]; k++){
                ll.addView(ComponentFactory.Companion.createRadlc());
            }
        }
    }

    public void loadSums(){
        for(int i=0; i<players.size(); i++){
            TextView tv = (TextView) linearLayoutSum.getChildAt(i);
            tv.setText(""+mSums[i]);
        }
    }

}
//TODO: first time setup - quick tutorial