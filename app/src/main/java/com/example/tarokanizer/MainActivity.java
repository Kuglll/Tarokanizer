package com.example.tarokanizer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
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

        mCardViewList = new ArrayList<>();
        BuildRecyclerView();

        buttonNew = findViewById(R.id.button_new);

        buttonNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //creates a dialog that runs in another thread (class Dialog)
                Dialog dialog = new Dialog();
                dialog.show(getSupportFragmentManager(), "dialog");
            }
        });
    }

    public void BuildRecyclerView() {

        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true); //increases performance
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new Adapter(this, mCardViewList);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

    public void AddNewCardboard(int position, String title){

        mCardViewList.add(position, new CardView(title));
        mAdapter.notifyItemInserted(position);
    }

    @Override
    public void onDialogPositiveClick(String title, String numberOfPlayers) {
        AddNewCardboard(mCardViewList.size(), title);
    }
}
