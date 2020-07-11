package com.kusa.tarokanizer.data_classes;

public class Settings {

    //klop, berac 70, pikolo 70 - radlci
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

    //mode
    boolean automaticMode = true;

    int ena = 30;
    int dva = 20;
    int tri = 10;

    int beracPikolo = 70;

    int soloEna = 60;
    int soloDva = 50;
    int soloTri = 40;
    int soloBrez = 80;

    int valat = 250;
    int napovedanValat = 500;
    int barvniValat = 125;
    int mondFang = 15;
    int renons = 70;

    int radlc = -100;

    //addons
    int trula = 10;
    int napovedanaTrula = 20;
    int kralji = 10;
    int napovedaniKralji = 20;
    int pagatUltimo = 25;
    int napovedanPagatUltimo = 50;
    int kraljUltimo = 10;
    int napovedanKraljUltimo = 20;

    private static Settings settings = null;

    private Settings(){} //Singleton

    public static Settings getInstance(){
        if (settings == null) settings = new Settings();

        return settings;
    }

    public boolean isAutomaticMode() {
        return automaticMode;
    }

    public void setAutomaticMode(boolean automaticMode) {
        this.automaticMode = automaticMode;
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

    public int getPagatUltimo() {
        return pagatUltimo;
    }

    public void setPagatUltimo(int pagatUltimo) {
        this.pagatUltimo = pagatUltimo;
    }

    public int getNapovedanPagatUltimo() {
        return napovedanPagatUltimo;
    }

    public void setNapovedanPagatUltimo(int napovedanPagatUltimo) {
        this.napovedanPagatUltimo = napovedanPagatUltimo;
    }

    public int getKraljUltimo() {
        return kraljUltimo;
    }

    public void setKraljUltimo(int kraljUltimo) {
        this.kraljUltimo = kraljUltimo;
    }

    public int getNapovedanKraljUltimo() {
        return napovedanKraljUltimo;
    }

    public void setNapovedanKraljUltimo(int napovedanKraljUltimo) {
        this.napovedanKraljUltimo = napovedanKraljUltimo;
    }

    public int getBeracPikolo() {
        return beracPikolo;
    }

    public void setBeracPikolo(int beracPikolo) {
        this.beracPikolo = beracPikolo;
    }

    public int getValat() {
        return valat;
    }

    public void setValat(int valat) {
        this.valat = valat;
    }

    public int getNapovedanValat() {
        return napovedanValat;
    }

    public void setNapovedanValat(int napovedanValat) {
        this.napovedanValat = napovedanValat;
    }

    public int getBarvniValat() {
        return barvniValat;
    }

    public void setBarvniValat(int barvniValat) {
        this.barvniValat = barvniValat;
    }

    public int getMondFang() {
        return mondFang;
    }

    public void setMondFang(int mondFang) {
        this.mondFang = mondFang;
    }

    public int getRenons() {
        return renons;
    }

    public void setRenons(int renons) {
        this.renons = renons;
    }

    public int getRadlc() {
        return radlc;
    }

    public void setRadlc(int radlc) {
        this.radlc = radlc;
    }
}
