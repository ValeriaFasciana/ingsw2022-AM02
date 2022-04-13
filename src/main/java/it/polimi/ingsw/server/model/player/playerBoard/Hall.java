package it.polimi.ingsw.server.model.player.playerBoard;

import it.polimi.ingsw.server.model.PawnColour;
import it.polimi.ingsw.server.model.StudentContainer;

import java.util.EnumMap;

public class Hall extends StudentContainer {
    public Hall(){
        super(50);
    }

    public boolean isLineFull(PawnColour colour){
        return !(super.getStudentsByColour(colour) < 10);
    }

    public void addStudent(PawnColour colour){
        if(isLineFull(colour))return;
        EnumMap<PawnColour,Integer> toAddMap = new EnumMap<PawnColour, Integer>(PawnColour.class);
        toAddMap.put(colour,1);
        this.addStudents(toAddMap);
    }
}
