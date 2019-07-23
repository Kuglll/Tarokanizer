package com.example.tarokanizer;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import static java.lang.Thread.*;

public class Dialog extends DialogFragment implements AdapterView.OnItemSelectedListener {

    private EditText editTextTitle;
    private Spinner spinner;
    private DialogListener listener;
    private ArrayList<String> players;
    private String numberOfPlayers;
    private ArrayList<String> mName = new ArrayList<String>();

    public interface DialogListener{
        public void onDialogPositiveClick(String title, ArrayList<String> players);
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

    @Override
    //gets the number of players from  spinner
    public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
        String text =  parent.getItemAtPosition(position).toString();
        numberOfPlayers = text;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public android.app.Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        players = new ArrayList<>();

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialog, null);

        editTextTitle = view.findViewById(R.id.edit_title);
        spinner = view.findViewById(R.id.edit_numberOfPlayers);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.peopleNumber, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        builder.setView(view)
                .setTitle("New game")
                .setPositiveButton("Create", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Send the positive button event back to the host activity
                        String title = editTextTitle.getText().toString();

                        if(!title.equals("") && !numberOfPlayers.equals("")) {
                            getPlayersNames(Integer.parseInt(numberOfPlayers));
                        }
                        getDialog().dismiss();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Send the negative button event back to the host activity
                        getDialog().dismiss();
                    }
                });

        return builder.create();
    }

    public ArrayList<String> CreatePlayerNamesDialog(final int i)
    {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.player_names, null);
        RelativeLayout l = (RelativeLayout) view.findViewById(R.id.playerNames);

        final EditText t = new EditText(getActivity());
        t.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT ));
        t.setTextColor(Color.BLACK);
        l.addView(t);

        builder.setView(view)
                .setTitle("Player " + i)
                .setPositiveButton("Next", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Send the positive button event back to the host activity
                        String name;
                        name = t.getText().toString();
                        players.add(name);
                        mName.add(name);

                        // when the last name is assigned the next button creates an instance
                        if(i == Integer.parseInt(numberOfPlayers)) {
                            String title = editTextTitle.getText().toString();
                            listener.onDialogPositiveClick(title, players);
                        }
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Send the negative button event back to the host activity
                    }
                })
                .show();
        return mName;
    }

    public void getPlayersNames(int numberOfPlayers) {

        for (int i = numberOfPlayers; i >= 1; i--) {
            CreatePlayerNamesDialog(i);
        }
    }



}
