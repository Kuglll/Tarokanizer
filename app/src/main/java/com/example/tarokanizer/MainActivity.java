package com.example.tarokanizer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements Dialog.DialogListener {

    private ArrayList<CardView> mCardViewList;

    private RecyclerView mRecyclerView;
    private Adapter mAdapter; //Adapters provide a binding from an app-specific data set to views that are displayed within a RecyclerView
    private RecyclerView.LayoutManager mLayoutManager;

    private Button buttonNew;

    //TODO: these 2 variables will be passed into game list
    private EditText textViewtitle;
    private EditText textViewnumberOfPlayers;
    private String returnTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CreateCardViewList();
        BuildRecyclerView();

        buttonNew = findViewById(R.id.button_new);

        buttonNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //creates a dialog that runs in another thread (class Dialog)
                Dialog dialog = new Dialog(mCardViewList, mAdapter);
                dialog.show(getSupportFragmentManager(), "dialog");
            }
        });
    }


    public void CreateCardViewList(){

        mCardViewList = new ArrayList<>();
    }
    public void BuildRecyclerView() {

        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true); //increases performance
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new Adapter(mCardViewList);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnCardBoardClickListener(new Adapter.OnCardBoardClickListener() {
            @Override
            public void onCardBoardClick(int position) {
                //actions that happen on cardboardclick
                Intent intent = new Intent(MainActivity.this, scoreboard.class);

                //change this
                String [] players = {"tim", "mark", "okorn"};

                intent.putExtra("playerNames", players);
                startActivity(intent);
            }
        });
    }

    //passing and applying the string in dialog
    @Override
    public void applyTexts(String title, String numberOfPlayers) {
            returnTitle = title;
            //textViewtitle.setText(title);
            //textViewnumberOfPlayers.setText(numberOfPlayers);
    }
}
