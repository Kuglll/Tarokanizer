package com.kusa.tarokanizer;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.kusa.tarokanizer.data_classes.Settings;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import static com.kusa.tarokanizer.MainActivityKt.SHARED_PREFERENCES;

public class SettingsActivity extends AppCompatActivity {

    static Intent startSettingsActivity(Context ctx){
        return new Intent(ctx, SettingsActivity.class);
    }

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    Settings settings;

    Switch swtch;
    Button saveButton;
    Button backButton;

    EditText ena;
    EditText dva;
    EditText tri;
    EditText soloEna;
    EditText soloDva;
    EditText soloTri;
    EditText soloBrez;

    EditText beracPikolo;
    EditText valat;
    EditText napovedanValat;
    EditText barvniValat;
    EditText mondFang;
    EditText renons;

    EditText trula;
    EditText napovedanaTrula;
    EditText kralji;
    EditText napovedaniKralji;
    EditText pagatUltimo;
    EditText napovedanPagatUltimo;
    EditText kraljUltimo;
    EditText napovedanKraljUltimo;

    EditText radlci;

    boolean changed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        preferences = getSharedPreferences(SHARED_PREFERENCES, MODE_PRIVATE);
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

        backButton = findViewById(R.id.backButton);
        saveButton = findViewById(R.id.saveButton);

        ena = findViewById(R.id.enaEdit);
        dva = findViewById(R.id.dvaEdit);
        tri = findViewById(R.id.triEdit);
        soloEna = findViewById(R.id.soloEnaEdit);
        soloDva = findViewById(R.id.soloDveEdit);
        soloTri = findViewById(R.id.soloTriEdit);
        soloBrez = findViewById(R.id.soloBrezEdit);

        beracPikolo = findViewById(R.id.beracPikoloEdit);
        valat = findViewById(R.id.valatEdit);
        napovedanValat = findViewById(R.id.napovedanValatEdit);
        barvniValat = findViewById(R.id.barvniValatEdit);
        mondFang = findViewById(R.id.mondFangEdit);
        renons = findViewById(R.id.renonsEdit);

        trula = findViewById(R.id.trulaEdit);
        napovedanaTrula = findViewById(R.id.napovedanaTrulaEdit);
        kralji = findViewById(R.id.kraljiEdit);
        napovedaniKralji = findViewById(R.id.napovedaniKraljiEdit);
        pagatUltimo = findViewById(R.id.spickaEdit);
        napovedanPagatUltimo = findViewById(R.id.napovedanaSpickaEdit);
        kraljUltimo = findViewById(R.id.kraljEdit);
        napovedanKraljUltimo = findViewById(R.id.napovedanKraljEdit);

        radlci = findViewById(R.id.radlcEdit);
    }

    public void initOnClickListeners(){
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

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

        addTextChangeListener(ena);
        addTextChangeListener(dva);
        addTextChangeListener(tri);
        addTextChangeListener(soloEna);
        addTextChangeListener(soloDva);
        addTextChangeListener(soloTri);
        addTextChangeListener(soloBrez);
        addTextChangeListener(beracPikolo);
        addTextChangeListener(valat);
        addTextChangeListener(napovedanValat);

        addTextChangeListener(barvniValat);
        addTextChangeListener(mondFang);
        addTextChangeListener(renons);

        addTextChangeListener(trula);
        addTextChangeListener(napovedanaTrula);
        addTextChangeListener(kralji);
        addTextChangeListener(napovedaniKralji);
        addTextChangeListener(pagatUltimo);
        addTextChangeListener(napovedanPagatUltimo);
        addTextChangeListener(kraljUltimo);
        addTextChangeListener(napovedanKraljUltimo);

        addTextChangeListener(radlci);
    }

    public void addTextChangeListener(EditText et) {
        et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!changed) {
                    changed = true;
                }
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
        if (!beracPikolo.getText().toString().equals("")) {
            settings.setBeracPikolo(Integer.parseInt(beracPikolo.getText().toString()));
        }
        if (!valat.getText().toString().equals("")) {
            settings.setValat(Integer.parseInt(valat.getText().toString()));
        }
        if (!napovedanValat.getText().toString().equals("")) {
            settings.setNapovedanValat(Integer.parseInt(napovedanValat.getText().toString()));
        }
        if (!barvniValat.getText().toString().equals("")) {
            settings.setBarvniValat(Integer.parseInt(barvniValat.getText().toString()));
        }
        if (!mondFang.getText().toString().equals("")) {
            settings.setMondFang(Integer.parseInt(mondFang.getText().toString()));
        }
        if (!renons.getText().toString().equals("")) {
            settings.setRenons(Integer.parseInt(renons.getText().toString()));
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
        if(!pagatUltimo.getText().toString().equals("")){
            settings.setPagatUltimo(Integer.parseInt(pagatUltimo.getText().toString()));
        }
        if(!napovedanPagatUltimo.getText().toString().equals("")){
            settings.setNapovedanPagatUltimo(Integer.parseInt(napovedanPagatUltimo.getText().toString()));
        }
        if(!kraljUltimo.getText().toString().equals("")){
            settings.setKraljUltimo(Integer.parseInt(kraljUltimo.getText().toString()));
        }
        if(!napovedanKraljUltimo.getText().toString().equals("")){
            settings.setNapovedanKraljUltimo(Integer.parseInt(napovedanKraljUltimo.getText().toString()));
        }
        if(!radlci.getText().toString().equals("")){
            settings.setRadlc(Integer.parseInt(radlci.getText().toString()));
        }

        storeToSp();
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

        editor.putInt("beracPikolo", settings.getBeracPikolo());
        editor.putInt("valat", settings.getValat());
        editor.putInt("napovedanValat", settings.getNapovedanValat());
        editor.putInt("barvniValat", settings.getBarvniValat());
        editor.putInt("mondFang", settings.getMondFang());
        editor.putInt("renons", settings.getRenons());

        editor.putInt("trula", settings.getTrula());
        editor.putInt("napovedanaTrula", settings.getNapovedanaTrula());
        editor.putInt("kralji", settings.getKralji());
        editor.putInt("napovedaniKralji", settings.getNapovedaniKralji());
        editor.putInt("spicka", settings.getPagatUltimo());
        editor.putInt("napovedanaSpicka", settings.getNapovedanPagatUltimo());
        editor.putInt("kralj", settings.getKraljUltimo());
        editor.putInt("napovedanKralj", settings.getNapovedanKraljUltimo());

        editor.putInt("radlc", settings.getRadlc());

        editor.apply();

        onBackPressed();
    }

    public void getData(){
        ena.setText(Integer.toString(settings.getEna()));
        dva.setText(Integer.toString(settings.getDva()));
        tri.setText(Integer.toString(settings.getTri()));
        soloEna.setText(Integer.toString(settings.getSoloEna()));
        soloDva.setText(Integer.toString(settings.getSoloDva()));
        soloTri.setText(Integer.toString(settings.getSoloTri()));
        soloBrez.setText(Integer.toString(settings.getSoloBrez()));

        beracPikolo.setText(Integer.toString(settings.getBeracPikolo()));
        valat.setText(Integer.toString(settings.getValat()));
        napovedanValat.setText(Integer.toString(settings.getNapovedanValat()));
        barvniValat.setText(Integer.toString(settings.getBarvniValat()));
        mondFang.setText(Integer.toString(settings.getMondFang()));
        renons.setText(Integer.toString(settings.getRenons()));

        trula.setText(Integer.toString(settings.getTrula()));
        napovedanaTrula.setText(Integer.toString(settings.getNapovedanaTrula()));
        kralji.setText(Integer.toString(settings.getKralji()));
        napovedaniKralji.setText(Integer.toString(settings.getNapovedaniKralji()));
        pagatUltimo.setText(Integer.toString(settings.getPagatUltimo()));
        napovedanPagatUltimo.setText(Integer.toString(settings.getNapovedanPagatUltimo()));
        kraljUltimo.setText(Integer.toString(settings.getKraljUltimo()));
        napovedanKraljUltimo.setText(Integer.toString(settings.getNapovedanKraljUltimo()));

        radlci.setText(Integer.toString(settings.getRadlc()));
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
