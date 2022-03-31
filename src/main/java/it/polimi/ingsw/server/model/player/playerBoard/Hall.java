package it.polimi.ingsw.server.model.player.playerBoard;

import it.polimi.ingsw.server.model.PawnColour;
import it.polimi.ingsw.server.model.StudentContainer;

public class Hall extends StudentContainer {
    public Hall(){
        super(50);
    }

    public boolean isLineFull(PawnColour colour){
        return !(super.getCount(colour) < 10);
    }

    public void addStudend(PawnColour colour){
        if(!isLineFull(colour))this.addStudents(colour,1);
    }
}
