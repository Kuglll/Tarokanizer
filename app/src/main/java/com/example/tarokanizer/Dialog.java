package com.example.tarokanizer;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Dialog extends DialogFragment implements AdapterView.OnItemSelectedListener {

    private EditText editTextTitle;
    private Spinner spinner;
    private DialogListener listener;
    private ArrayList<String> players;
    private int numberOfPlayers;
    private Context context;
    private int mPlayers;

    public Dialog(Context context){
        this.context = context;
    }

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
        numberOfPlayers = Integer.parseInt(text);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public synchronized android.app.Dialog onCreateDialog(Bundle savedInstanceState){
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
                        if(!title.equals("")) {
                            CreatePlayerNamesDialog(numberOfPlayers);
                        } else{
                            Toast.makeText(getActivity(), "Missing game title! Better luck next time.", Toast.LENGTH_LONG).show();
                        }
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

    public void CreatePlayerNamesDialog(final int i) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        View view = LayoutInflater.from(context).inflate(R.layout.player_names, null);
        RelativeLayout l = view.findViewById(R.id.playerNames);

        final EditText t = new EditText(context);
        t.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        t.setTextColor(Color.BLACK);
        l.addView(t);

        builder.setView(view);
        builder.setTitle("Player " + (numberOfPlayers+1-i));
        builder.setPositiveButton("Next", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // Send the positive button event back to the host activity
                String name;
                name = t.getText().toString();
                players.add(name);

                mPlayers = i - 1;
                // when the last name is assigned the next button creates an instance
                if (i == 1) {
                    String title = editTextTitle.getText().toString();
                    listener.onDialogPositiveClick(title, players);
                    return;
                }
                //Creating new dialogs until we run out of players
                CreatePlayerNamesDialog(mPlayers);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // Send the negative button event back to the host activity
                mPlayers = 0;
            }
        });
        builder.show();

    }

}
