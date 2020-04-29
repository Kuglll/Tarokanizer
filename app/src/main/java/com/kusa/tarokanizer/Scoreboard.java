package com.kusa.tarokanizer;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.kusa.tarokanizer.data_classes.CardView;
import com.kusa.tarokanizer.data_classes.Games;
import com.kusa.tarokanizer.data_classes.Kontras;
import com.kusa.tarokanizer.data_classes.Player;
import com.kusa.tarokanizer.data_classes.Round;
import com.kusa.tarokanizer.data_classes.Settings;
import com.kusa.tarokanizer.utils.ComponentFactory;
import com.kusa.tarokanizer.utils.DialogFactory;

import java.util.ArrayList;

import androidx.appcompat.app.AlertDialog;
import androidx.core.widget.TextViewCompat;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

import static java.lang.Math.abs;

public class Scoreboard extends Activity {

    LinearLayout linearLayoutPlayers;
    LinearLayout linearLayoutRadlci;
    LinearLayout linearLayoutScore;
    LinearLayout linearLayoutSum;
    CardView cardView;
    private Button buttonNew;
    private Button buttonSettings;
    private Button buttonFinish;
    Button backButton;

    ArrayList<Player> players;

    int[] mSums = {0,0,0,0,0,0,0,0};

    int[] mRadlci;

    int position;

    Settings settings;

    Round round;
    ArrayList<Round> rounds;

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

        TextView tv = findViewById(R.id.toolbarTitle);
        tv.setText(getIntent().getStringExtra("title"));

        backButton = findViewById(R.id.backButton);
        buttonNew = findViewById(R.id.addButton);
        buttonFinish = findViewById(R.id.finishButton);
        buttonSettings = findViewById(R.id.settingsButton);
        buttonSettings.setVisibility(View.GONE);
    }

    public void initializeOnClickListeners(){
        buttonNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayWhatGameWasPlayed();
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        buttonFinish.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                displayFinishGameDialog();
            }
        });
    }

    public void displayFinishGameDialog(){
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);

        LinearLayout layout = new LinearLayout(this);
        LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setLayoutParams(parms);

        TextView title = new TextView(this);
        title.setPadding(64,48,64,0);
        title.setText("Ali res želite zaključiti igro?");
        title.setTextColor(getResources().getColor(R.color.black));
        title.setTextSize(18);
        layout.addView(title);

        TextView text = new TextView(this);
        text.setPadding(64, 16, 64,0);
        text.setText("To bo samodejno odštelo radlce in razkrilo zmagovalca.");

        layout.addView(text);
        dialog.setView(layout);

        dialog.setPositiveButton("DA", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finishGame();
            }
        });

        dialog.setNegativeButton("PREKLIČI", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        dialog.show();
    }

    public void finishGame(){
        for (int i = 0; i < players.size(); i++) {
            mSums[i] = mSums[i] + mRadlci[i] * settings.getRadlc();
            mRadlci[i] = 0;
        }
        loadSums();
        loadRadlci();
        findWinner();
    }

    public void findWinner() {
        ArrayList<String> winners = new ArrayList<>();
        int max = mSums[0];
        for (int i = 0; i < players.size(); i++) {
            if (mSums[i] > max) {
                max = mSums[i];
                winners.clear();
                winners.add(players.get(i).getName());
            } else if (mSums[i] == max) {
                winners.add(players.get(i).getName());
            }
        }
        displayWinnerDialog(winners, max);
    }

    public void displayWinnerDialog(ArrayList<String> winners, int points) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Imamo zmagovalca!");
        StringBuilder sb = new StringBuilder();

        switch (winners.size()) {
            case 1:
                sb.append("Zmagovalec je: ");
                break;
            case 2:
                sb.append("Zmagovalca sta: ");
                break;
            default:
                sb.append("Zmagovalci so: ");
                break;
        }

        for (String winner : winners) {
            sb.append(winner + ", ");
        }

        sb.setLength(sb.length() - 2);

        LinearLayout layout = new LinearLayout(this);
        LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(0, 0, 0, 64);
        layout.setLayoutParams(parms);

        TextView tv = new TextView(this);
        tv.setPadding(64, 8, 0, 0);
        tv.setText(sb);
        layout.addView(tv);

        tv = new TextView(this);
        tv.setPadding(64, 8, 0, 0);
        tv.setText("S številom točk: " + points);
        layout.addView(tv);

        dialog.setView(layout);
        dialog.show();
    }

    public void displayWhatGameWasPlayed(){
        // create new round
        round = new Round();

        AlertDialog.Builder builder = new AlertDialog.Builder(Scoreboard.this);
        final String[] games = {"Ena", "Dva", "Tri", "Klop", "Berac", "Pikolo", "Solo ena", "Solo dva", "Solo tri", "Solo brez", "Valat",
            "Napovedani valat", "Napovedani barvni valat", "Mond fang", "Renons"};
        builder.setTitle("Katera igra je bila igrana?")
                .setItems(games, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            //display razlika + 30/20/10 + radlci
                            case 0:
                                round.setIdGame(Games.ENA);
                                displayWhoPlayedDialog(settings.getEna());
                                break;
                            case 1:
                                round.setIdGame(Games.DVA);
                                displayWhoPlayedDialog(settings.getDva());
                                break;
                            case 2:
                                round.setIdGame(Games.TRI);
                                displayWhoPlayedDialog(settings.getTri());
                                break;
                            //set number of points for each player + radlci
                            case 3:
                                round.setIdGame(Games.KLOP);
                                displayWhoPlayedAswell();
                                break;
                            case 4:
                                round.setIdGame(Games.BERAC);
                                displayWhoPlayedDialog(0);
                                break;
                            case 5:
                                round.setIdGame(Games.PIKOLO);
                                displayWhoPlayedDialog(0);
                                break;
                            //display razlika + 60/50/40 + radlci
                            case 6:
                                round.setIdGame(Games.SOLO_ENA);
                                displayWhoPlayedDialog(settings.getSoloEna());
                                break;
                            case 7:
                                round.setIdGame(Games.SOLO_DVA);
                                displayWhoPlayedDialog(settings.getSoloDva());
                                break;
                            case 8:
                                round.setIdGame(Games.SOLO_TRI);
                                displayWhoPlayedDialog(settings.getSoloTri());
                                break;
                            case 9:
                                round.setIdGame(Games.SOLO_BREZ);
                                displayWhoPlayedDialog(settings.getSoloBrez());
                                break;
                            case 10:
                                round.setIdGame(Games.VALAT);
                                displayWhoPlayedDialog(settings.getValat());
                                break;
                            case 11:
                                round.setIdGame(Games.NAPOVEDAN_VALAT);
                                displayWhoPlayedDialog(settings.getNapovedanValat());
                                break;
                            case 12:
                                round.setIdGame(Games.BARVNI_VALAT);
                                displayWhoPlayedDialog(settings.getBarvniValat());
                                break;
                            case 13:
                                round.setIdGame(Games.MOND_FANG);
                                displayWhoPlayedDialog(-settings.getMondFang());
                                break;
                            case 14: // renons
                                round.setIdGame(Games.RENONS);
                                displayWhoPlayedDialog(-settings.getRenons());
                                break;
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

        String dialogTitle;
        if (round.getIdGame() == Games.MOND_FANG) {
            dialogTitle = "Kdo je izgubil monda?";
        } else if (round.getIdGame() == Games.RENONS) {
            dialogTitle = "Kdo se je zatalal?";
        } else {
            dialogTitle = "Kdo je igral?";
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(Scoreboard.this);
        builder.setTitle(dialogTitle)
                .setItems(mPlayers, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        round.setIdPlayer(which);

                        if (round.getIdGame() == Games.VALAT || round.getIdGame() == Games.NAPOVEDAN_VALAT) {
                            round.setPoints(tocke);
                            displayWhoGotRufed(0);
                        } else if (round.getIdGame() == Games.BARVNI_VALAT) {
                            round.setPoints(tocke);
                            displayWhoWonDialog(createChecked());
                        } else if (round.getIdGame() == Games.MOND_FANG) {
                            round.setPoints(tocke);
                            round.setRazlikaPozitivna(true);
                            finalizeScoreForMond(createChecked());
                        } else if (round.getIdGame() == Games.RENONS) {
                            round.setPoints(tocke);
                            round.setRazlikaPozitivna(true);
                            finalizeScore(createChecked());
                        } else if (round.getIdGame() == Games.BERAC || round.getIdGame() == Games.PIKOLO) {
                            displayWhoPlayedAswell();
                        } else if (
                            round.getIdGame() == Games.SOLO_BREZ ||
                                round.getIdGame() == Games.SOLO_ENA ||
                                round.getIdGame() == Games.SOLO_DVA ||
                                round.getIdGame() == Games.SOLO_TRI
                        ) {
                            pointsDialog(tocke);
                        } else {
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

    public void finalizeScoreForMond(boolean[] checked) {
        int[] pointsPerPlayer = new int[players.size()];

        for (int i = 0; i < players.size(); i++) {
            if (players.get(i).getId() == round.getIdPlayer()) {
                pointsPerPlayer[i] = round.getPoints();

                LinearLayout ll = (LinearLayout) linearLayoutScore.getChildAt(i);
                ll.addView(ComponentFactory.Companion.createTextViewScore(Integer.toString(round.getPoints()), function1, true));

                //updating sum in the cardview
                mSums[i] += round.getPoints();

                //get current textview and override with new sum
                TextView tv = (TextView) linearLayoutSum.getChildAt(i);
                tv.setText("" + mSums[i]);
            } else { // add blank score so every game is its own row
                pointsPerPlayer[i] = 0;
                LinearLayout ll = (LinearLayout) linearLayoutScore.getChildAt(i);
                ll.addView(ComponentFactory.Companion.createTextViewScore("/", function1, false));
            }
        }

        //scroll down everytime a result is added
        (findViewById(R.id.scrollViewInScoreBoard)).post(new Runnable() {
            public void run() {
                ((ScrollView) findViewById(R.id.scrollViewInScoreBoard)).fullScroll(View.FOCUS_DOWN);
            }
        });

        round.setPointPerPlayer(pointsPerPlayer);
        rounds.add(round);

        checkForObvezenKlop(checked);
    }

    public void displayWhoPlayedAswell(){
        String [] mPlayers = new String[players.size()];
        final boolean [] checked = new boolean[players.size()];
        for(int i=0; i<players.size(); i++){
            mPlayers[i] = players.get(i).getName();
            checked[i] = false;
        }

        //if less than 4 players everyone played along
        if (players.size() <= 4) {
            for (int i = 0; i < players.size(); i++) {
                checked[i] = true;
            }
            if (round.getIdGame() == Games.KLOP) {
                getPointsForPlayers(checked);
            } else if (round.getIdGame() == Games.BERAC || round.getIdGame() == Games.PIKOLO) {
                displayWhoWonDialog(checked);
            }
        } else {

            if (round.getIdGame() == Games.BERAC || round.getIdGame() == Games.PIKOLO) {
                //better ux - automatic check on dialog
                checked[round.getIdPlayer()] = true;
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
                    if (round.getIdGame() == Games.KLOP) {
                        getPointsForPlayers(checked);
                    } else {
                        if (round.getIdGame() == Games.BERAC || round.getIdGame() == Games.PIKOLO) {
                            checked[round.getIdPlayer()] = true;
                        }
                        displayWhoWonDialog(checked);
                    }
                }
            });

            builder.setNegativeButton("PREKLIČI", null);

            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }

    public void getPointsForPlayers(final boolean [] checked){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Vnesi točke za igralce!");

        final EditText [] fields = new EditText[players.size()];
        final int[] pointsPerPlayer = new int[players.size()];

        ScrollView scroll = new ScrollView(this);

        LinearLayout layout = new LinearLayout(this);
        LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                                                            LinearLayout.LayoutParams.WRAP_CONTENT);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setLayoutParams(parms);
        layout.setGravity(Gravity.CLIP_VERTICAL);
        layout.setPadding(2, 2, 2, 2);

        TextView tv = new TextView(this);
        tv.setPadding(64, 8, 0 , 0);
        tv.setText("Hint: Pusti prazno za 0");
        layout.addView(tv);

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
                try {
                    for(int k=0; k<checked.length; k++){
                        if(checked[k]){
                            String input = fields[k].getText().toString();
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
                finalizeScoreForKlop(checked, pointsPerPlayer);
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

    public void finalizeScoreForKlop(boolean[] checked, int[] pointsPerPlayer) {
        for(int i=0; i<players.size(); i++) {
            if (mRadlci[i] > 0) {
                pointsPerPlayer[i] = pointsPerPlayer[i] * 2;
            }
            LinearLayout ll = (LinearLayout) linearLayoutScore.getChildAt(i);
            if (pointsPerPlayer[i] != 0) {
                ll.addView(ComponentFactory.Companion.createTextViewScore(Integer.toString(pointsPerPlayer[i]), function1, false));
            } else {
                ll.addView(ComponentFactory.Companion.createTextViewScore("/", function1, false));
            }

            //updating sum in the cardview
            mSums[i] += pointsPerPlayer[i];

            //get current textview and override with new sum
            TextView tv = (TextView) linearLayoutSum.getChildAt(i);
            tv.setText("" + mSums[i]);
        }

        round.setPointPerPlayer(pointsPerPlayer);
        round.setChecked(checked);

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
                loadRadlci();
            }
        }

        checkForObvezenKlop(checked);
    }

    public void displayWhoWonDialog(final boolean [] checked){
        String[] items = {"Da", "Ne"};
        final AlertDialog.Builder builder = new AlertDialog.Builder(Scoreboard.this);
        builder.setTitle("Ali je igralec zmagal?")
                .setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (round.getIdGame() == Games.VALAT
                            || round.getIdGame() == Games.NAPOVEDAN_VALAT
                            || round.getIdGame() == Games.BARVNI_VALAT) {
                            switch (which) {
                                case 0:
                                    round.setRazlikaPozitivna(true);
                                    break; //da
                                case 1:
                                    round.setRazlikaPozitivna(false);
                                    break; //ne
                            }
                        } else {
                            switch (which) {
                                case 0:
                                    round.setPoints(settings.getBeracPikolo());
                                    break;
                                case 1:
                                    round.setPoints(-settings.getBeracPikolo());
                                    break;
                            }
                            round.setRazlikaPozitivna(true);
                        }
                        checkForKontra(checked);
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
                        if (round.getIdGame() == Games.VALAT || round.getIdGame() == Games.NAPOVEDAN_VALAT) {
                            displayWhoWonDialog(createChecked());
                        } else {
                            pointsDialog(tocke);
                        }
                    }
                });

        builder.setPositiveButton("PRESKOČI", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (round.getIdGame() == Games.VALAT || round.getIdGame() == Games.NAPOVEDAN_VALAT) {
                    displayWhoWonDialog(createChecked());
                } else {
                    pointsDialog(tocke);
                }
            }
        });

        builder.setNegativeButton("PREKLIČI", null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }



    public void pointsDialog(final int tocke){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        LinearLayout layout = new LinearLayout(this);
        LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setLayoutParams(parms);

        TextView title = new TextView(this);
        title.setPadding(64,48,64,0);
        title.setText("Kakšna je bila razlika?");
        title.setTextColor(getResources().getColor(R.color.black));
        title.setTextSize(18);
        layout.addView(title);

        TextView text = new TextView(this);
        text.setPadding(64, 16, 64,0);
        text.setText("Hint: če je igralec izgubil brez razlike vnesi -0");
        layout.addView(text);

        final EditText editext = new EditText(this);
        editext.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_SIGNED);
        editext.requestFocus();
        layout.addView(editext);

        builder.setView(layout);

        builder.setPositiveButton("NAPREJ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String input;
                try {
                    input = (editext.getText().toString());
                    if(input.equals("")) input = "0";
                    if (input.equals("-0")) {
                        round.setRazlikaPozitivna(false);
                        round.setPoints(tocke);
                    } else {
                        int score = Integer.parseInt(input);
                        if (score % 5 != 0) {
                            Toast.makeText(Scoreboard.this, "Razlika je bila napačna! Deljiva mora biti s 5!", Toast.LENGTH_LONG).show();
                            dialog.cancel();
                            return;
                        }
                        if (score < 0) {
                            round.setRazlikaPozitivna(false);
                        } else {
                            round.setRazlikaPozitivna(true);
                        }
                        round.setPoints(abs(score) + tocke);
                    }
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
        final String [] addons = {"Trula", "Napovedana Trula", "Kralji", "Napovedani Kralji", "Pagat Ultimo", "Napovedan Pagat Ultimo",
            "Kralj Ultimo ", "Napovedan Kralj Ultimo"};
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
                            case 0:
                                tmp += settings.getTrula();
                                break;
                            case 1:
                                tmp += settings.getNapovedanaTrula();
                                break;
                            case 2:
                                tmp += settings.getKralji();
                                break;
                            case 3:
                                tmp += settings.getNapovedaniKralji();
                                break;
                            case 4:
                                tmp += settings.getPagatUltimo();
                                break;
                            case 5:
                                tmp += settings.getNapovedanPagatUltimo();
                                break;
                            case 6:
                                tmp += settings.getKraljUltimo();
                                break;
                            case 7:
                                tmp += settings.getNapovedanKraljUltimo();
                                break;
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

        builder.setNeutralButton("PRESKOČI", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                displayKontraDodatki();
            }
        });

        builder.setNegativeButton("PREKLIČI", null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void displayKontraDodatki(){
        AlertDialog.Builder builder = new AlertDialog.Builder(Scoreboard.this);
        final String [] addons = {"Trula", "Napovedana Trula", "Kralji", "Napovedani Kralji", "Pagat Ultimo", "Napovedan Pagat Ultimo",
            "Kralj Ultimo ", "Napovedan Kralj Ultimo"};
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
                            case 0:
                                tmp -= settings.getTrula();
                                break;
                            case 1:
                                tmp -= settings.getNapovedanaTrula();
                                break;
                            case 2:
                                tmp -= settings.getKralji();
                                break;
                            case 3:
                                tmp -= settings.getNapovedaniKralji();
                                break;
                            case 4:
                                tmp -= settings.getPagatUltimo();
                                break;
                            case 5:
                                tmp -= settings.getNapovedanPagatUltimo();
                                break;
                            case 6:
                                tmp -= settings.getKraljUltimo();
                                break;
                            case 7:
                                tmp -= settings.getNapovedanKraljUltimo();
                                break;
                        }
                    }
                }
                if(round.isRazlikaPozitivna()){
                    round.addPoints(tmp);
                }else{
                    round.addPoints(-tmp);
                }
                checkForKontra(createChecked());
            }
        });

        builder.setNeutralButton("PRESKOČI", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                checkForKontra(createChecked());
            }
        });

        builder.setNegativeButton("PREKLIČI", null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public boolean[] createChecked() {
        boolean[] checked = new boolean[players.size()];

        for (int i = 0; i < players.size(); i++) {
            checked[i] = round.getIdPlayer() == i || round.getIdRufanPlayer() == i;
        }

        return checked;
    }

    public void checkForKontra(final boolean[] checked) {
        final String[] kontras = {"Kontra", "Rekontra", "Subkontra", "Mordkontra"};

        AlertDialog.Builder builder = new AlertDialog.Builder(Scoreboard.this);
        builder.setTitle("Ali je bila izrečena kakšna kontra?")
            .setItems(kontras, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which) {
                        case 0:
                            round.setPoints(round.getPoints() * 2);
                            round.setKontra(Kontras.KONTRA);
                            break;
                        case 1:
                            round.setPoints(round.getPoints() * 4);
                            round.setKontra(Kontras.REKONTRA);
                            break;
                        case 2:
                            round.setPoints(round.getPoints() * 8);
                            round.setKontra(Kontras.SUBKONTRA);
                            break;
                        case 3:
                            round.setPoints(round.getPoints() * 16);
                            round.setKontra(Kontras.MORDKONTRA);
                            break;
                    }
                    finalizeScore(checked);
                }
            });

        builder.setPositiveButton("PRESKOČI", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finalizeScore(checked);
            }
        });

        builder.setNegativeButton("PREKLIČI", null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    void finalizeScore(boolean [] checked){
        round.setChecked(checked);

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
            loadRadlci();
        }

        int[] pointsPerPlayer = new int[players.size()];

        for(int i = 0; i<players.size(); i++){
            if(players.get(i).getId() == round.getIdPlayer() ||
                players.get(i).getId() == round.getIdRufanPlayer()){
                pointsPerPlayer[i] = round.getPoints();

                LinearLayout ll = (LinearLayout) linearLayoutScore.getChildAt(i);
                if (players.get(i).getId() == round.getIdPlayer()) {
                    ll.addView(ComponentFactory.Companion.createTextViewScore(Integer.toString(round.getPoints()), function1, true));
                } else {
                    ll.addView(ComponentFactory.Companion.createTextViewScore(Integer.toString(round.getPoints()), function1, false));
                }

                //updating sum in the cardview
                mSums[i] += round.getPoints();

                //get current textview and override with new sum
                TextView tv = (TextView) linearLayoutSum.getChildAt(i);
                tv.setText("" + mSums[i]);
            }else{ // add blank score so every game is its own row
                pointsPerPlayer[i] = 0;
                LinearLayout ll = (LinearLayout) linearLayoutScore.getChildAt(i);
                ll.addView(ComponentFactory.Companion.createTextViewScore("/", function1, false));
            }
        }

        //scroll down everytime a result is added
        (findViewById(R.id.scrollViewInScoreBoard)).post(new Runnable() {
            public void run() {
                ((ScrollView) findViewById(R.id.scrollViewInScoreBoard)).fullScroll(View.FOCUS_DOWN);
            }
        });

        round.setPointPerPlayer(pointsPerPlayer);
        rounds.add(round);

        //add radlc if the game is correct
        if (round.getIdGame() == Games.KLOP || round.getIdGame() == Games.BERAC || round.getIdGame() == Games.PIKOLO) {
            for(int i=0; i<checked.length; i++){
                if(checked[i] || round.getIdPlayer() == i){
                    //updating number of radlci for player
                    mRadlci[i] = mRadlci[i] + 1;
                    loadRadlci();
                }
            }
        }

        checkForObvezenKlop(checked);
    }

    public void checkForObvezenKlop(boolean[] checked) {
        for (int i = 0; i < players.size(); i++) {
            if (mSums[i] == 0 && checked[i]
                && round.getIdGame() != Games.KLOP
                && round.getIdGame() != Games.BERAC
                && round.getIdGame() != Games.PIKOLO
            ) {
                DialogFactory.Companion.displayObvezenKlopDialog(this);
                break;
            }
        }
    }

    public void initializeUi(){
        Intent intent = getIntent();

        position = intent.getIntExtra("position", -1);

        cardView = MainActivity.Companion.getCardViewList().get(position);

        players = cardView.getPlayers();
        rounds = cardView.getRounds();
        mSums = cardView.getmSums();
        mRadlci = cardView.getRadlci();

        for(int i=0; i<players.size(); i++) {
            TextView tv = ComponentFactory.Companion.createTextViewPlayer(players.get(i).getName(), true, null);
            TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(tv, 1, 200, 1,
                TypedValue.COMPLEX_UNIT_DIP);
            linearLayoutPlayers.addView(tv);


            linearLayoutScore.addView(ComponentFactory.Companion.createScoreLayout(i));
            linearLayoutSum.addView(ComponentFactory.Companion.createTextViewSum(i));
            linearLayoutRadlci.addView(ComponentFactory.Companion.createPlayersRadlcLayout(i, true, null));
        }

        loadRounds();
        loadRadlci();
        loadSums();
    }

    public void loadRounds(){
        for(int i=0; i<rounds.size(); i++) {
            int[] ppp = rounds.get(i).getPointPerPlayer();
            for (int k = 0; k < ppp.length; k++) {
                LinearLayout ll = (LinearLayout) linearLayoutScore.getChildAt(k);
                if (ppp[k] == 0) {
                    ll.addView(ComponentFactory.Companion.createTextViewScore("/", function1, false));
                } else {
                    if (players.get(k).getId() == rounds.get(i).getIdPlayer()) {
                        ll.addView(ComponentFactory.Companion.createTextViewScore(Integer.toString(ppp[k]), function1, true));
                    } else {
                        ll.addView(ComponentFactory.Companion.createTextViewScore(Integer.toString(ppp[k]), function1, false));
                    }
                }
            }
        }
    }

    public void deleteRound(final int index){
        AlertDialog.Builder builder = new AlertDialog.Builder(Scoreboard.this);
        builder.setTitle("Runda številka " + (index + 1));

        LinearLayout layout = new LinearLayout(this);
        LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setLayoutParams(parms);
        layout.setPadding(2, 2, 2, 2);

        if (rounds.get(index).isAutomaticMode()) {
            TextView text = new TextView(this);
            text.setText("Igra: " + resolveGameId(rounds.get(index).getIdGame()));
            text.setPadding(64, 8, 0, 0);
            layout.addView(text);

            if (rounds.get(index).getIdPlayer() != -1) {
                text = new TextView(this);
                text.setText("Kdo je igral: " + players.get(rounds.get(index).getIdPlayer()).getName());
                text.setPadding(64, 8, 0, 0);
                layout.addView(text);
            }

            if (rounds.get(index).getIdRufanPlayer() != -1) {
                text = new TextView(this);
                text.setText("Kdo je bil rufan: " + players.get(rounds.get(index).getIdRufanPlayer()).getName());
                text.setPadding(64, 8, 0, 0);
                layout.addView(text);
            }

            if (rounds.get(index).getIdGame() == Games.KLOP ||
                rounds.get(index).getIdGame() == Games.BERAC ||
                rounds.get(index).getIdGame() == Games.PIKOLO) {
                boolean[] checked = rounds.get(index).getChecked();
                text = new TextView(this);
                text.setPadding(64, 8, 0, 0);
                StringBuilder whoPlayedAswell = new StringBuilder("Kdo je bil udeležen: ");
                for (int i = 0; i < checked.length; i++) {
                    if (checked[i] && rounds.get(index).getIdPlayer() != i) {
                        whoPlayedAswell.append(players.get(i).getName() + ", ");
                    }
                }
                whoPlayedAswell.setLength(whoPlayedAswell.length() - 2);
                text.setText(whoPlayedAswell);
                layout.addView(text);
            }

            if (rounds.get(index).getKontra() != -1) {
                text = new TextView(this);
                String kontra;
                switch (rounds.get(index).getKontra()) {
                    case Kontras.KONTRA:
                        kontra = "Kontra";
                        break;
                    case Kontras.REKONTRA:
                        kontra = "Rekontra";
                        break;
                    case Kontras.SUBKONTRA:
                        kontra = "Subkontra";
                        break;
                    case Kontras.MORDKONTRA:
                        kontra = "Mordkontra";
                        break;
                    default:
                        kontra = "";
                }
                text.setText("Kontra: " + kontra);
                text.setPadding(64, 8, 0, 0);
                layout.addView(text);
            }
        }
        builder.setView(layout);

        builder.setPositiveButton("IZBRIŠI", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                repairDataAfterRoundDeletion(rounds.get(index));
                rounds.remove(index);
                cleanRounds();
                loadRounds();
            }
        });

        builder.setNegativeButton("PREKLIČI", null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public String resolveGameId(int id) {

        switch (id) {
            case Games.ENA:
                return "Ena";
            case Games.DVA:
                return "Dva";
            case Games.TRI:
                return "Tri";
            case Games.KLOP:
                return "Klop";
            case Games.BERAC:
                return "Berač";
            case Games.PIKOLO:
                return "Pikolo";
            case Games.SOLO_ENA:
                return "Solo ena";
            case Games.SOLO_DVA:
                return "Solo dva";
            case Games.SOLO_TRI:
                return "Solo tri";
            case Games.SOLO_BREZ:
                return "Solo brez";
            case Games.VALAT:
                return "Valat";
            case Games.NAPOVEDAN_VALAT:
                return "Napovedani valat";
            case Games.BARVNI_VALAT:
                return "Napovedani barvni valat";
            case Games.MOND_FANG:
                return "Mond fang";
            case Games.RENONS:
                return "Renons";
            default:
                return null;
        }
    }

    public void repairDataAfterRoundDeletion(Round round){
        //radlci correction
        if (round.getIdGame() == Games.KLOP || round.getIdGame() == Games.BERAC || round.getIdGame() == Games.PIKOLO) {
            for (int i = 0; i < round.getChecked().length; i++) {
                if (round.getChecked()[i] || round.getIdPlayer() == i) {
                    mRadlci[i] = mRadlci[i] - 1;
                }
            }
        }
        loadRadlci();

        //sums correction
        for (int i = 0; i < players.size(); i++) {
            mSums[i] -= round.getPointPerPlayer()[i];
        }
        loadSums();
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