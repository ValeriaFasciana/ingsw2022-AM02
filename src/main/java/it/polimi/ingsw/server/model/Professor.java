package it.polimi.ingsw.server.model;

public class Professor {
    String player;
    int counter;

    public Professor() {
        this.player = "";
        this.counter = 0;
    }

    public int getCounter() {
        return counter;
    }

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }
}
