package it.polimi.ingsw.server.model.board;


public class MotherNature {
    private int position;

    public MotherNature(int initialPosition){
        this.position = initialPosition;
    }
    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
