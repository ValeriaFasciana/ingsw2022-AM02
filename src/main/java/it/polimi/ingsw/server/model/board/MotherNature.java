package it.polimi.ingsw.server.model.board;


public class MotherNature {
    private IsleGroup position;

    public MotherNature(IsleGroup initialPosition){
        this.position = initialPosition;
    }
    public IsleGroup getPosition() {
        return position;
    }

    public void setPosition(IsleGroup position) {
        this.position = position;
    }
}
