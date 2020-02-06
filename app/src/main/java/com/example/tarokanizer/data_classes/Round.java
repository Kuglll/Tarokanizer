package com.example.tarokanizer.data_classes;

public class Round {

    int idPlayer;
    int idRufanPlayer;
    int points;
    boolean razlikaPozitivna;
    boolean won;

    public Round(){
        idPlayer = -1;
        idRufanPlayer = -1;
        points = 0;
        won = false;
        razlikaPozitivna = false;
    }

    public int getIdPlayer() {
        return idPlayer;
    }

    public void setIdPlayer(int idPlayer) {
        this.idPlayer = idPlayer;
    }

    public int getIdRufanPlayer() {
        return idRufanPlayer;
    }

    public void setIdRufanPlayer(int idRufanPlayer) {
        this.idRufanPlayer = idRufanPlayer;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public void addPoints(int points){
        this.points += points;
    }

    public boolean isWon() {
        return won;
    }

    public void setWon(boolean won) {
        this.won = won;
    }

    public boolean isRazlikaPozitivna() {
        return razlikaPozitivna;
    }

    public void setRazlikaPozitivna(boolean razlikaPozitivna) {
        this.razlikaPozitivna = razlikaPozitivna;
    }
}
