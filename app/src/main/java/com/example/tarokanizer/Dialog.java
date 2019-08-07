package com.example.tarokanizer;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import android.view.WindowManager.LayoutParams;

import java.util.ArrayList;

public class Dialog extends DialogFragment implements AdapterView.OnItemSelectedListener {

    private EditText editTextTitle;
    private Spinner spinner;
    private DialogListener listener;
    private ArrayList<String> players;
    private int numberOfPlayers;
    private Context context;
    private int mPlayers;
    private int mScore;
    private boolean resultValue;
    private boolean mPositive = true;

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
        AlertDialog dialog;

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

        //this part makes sure that the keyboard pops up at the start of the dialog
        dialog = builder.create();
        dialog.getWindow().setSoftInputMode(LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        dialog.show();

        return dialog;
    }

    public void CreatePlayerNamesDialog(final int i) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        AlertDialog dialog;

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

        //this part makes sure that the keyboard pops up at the start of the dialog
        dialog = builder.create();
        dialog.getWindow().setSoftInputMode(LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        dialog.show();

    }

    public int ScoreDialog (View view, final Context cont){

        final Handler handler = new Handler()
        {
            @Override
            public void handleMessage(Message mesg)
            {
                throw new RuntimeException();
            }
        };
        final CharSequence[] items = {"+", "-"};
        AlertDialog.Builder builder = new AlertDialog.Builder(cont);
        AlertDialog dialog;

        view = LayoutInflater.from(cont).inflate(R.layout.player_names, null);
        RelativeLayout l = view.findViewById(R.id.playerNames);

        final EditText t = new EditText(cont);
        t.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        t.setInputType(InputType.TYPE_CLASS_NUMBER); //numbers only
        t.setTextColor(Color.BLACK);
        l.addView(t);

        builder.setView(view);
        builder.setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int item) {
               if(item == 1){mPositive = false;}
               else{mPositive = true;}
            }
        });
        builder.setTitle("Score");
        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // Send the positive button event back to the host activity
                mScore = Integer.parseInt(t.getText().toString());
                handler.sendMessage(handler.obtainMessage());
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // Send the negative button event back to the host activity
                Dialog.this.notify();
                handler.sendMessage(handler.obtainMessage());
            }
        });

        //this part makes sure that the keyboard pops up at the start of the dialog
        dialog = builder.create();
        dialog.getWindow().setSoftInputMode(LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        dialog.show();

        try{ Looper.loop(); }
        catch(RuntimeException e){}

        if(!mPositive){mScore *= -1;}

        return mScore;
    }

}
