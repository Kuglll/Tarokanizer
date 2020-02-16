package com.kusa.tarokanizer;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.kusa.tarokanizer.data_classes.Settings;

public class SettingsActivity extends AppCompatActivity {

    static Intent startSettingsActivity(Context ctx){
        return new Intent(ctx, SettingsActivity.class);
    }

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    Toolbar toolbar;
    Settings settings;

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

        preferences = getSharedPreferences(MainActivity.SHARED_PREFERENCES, MODE_PRIVATE);
        settings = Settings.getInstance();
        changed = false;

        initViews();
        getData();
        initOnClickListeners();
    }

    public void initViews(){
        swtch = findViewById(R.id.switchButton);
        if(settings.isAutomaticMode()) swtch.setChecked(true);
        else swtch.setChecked(false);

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

    public void initOnClickListeners(){
        saveButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                changed = false;
                storeSettings();
                Toast.makeText(SettingsActivity.this, "Nastavitve uspešno shranjene!", Toast.LENGTH_LONG).show();
            }
        });

        swtch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                changed = true;
                if(checked){
                    settings.setAutomaticMode(true);
                }else{
                    settings.setAutomaticMode(false);
                }
            }
        });

        ena.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                changed = true;
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        dva.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                changed = true;
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        tri.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                changed = true;
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        soloEna.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                changed = true;
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        soloDva.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                changed = true;
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        soloTri.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                changed = true;
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        soloBrez.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                changed = true;
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        trula.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                changed = true;
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        napovedanaTrula.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                changed = true;
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        kralji.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                changed = true;
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        napovedaniKralji.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                changed = true;
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        spicka.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                changed = true;
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        napovedanaSpicka.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                changed = true;
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        kralj.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                changed = true;
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        napovedanKralj.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                changed = true;
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    public void storeSettings(){
        if(!ena.getText().toString().equals("")){
            settings.setEna(Integer.parseInt(ena.getText().toString()));
        }
        if(!dva.getText().toString().equals("")){
            settings.setDva(Integer.parseInt(dva.getText().toString()));
        }
        if(!tri.getText().toString().equals("")){
            settings.setTri(Integer.parseInt(tri.getText().toString()));
        }
        if(!soloEna.getText().toString().equals("")){
            settings.setSoloEna(Integer.parseInt(soloEna.getText().toString()));
        }
        if(!soloDva.getText().toString().equals("")){
            settings.setSoloDva(Integer.parseInt(soloDva.getText().toString()));
        }
        if(!soloTri.getText().toString().equals("")){
            settings.setSoloTri(Integer.parseInt(soloTri.getText().toString()));
        }
        if(!soloBrez.getText().toString().equals("")){
            settings.setSoloBrez(Integer.parseInt(soloBrez.getText().toString()));
        }
        if(!trula.getText().toString().equals("")){
            settings.setTrula(Integer.parseInt(trula.getText().toString()));
        }
        if(!napovedanaTrula.getText().toString().equals("")){
            settings.setNapovedanaTrula(Integer.parseInt(napovedanaTrula.getText().toString()));
        }
        if(!kralji.getText().toString().equals("")){
            settings.setKralji(Integer.parseInt(kralji.getText().toString()));
        }
        if(!napovedaniKralji.getText().toString().equals("")){
            settings.setNapovedaniKralji(Integer.parseInt(napovedaniKralji.getText().toString()));
        }
        if(!spicka.getText().toString().equals("")){
            settings.setSpicka(Integer.parseInt(spicka.getText().toString()));
        }
        if(!napovedanaSpicka.getText().toString().equals("")){
            settings.setNapovedanaSpicka(Integer.parseInt(napovedanaSpicka.getText().toString()));
        }
        if(!kralj.getText().toString().equals("")){
            settings.setKralj(Integer.parseInt(kralj.getText().toString()));
        }
        if(!napovedanKralj.getText().toString().equals("")){
            settings.setNapovedanKralj(Integer.parseInt(napovedanKralj.getText().toString()));
        }

        storeToSp();

        Log.d("Settings", "Settings stored!");
    }

    public void storeToSp(){
        editor = preferences.edit();

        editor.putBoolean("automaticMode", settings.isAutomaticMode());

        editor.putInt("ena", settings.getEna());
        editor.putInt("dva", settings.getDva());
        editor.putInt("tri", settings.getTri());
        editor.putInt("soloEna", settings.getSoloEna());
        editor.putInt("soloDva", settings.getSoloDva());
        editor.putInt("soloTri", settings.getSoloTri());
        editor.putInt("soloBrez", settings.getSoloBrez());
        editor.putInt("trula", settings.getTrula());
        editor.putInt("napovedanaTrula", settings.getNapovedanaTrula());
        editor.putInt("kralji", settings.getKralji());
        editor.putInt("napovedaniKralji", settings.getNapovedaniKralji());
        editor.putInt("spicka", settings.getSpicka());
        editor.putInt("napovedanaSpicka", settings.getNapovedanaSpicka());
        editor.putInt("kralj", settings.getKralj());
        editor.putInt("napovedanKralj", settings.getNapovedanKralj());

        editor.apply();

        Log.d("Settings", "Settings stored to SP!");
    }

    public void getData(){
        ena.setText(Integer.toString(settings.getEna()));
        dva.setText(Integer.toString(settings.getDva()));
        tri.setText(Integer.toString(settings.getTri()));
        soloEna.setText(Integer.toString(settings.getSoloEna()));
        soloDva.setText(Integer.toString(settings.getSoloDva()));
        soloTri.setText(Integer.toString(settings.getSoloTri()));
        soloBrez.setText(Integer.toString(settings.getSoloBrez()));

        trula.setText(Integer.toString(settings.getTrula()));
        napovedanaTrula.setText(Integer.toString(settings.getNapovedanaTrula()));
        kralji.setText(Integer.toString(settings.getKralji()));
        napovedaniKralji.setText(Integer.toString(settings.getNapovedaniKralji()));
        spicka.setText(Integer.toString(settings.getSpicka()));
        napovedanaSpicka.setText(Integer.toString(settings.getNapovedanaSpicka()));
        kralj.setText(Integer.toString(settings.getKralj()));
        napovedanKralj.setText(Integer.toString(settings.getNapovedanKralj()));
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
