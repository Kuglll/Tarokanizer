package com.example.tarokanizer;

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

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements Dialog.DialogListener{

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    private static ArrayList<CardView> mCardViewList;
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

        mCardViewList = new ArrayList<>();

        //preferences = getApplicationContext().getSharedPreferences("mPreferences", MODE_PRIVATE);
        preferences = getPreferences(MODE_PRIVATE);
        loadCardViewList();

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

    public void loadCardViewList(){
        Gson gson = new Gson();
        int numberOfCardViews = preferences.getInt("numberOfCardViews", 0);
        CardView cv = null;

        for(int i=0; i<numberOfCardViews; i++){
            String json = preferences.getString("cardView" + i, "");
            cv = gson.fromJson(json, CardView.class);
            mCardViewList.add(cv);
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
        mAdapter.notifyDataSetChanged();
        //mAdapter.notifyItemRangeChanged(position, mCardViewList.size());
    }

    public void onPause() {
        super.onPause();
        storeCardViewList();
    }

    public void storeCardViewList(){
        editor = preferences.edit();
        Gson gson = new Gson();
        int i = 0;

        for(CardView cv: mCardViewList){
            String json = gson.toJson(cv);
            editor.putString("cardView" + i, json);
            i++;
        }
        editor.putInt("numberOfCardViews", i);

        editor.apply();
    }
}
