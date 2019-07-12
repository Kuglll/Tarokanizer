package com.example.tarokanizer;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import java.util.ArrayList;

public class Dialog extends AppCompatDialogFragment {

    private EditText editTextTitle;
    private EditText editTextNumberOfPlayers;
    private DialogListener listener;
    private ArrayList<CardView> mCardViewList;
    private Adapter mAdapter;
    private String returnTitle;

    public Dialog(ArrayList<CardView> list,  Adapter adapter){
        mCardViewList = list;
        mAdapter = adapter;
    }

    @Override
    public android.app.Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialog, null);

        builder.setView(view)
                .setTitle("New game")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("Create", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        String title = editTextTitle.getText().toString();
                        returnTitle = title;
                        String numberOfPlayers = editTextNumberOfPlayers.getText().toString();
                        int position = mCardViewList.size();
                        listener.applyTexts(title, numberOfPlayers);
                        AddNewCardboard(position);
                    }
                });

        editTextTitle = view.findViewById(R.id.edit_title);
        editTextNumberOfPlayers = view.findViewById(R.id.edit_numberOfPlayers);

        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            listener = (DialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement DialogListener.");
        }
    }

    public interface DialogListener{
        void applyTexts (String title, String numberOfPlayers);
    }

    //Creates new Cardboard with a title after the New button is clicked. Dialog is run asynchronous, that is why this method is created in this class.
    public void AddNewCardboard(int position){

        mCardViewList.add(position ,new CardView(returnTitle));
        mAdapter.notifyItemInserted(position);
    }



}
