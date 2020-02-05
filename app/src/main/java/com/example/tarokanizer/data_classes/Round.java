package com.example.tarokanizer.data_classes;

public class Round {

    boolean [] checked;
    int points;
    boolean razlikaPozitivna;
    boolean won;

    public Round(int numberOfPlayers){
        checked = new boolean[numberOfPlayers];
        for(int i=0; i<checked.length; i++){
            checked[i] = false;
        }
        points = 0;
        won = false;
        razlikaPozitivna = false;
    }

    public boolean[] getChecked() {
        return checked;
    }

    public void setChecked(boolean[] checked) {
        this.checked = checked;
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
