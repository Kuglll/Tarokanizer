package com.example.tarokanizer.data_classes;

public class Settings {

    //klop 70, berac 70, pikolo 70 - radlci
    //ena 30, dva 20, tri 10
    //solo tri 40, solo dva 50, solo ena 60, solo brez 80

    /*
        trula 10
        napovedana trula 20
        krali 10
        napovedani krali 20
        špička 25
        napovedana špička 50
        kralj 10
        napovedan kralj 20
    */

    int ena = 30;
    int dva = 20;
    int tri = 10;

    int soloEna = 60;
    int soloDva = 50;
    int soloTri = 40;
    int soloBrez = 80;

    //addons
    int trula = 10;
    int napovedanaTrula = 20;
    int kralji = 10;
    int napovedaniKralji = 20;
    int spicka = 25;
    int napovedanaSpicka = 50;
    int kralj = 10;
    int napovedanKralj = 20;

    private static Settings settings = null;

    private Settings(){} //Singleton

    public static Settings getInstance(){
        if (settings == null) settings = new Settings();

        return settings;
    }

    public int getEna() {
        return ena;
    }

    public void setEna(int ena) {
        this.ena = ena;
    }

    public int getDva() {
        return dva;
    }

    public void setDva(int dva) {
        this.dva = dva;
    }

    public int getTri() {
        return tri;
    }

    public void setTri(int tri) {
        this.tri = tri;
    }

    public int getSoloEna() {
        return soloEna;
    }

    public void setSoloEna(int soloEna) {
        this.soloEna = soloEna;
    }

    public int getSoloDva() {
        return soloDva;
    }

    public void setSoloDva(int soloDva) {
        this.soloDva = soloDva;
    }

    public int getSoloTri() {
        return soloTri;
    }

    public void setSoloTri(int soloTri) {
        this.soloTri = soloTri;
    }

    public int getSoloBrez() {
        return soloBrez;
    }

    public void setSoloBrez(int soloBrez) {
        this.soloBrez = soloBrez;
    }

    public int getTrula() {
        return trula;
    }

    public void setTrula(int trula) {
        this.trula = trula;
    }

    public int getNapovedanaTrula() {
        return napovedanaTrula;
    }

    public void setNapovedanaTrula(int napovedanaTrula) {
        this.napovedanaTrula = napovedanaTrula;
    }

    public int getKralji() {
        return kralji;
    }

    public void setKralji(int kralji) {
        this.kralji = kralji;
    }

    public int getNapovedaniKralji() {
        return napovedaniKralji;
    }

    public void setNapovedaniKralji(int napovedaniKralji) {
        this.napovedaniKralji = napovedaniKralji;
    }

    public int getSpicka() {
        return spicka;
    }

    public void setSpicka(int spicka) {
        this.spicka = spicka;
    }

    public int getNapovedanaSpicka() {
        return napovedanaSpicka;
    }

    public void setNapovedanaSpicka(int napovedanaSpicka) {
        this.napovedanaSpicka = napovedanaSpicka;
    }

    public int getKralj() {
        return kralj;
    }

    public void setKralj(int kralj) {
        this.kralj = kralj;
    }

    public int getNapovedanKralj() {
        return napovedanKralj;
    }

    public void setNapovedanKralj(int napovedanKralj) {
        this.napovedanKralj = napovedanKralj;
    }
}
