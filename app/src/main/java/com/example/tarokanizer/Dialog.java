package com.example.tarokanizer;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.fragment.app.DialogFragment;

import java.util.ArrayList;

public class Dialog extends DialogFragment {

    private EditText editTextTitle;
    private EditText editTextNumberOfPlayers;
    private DialogListener listener;
    private ArrayList<String> players;

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

    public android.app.Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialog, null);

        players = new ArrayList<>();
        editTextTitle = view.findViewById(R.id.edit_title);
        editTextNumberOfPlayers = view.findViewById(R.id.edit_numberOfPlayers);

        builder.setView(view)
                .setTitle("New game")
                .setPositiveButton("Create", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Send the positive button event back to the host activity
                        String title = editTextTitle.getText().toString();
                        String numberOfPlayers = editTextNumberOfPlayers.getText().toString();

                        if(!title.equals("") && !numberOfPlayers.equals("")) {
                            getPlayersNames(Integer.parseInt(numberOfPlayers));
                            listener.onDialogPositiveClick(title, players);
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

    public void getPlayersNames(int numberOfPlayers){
        for(int i=0; i<numberOfPlayers; i++){
            players.add("player");
            //TODO: build x dialogs and get player names
        }
    }

}
