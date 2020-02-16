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

import com.example.tarokanizer.data_classes.CardView;
import com.example.tarokanizer.data_classes.Player;
import com.example.tarokanizer.data_classes.Round;
import com.example.tarokanizer.data_classes.Settings;

import java.util.ArrayList;

public class ScoreboardDefault extends AppCompatActivity {

    LinearLayout linearLayoutPlayers;
    LinearLayout linearLayoutRadlci;
    LinearLayout linearLayoutScore;
    LinearLayout linearLayoutSum;
    LinearLayout ll;
    CardView cardView;
    private Toolbar toolbar;
    Button buttonNew;


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

    int [] pointsPerPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scoreboard);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        settings = Settings.getInstance();

        initViews();
        initOnClickListeners();
        initialize();
    }

    public void initViews(){
        linearLayoutPlayers = findViewById(R.id.names);
        linearLayoutRadlci = findViewById(R.id.radlci);
        linearLayoutScore = findViewById(R.id.score);
        linearLayoutSum = findViewById(R.id.sum);

        buttonNew = findViewById(R.id.button_new);
        Button buttonSettings = findViewById(R.id.button_settings);
        buttonSettings.setVisibility(View.GONE);
    }

    public void initOnClickListeners(){
        buttonNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addRound();
            }
        });
    }

    public void addRound(){
        round = new Round();
        round.setAutomaticMode(false);
        pointsPerPlayer = new int[players.size()];

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Vnesi točke za igralce!");
        final EditText[] fields = new EditText[players.size()];

        ScrollView scroll = new ScrollView(this);

        LinearLayout layout = new LinearLayout(this);
        LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setLayoutParams(parms);
        layout.setGravity(Gravity.CLIP_VERTICAL);
        layout.setPadding(2, 2, 2, 2);

        for(int i=0; i<players.size(); i++) {
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
        }

        scroll.addView(layout);
        builder.setView(scroll);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String input;
                try {
                    for(int k=0; k<fields.length; k++){
                        input = fields[k].getText().toString();
                        if(input.equals("")) input = "0";

                        int score = Integer.parseInt(input);
                        pointsPerPlayer[k] = score;
                    }
                    updateUi(pointsPerPlayer);
                }catch (Exception e){
                    Toast.makeText(ScoreboardDefault.this, "Vnos je bil napačen!", Toast.LENGTH_LONG).show();
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

        AlertDialog dlg = builder.show();
        dlg.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
    }

    public void updateUi(int [] pointsPerPlayer){
        for(int i=0; i<players.size(); i++) {
            //create textview with score
            TextView tv = createTextViewScore(Integer.toString(pointsPerPlayer[i]));
            //add score visually
            scores.get(i).addView(tv);
            //add score to store
            mScores.get(i).add(Integer.toString(pointsPerPlayer[i]));

            //updating sum in the cardview
            mSums[i] += pointsPerPlayer[i];

            //get current textview and override with new sum
            tv = sums.get(i);
            tv.setText("" + mSums[i]);
        }

        //scroll down everytime a result is added
        (findViewById(R.id.scrollViewInScoreBoard)).post(new Runnable() {
            public void run() {
                ((ScrollView) findViewById(R.id.scrollViewInScoreBoard)).fullScroll(View.FOCUS_DOWN);
            }
        });

        round.setPointPerPlayer(pointsPerPlayer);
        rounds.add(round);
    }

    public void initialize(){
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

        loadScores();
        loadRadlci();
        loadSums();
    }

    public void loadScores(){
        TextView tv;
        for(int i=0; i<rounds.size(); i++){
            //klop or manual mode
            if (rounds.get(i).getIdGame() == 3 || !rounds.get(i).isAutomaticMode()) {
                int[] ppp = rounds.get(i).getPointPerPlayer();
                for (int k = 0; k < ppp.length; k++) {
                    tv = createTextViewScore(Integer.toString(ppp[k]));
                    scores.get(k).addView(tv);
                }

            } else { //everything else
                String points = Integer.toString(rounds.get(i).getPoints());
                for (int k = 0; k < players.size(); k++) {
                    if (players.get(k).getId() == rounds.get(i).getIdPlayer() ||
                            players.get(k).getId() == rounds.get(i).getIdRufanPlayer()) {
                        tv = createTextViewScore(points);
                    } else {
                        tv = createTextViewScore("0");
                    }
                    scores.get(k).addView(tv);
                }
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
        textView.setTextColor(ContextCompat.getColor(ScoreboardDefault.this, R.color.brightGray));
        textView.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT, 1f));
        textView.setGravity(Gravity.CENTER);
        textView.setBackgroundResource(R.drawable.border);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog scoreDialog = new Dialog(ScoreboardDefault.this);
                AddRadlcOnClick(view, scoreDialog);
            }
        });

        return textView;
    }

    public void AddRadlcOnClick(View view, Dialog scoreDialog){
        mRadlci[view.getId()] += scoreDialog.RadlcDialog(view, ScoreboardDefault.this);
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
        ll.setBackgroundResource(R.drawable.border);
        ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog scoreDialog = new Dialog(ScoreboardDefault.this);
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
        textView.setTextColor(ContextCompat.getColor(ScoreboardDefault.this, R.color.brightGray));


        return textView;
    }

    public TextView createTextViewSum(int id){
        TextView textView = new TextView(this);
        textView.setText("0");
        textView.setId(id);
        textView.setTextColor(ContextCompat.getColor(ScoreboardDefault.this, R.color.brightGray));
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