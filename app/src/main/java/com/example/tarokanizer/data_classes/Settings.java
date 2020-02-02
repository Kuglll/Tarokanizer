package com.example.tarokanizer.data_classes;

public class Settings {

    //klop 70, berac 70, pikolo 70 - radlci
    //ena 30, dva 20, tri 10
    //solo tri 40, solo dva 50, solo ena 60, solo brez 80

    int ena = 30;
    int dva = 20;
    int tri = 10;

    int soloEna = 60;
    int soloDva = 50;
    int soloTri = 40;
    int soloBrez = 80;

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
}
