package it.polimi.ingsw.server.model.board;

import it.polimi.ingsw.server.model.Bag;

import java.util.List;

public class GameBoard {
    private List<Cloud> clouds;
    private List<IsleGroup> isles;
    private Bag bag;
    private MotherNature motherNature;


    public IsleGroup getMotherNaturePosition(){
        return motherNature.getPosition();
    }
    public void moveMotherNatureTo(IsleGroup isle){
        motherNature.setPosition(isle);
    }



}
