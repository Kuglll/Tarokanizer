package com.kusa.tarokanizer;

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
import com.kusa.tarokanizer.data_classes.Player;
import com.kusa.tarokanizer.data_classes.Round;
import com.kusa.tarokanizer.utils.ComponentFactory;

import java.util.ArrayList;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.TextViewCompat;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class ScoreboardDefault extends AppCompatActivity {

    LinearLayout linearLayoutPlayers;
    LinearLayout linearLayoutRadlci;
    LinearLayout linearLayoutScore;
    LinearLayout linearLayoutSum;
    CardView cardView;
    Button buttonNew;
    Button backButton;

    ArrayList<Player> players;

    int[] mSums = {0, 0, 0, 0, 0, 0, 0, 0};
    int[] mRadlci;

    int position;

    Round round;
    ArrayList<Round> rounds;

    int[] pointsPerPlayer;

    // because there is no lambda expressions in java 7
    Function1 function1 = new Function1<Integer, Unit>() {
        @Override
        public Unit invoke(Integer integer) {
            deleteRound(integer);
            return null;
        }
    };

    Function1 function2 = new Function1<Integer, Unit>() {
        @Override
        public Unit invoke(Integer integer) {
            addRadlcOnClick(integer);
            return null;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scoreboard);

        initViews();
        initOnClickListeners();
        initialize();
    }

    public void initViews() {
        linearLayoutPlayers = findViewById(R.id.names);
        linearLayoutRadlci = findViewById(R.id.radlci);
        linearLayoutScore = findViewById(R.id.score);
        linearLayoutSum = findViewById(R.id.sum);

        TextView tv = findViewById(R.id.toolbarTitle);
        tv.setText(getIntent().getStringExtra("title"));

        backButton = findViewById(R.id.backButton);
        buttonNew = findViewById(R.id.addButton);
        Button buttonSettings = findViewById(R.id.settingsButton);
        buttonSettings.setVisibility(View.GONE);
        buttonSettings = findViewById(R.id.finishButton);
        buttonSettings.setVisibility(View.GONE);
    }

    public void initOnClickListeners() {
        buttonNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addRound();
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    public void addRound() {
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

        TextView tv = new TextView(this);
        tv.setPadding(64, 8, 0 , 0);
        tv.setText("Hint: Pusti prazno za 0");
        layout.addView(tv);

        for (int i = 0; i < players.size(); i++) {
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
                    for (int k = 0; k < fields.length; k++) {
                        input = fields[k].getText().toString();
                        if (input.equals("")) {
                            input = "0";
                        }

                        int score = Integer.parseInt(input);
                        pointsPerPlayer[k] = score;
                    }
                    updateUi(pointsPerPlayer);
                } catch (Exception e) {
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

    public void updateUi(int[] pointsPerPlayer) {
        for (int i = 0; i < players.size(); i++) {
            //create textview with score
            TextView tv;
            if (pointsPerPlayer[i] == 0) {
                tv = ComponentFactory.Companion.createTextViewScore("/", function1, false);
            } else {
                tv = ComponentFactory.Companion.createTextViewScore(Integer.toString(pointsPerPlayer[i]), function1, false);
            }
            //add score visually
            LinearLayout ll = (LinearLayout) linearLayoutScore.getChildAt(i);
            ll.addView(tv);

            //updating sum in the cardview
            mSums[i] += pointsPerPlayer[i];

            //get current textview and override with new sum
            tv = (TextView) linearLayoutSum.getChildAt(i);
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

    public void deleteRound(final int index) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ScoreboardDefault.this);
        builder.setTitle("Ali želite izbrisati rundo?");

        builder.setPositiveButton("DA", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                repairDataAfterRoundDeletion(rounds.get(index));
                rounds.remove(index);
                cleanRounds();
                loadRounds();
            }
        });

        builder.setNegativeButton("NE", null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void initialize() {
        Intent intent = getIntent();

        position = intent.getIntExtra("position", -1);
        cardView = MainActivity.Companion.getCardViewList().get(position);

        players = cardView.getPlayers();
        rounds = cardView.getRounds();
        mSums = cardView.getmSums();
        mRadlci = cardView.getRadlci();

        for (int i = 0; i < players.size(); i++) {
            TextView tv = ComponentFactory.Companion.createTextViewPlayer(players.get(i).getName(), false, function2);
            TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(tv, 1, 200, 1,
                TypedValue.COMPLEX_UNIT_DIP);
            linearLayoutPlayers.addView(tv);

            LinearLayout ll = ComponentFactory.Companion.createScoreLayout(i);
            linearLayoutScore.addView(ll);
            linearLayoutSum.addView(ComponentFactory.Companion.createTextViewSum(i));

            ll = ComponentFactory.Companion.createPlayersRadlcLayout(i, false, function2);
            linearLayoutRadlci.addView(ll);
        }

        loadRounds();
        loadRadlci();
        loadSums();
    }

    public void loadRounds() {
        TextView tv;
        for (int i = 0; i < rounds.size(); i++) {
            int[] ppp = rounds.get(i).getPointPerPlayer();
            for (int k = 0; k < ppp.length; k++) {
                if (ppp[k] == 0) {
                    tv = ComponentFactory.Companion.createTextViewScore("/", function1, false);
                } else {
                    tv = ComponentFactory.Companion.createTextViewScore(Integer.toString(ppp[k]), function1, false);
                }
                LinearLayout ll = (LinearLayout) linearLayoutScore.getChildAt(k);
                ll.addView(tv);
            }
        }
    }

    public void cleanRounds() {
        for (int i = 0; i < players.size(); i++) {
            LinearLayout ll = (LinearLayout) linearLayoutScore.getChildAt(i);
            ll.removeAllViews();
        }
    }

    public void loadRadlci() {
        for (int i = 0; i < players.size(); i++) {
            LinearLayout ll = (LinearLayout) linearLayoutRadlci.getChildAt(i);
            for (int k = 0; k < mRadlci[i]; k++) {
                ll.addView(ComponentFactory.Companion.createRadlc());
            }
        }
    }

    public void loadSums() {
        for (int i = 0; i < players.size(); i++) {
            TextView tv = (TextView) linearLayoutSum.getChildAt(i);
            tv.setText("" + mSums[i]);
        }
    }

    public void repairDataAfterRoundDeletion(Round round) {
        //sums correction
        for (int i = 0; i < players.size(); i++) {
            mSums[i] -= round.getPointPerPlayer()[i];
        }
        loadSums();
    }

    public void addRadlcOnClick(int id) {
        Dialog scoreDialog = new Dialog(ScoreboardDefault.this);
        mRadlci[id] += scoreDialog.RadlcDialog();
        if (mRadlci[id] < 0) {
            mRadlci[id] = 0;
        }
        Integer a = mRadlci[id];
        if (a != null || a != 0) {
            LinearLayout ll = (LinearLayout) linearLayoutRadlci.getChildAt(id);
            ll.removeAllViews();
            for (int i = 0; i < a; i++) {
                ll.addView(ComponentFactory.Companion.createRadlc());
            }
        }
    }
}