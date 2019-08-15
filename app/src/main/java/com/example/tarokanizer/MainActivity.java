package com.example.tarokanizer;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements Dialog.DialogListener{

    FileOutputStream fos;
    ObjectOutputStream os;
    FileInputStream fis;
    ObjectInputStream ois;

    private static ArrayList<CardView> mCardViewList = new ArrayList<>();;
    private RecyclerView mRecyclerView;
    private Adapter mAdapter; //Adapters provide a binding from an app-specific data set to views that are displayed within a RecyclerView
    private RecyclerView.LayoutManager mLayoutManager;
    private Button buttonNew;
    private Toolbar toolbar;

    //TODO: these 2 variables will be passed into game list
    private EditText textViewtitle;
    private EditText textViewnumberOfPlayers;
    private String returnTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //preferences = getApplicationContext().getSharedPreferences("mPreferences", MODE_PRIVATE);
        //preferences = getPreferences(MODE_PRIVATE);
        

        BuildRecyclerView();

        buttonNew = findViewById(R.id.button_new);
        buttonNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //creates a dialog that runs in another thread (class Dialog)
                Dialog dialog = new Dialog(MainActivity.this);
                dialog.show(getSupportFragmentManager(), "dialog");
            }
        });
    }

    public void onResume() {
        super.onResume();

        try {
            fos = getApplicationContext().openFileOutput("storage", Context.MODE_PRIVATE);
            os = new ObjectOutputStream(fos);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }
        loadCardViewList();
    }

    public void loadCardViewList(){
        try {
            fis = getApplicationContext().openFileInput("storage");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try{
            Integer numberOfCardViews = ois.readInt();
            for(int i=0; i<numberOfCardViews; i++){
                CardView cv = (CardView) ois.readObject();
                mCardViewList.add(cv);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            ois.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void BuildRecyclerView() {

        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true); //increases performance
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new Adapter(this, mCardViewList);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnCardBoardClickListener(new Adapter.OnDeleteButtonClickListener() {
            @Override
            //deleting the cardboard
            public void onDeleteClick(final int position) {
                final AlertDialog deleteDialog = new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Delete Game")
                        .setMessage("Do you want to delete selected game?")
                        .setPositiveButton("Yes", null)
                        .setNegativeButton("No", null)
                        .show();

                Button positiveButton = deleteDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                positiveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        RemoveItem(position);
                        deleteDialog.dismiss();
                    }
                });
            }
        });
    }

    public static ArrayList<CardView> getCardViewList(){
        return mCardViewList;
    }

    public void AddNewCardboard(int position, String title, ArrayList<String> players){
        mCardViewList.add(position, new CardView(title, players));
        mAdapter.notifyItemInserted(position);
    }

    @Override
    public void onDialogPositiveClick(String title, ArrayList<String> players) {
        AddNewCardboard(mCardViewList.size(), title, players);
    }

    public void RemoveItem(int position) {
        mCardViewList.remove(position);
        mAdapter.notifyItemRemoved(position);
    }

    public void onPause() {
        super.onPause();
        storeCardViewList();
    }

    public void storeCardViewList(){
        Integer numberOfCardViews = mCardViewList.size();
        try {
            os.writeInt(numberOfCardViews);
        } catch (IOException e) {
            e.printStackTrace();
        }
        for(CardView cv: mCardViewList){
            try {
                os.writeObject(cv);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
