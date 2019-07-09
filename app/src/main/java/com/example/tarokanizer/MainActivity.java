package com.example.tarokanizer;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<CardView> mCardViewList;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter; //Adapters provide a binding from an app-specific data set to views that are displayed within a RecyclerView
    private RecyclerView.LayoutManager mLayoutManager;

    private Button buttonNew;

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
                int position = mCardViewList.size();
                InsertItem(position);
            }
        });

    }

    public void InsertItem(int position){

        mCardViewList.add(position ,new CardView("KajKaj"));
        mAdapter.notifyItemInserted(position);
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
    }
}
