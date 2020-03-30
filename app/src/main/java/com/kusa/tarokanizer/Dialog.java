package com.kusa.tarokanizer;

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
import android.view.WindowManager.LayoutParams;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.kusa.tarokanizer.data_classes.Player;

import java.util.ArrayList;

import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.RecyclerView;

public class Dialog extends DialogFragment implements AdapterView.OnItemSelectedListener {

    private EditText editTextTitle;
    private Spinner spinner;
    private DialogListener listener;
    private ArrayList<Player> players;
    private int numberOfPlayers;
    private Context context;
    private int mPlayers;
    private Integer mRadlc;

    public Dialog(Context context){
        this.context = context;
    }

    public java.lang.Integer RadlcDialog() {
        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message mesg) {
                throw new RuntimeException();
            }
        };
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        final AlertDialog dialog;

        View view = LayoutInflater.from(context).inflate(R.layout.radlci_layout, null);
        final TextView plusText = view.findViewById(R.id.add_radlc);
        final TextView minusText = view.findViewById(R.id.remove_radlc);
        final ImageView closeRadlcWindow = view.findViewById(R.id.image_delete_radlc_window);

        builder.setView(view);

        dialog = builder.create();
        dialog.show();

        plusText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mRadlc = 1;
                dialog.cancel();
                handler.sendMessage(handler.obtainMessage());
            }
        });
        minusText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mRadlc = -1;
                dialog.cancel();
                handler.sendMessage(handler.obtainMessage());
            }
        });
        closeRadlcWindow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mRadlc = 0;
                dialog.cancel();
                handler.sendMessage(handler.obtainMessage());
            }
        });

        try {
            Looper.loop();
        } catch (RuntimeException e) {
        }

        return mRadlc;
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

        editTextTitle.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.peopleNumber, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);


        builder.setView(view)
                .setTitle("Nova igra")
                .setPositiveButton("USTVARI", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Send the positive button event back to the host activity
                        String title = editTextTitle.getText().toString();
                        if(title.length() > 15)
                        {
                            Toast.makeText(context, "Naslov ne sme biti daljši od 15 znakov!" , Toast.LENGTH_SHORT ).show();
                        }else {
                            if (!title.equals("")) {
                                CreatePlayerNamesDialog(numberOfPlayers);
                            } else {
                                Toast.makeText(getActivity(), "Naslov igre ne sme biti prazen!", Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                })
                .setNegativeButton("Prekliči", new DialogInterface.OnClickListener() {
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
        t.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
        t.setTextColor(Color.BLACK);
        l.addView(t);


        builder.setView(view);
        builder.setTitle("Igralec " + (numberOfPlayers+1-i));
        builder.setPositiveButton("Naprej", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // Send the positive button event back to the host activity
                String name = t.getText().toString();
                if(name.length() > 15) {
                    Toast.makeText(context, "Ime igralca ne sme biti daljše od 15 znakov!" , Toast.LENGTH_SHORT ).show();
                }else{
                players.add(new Player(name,numberOfPlayers-i));

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
            }
        });
        builder.setNegativeButton("Prekliči", new DialogInterface.OnClickListener() {
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

    public interface DialogListener {

        void onDialogPositiveClick(String title, ArrayList<Player> players);
    }
}