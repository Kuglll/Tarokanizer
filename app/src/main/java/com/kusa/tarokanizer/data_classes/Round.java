package com.kusa.tarokanizer.data_classes;

public class Round {

    private boolean automaticMode;

    private int idGame; //which game was played
    private int idPlayer;
    private int idRufanPlayer;
    private int points;
    private boolean razlikaPozitivna;
    private boolean won;

    private int [] pointPerPlayer;

    public Round(){
        automaticMode = true;
        idGame = -1;
        idPlayer = -1;
        idRufanPlayer = -1;
        points = 0;
        won = false;
        razlikaPozitivna = false;
    }

    public boolean isAutomaticMode() {
        return automaticMode;
    }

    public void setAutomaticMode(boolean automaticMode) {
        this.automaticMode = automaticMode;
    }

    public int getIdGame() {
        return idGame;
    }

    public void setIdGame(int idGame) {
        this.idGame = idGame;
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

    public int[] getPointPerPlayer() {
        return pointPerPlayer;
    }

    public void setPointPerPlayer(int[] pointPerPlayer) {
        this.pointPerPlayer = pointPerPlayer;
    }
}
