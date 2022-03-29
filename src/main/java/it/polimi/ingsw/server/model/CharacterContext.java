package it.polimi.ingsw.server.model;

public class CharacterContext {
    int id;
    int price;
    boolean alreadyPlayed;



    public int getId() {
        return id;

    }

    public int getPrice() {
        return price;
    }

    public boolean isAlreadyPlayed() {
        return alreadyPlayed;
    }

    public void setAlreadyPlayed(boolean alreadyPlayed) {
        this.alreadyPlayed = alreadyPlayed;
    }

    public CharacterContext(int id) {
        this.id = id;
        this.alreadyPlayed = false;
        this.price= id%3;
        if(this.price==0)
            this.price=3;
    }


    void effect(){

    }

}
