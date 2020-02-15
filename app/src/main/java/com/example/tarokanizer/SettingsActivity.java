package com.example.tarokanizer;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

public class SettingsActivity extends AppCompatActivity {

    static Intent startSettingsActivity(Context ctx){
        return new Intent(ctx, SettingsActivity.class);
    }

    Toolbar toolbar;

    Switch swtch;
    Button saveButton;

    EditText ena;
    EditText dva;
    EditText tri;
    EditText soloEna;
    EditText soloDva;
    EditText soloTri;
    EditText soloBrez;

    EditText trula;
    EditText napovedanaTrula;
    EditText kralji;
    EditText napovedaniKralji;
    EditText spicka;
    EditText napovedanaSpicka;
    EditText kralj;
    EditText napovedanKralj;

    boolean changed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        changed = true;

        initViews();
    }

    public void initViews(){
        swtch = findViewById(R.id.switchButton);
        saveButton = findViewById(R.id.saveButton);

        saveButton = findViewById(R.id.saveButton);
        ena = findViewById(R.id.enaEdit);
        dva = findViewById(R.id.dvaEdit);
        tri = findViewById(R.id.triEdit);
        soloEna = findViewById(R.id.soloEnaEdit);
        soloDva = findViewById(R.id.soloDveEdit);
        soloTri = findViewById(R.id.soloTriEdit);
        soloBrez = findViewById(R.id.soloBrezEdit);

        trula = findViewById(R.id.trulaEdit);
        napovedanaTrula = findViewById(R.id.napovedanaTrulaEdit);
        kralji = findViewById(R.id.kraljiEdit);
        napovedaniKralji = findViewById(R.id.napovedaniKraljiEdit);
        spicka = findViewById(R.id.spickaEdit);
        napovedanaSpicka = findViewById(R.id.napovedanaSpickaEdit);
        kralj = findViewById(R.id.kraljEdit);
        napovedanKralj = findViewById(R.id.napovedanKraljEdit);
    }

    @Override
    public void onBackPressed() {
        if(changed){
            AlertDialog.Builder builder = new AlertDialog.Builder(SettingsActivity.this);
            builder.setTitle("Nastavitve niso shranjene! Ali res želite zapustiti stran?");

            builder.setPositiveButton("DA", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    changed = false;
                    onBackPressed();
                }
            });

            builder.setNegativeButton("NE", null);

            AlertDialog dialog = builder.create();
            dialog.show();
        } else {
            super.onBackPressed();
        }

    }
}
