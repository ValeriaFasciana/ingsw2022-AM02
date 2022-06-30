package it.polimi.ingsw.server.model.player.playerBoard;

import it.polimi.ingsw.shared.enums.PawnColour;
import it.polimi.ingsw.server.model.StudentContainer;

import java.util.EnumMap;
import java.util.Map;

/**
 * Class for the playerboard hall
 */
public class Hall extends StudentContainer {
    public Hall(){
        super(50);
    }

    public boolean isLineFull(PawnColour colour){
        return (super.getStudentsByColour(colour) >= 10);
    }

    /**
     *
     * @param colour
     */
    public void addStudent(PawnColour colour){
        if(isLineFull(colour))return;
        EnumMap<PawnColour,Integer> toAddMap = new EnumMap<>(PawnColour.class);
        toAddMap.put(colour,1);
        this.addStudents(toAddMap);
    }

    public Map<PawnColour,Boolean> getAvailableColourMap(){
        Map<PawnColour,Boolean> colourAvailabilityMap = new EnumMap<>(PawnColour.class);
        for(PawnColour colour : PawnColour.values()){
            colourAvailabilityMap.put(colour,!isLineFull(colour));
        }
        return colourAvailabilityMap;
    }
}
