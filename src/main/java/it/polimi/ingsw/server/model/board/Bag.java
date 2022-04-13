package it.polimi.ingsw.server.model.board;

import it.polimi.ingsw.server.model.PawnColour;
import it.polimi.ingsw.server.model.StudentContainer;

import javax.swing.*;
import java.lang.reflect.Array;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Random;

public class Bag extends StudentContainer {

    public Bag() {
        super(150);
        addStudentsForEachColour(2);
    }

    /*
        pick the desired number of pawns randomly
    */
    public EnumMap<PawnColour, Integer> pick(Integer number){
        EnumMap<PawnColour, Integer> pickedStudents = new EnumMap<PawnColour,Integer>(PawnColour.class);

        while(number>0 && !super.isEmpty()){
            Object[] availableColours = super.getAvailableColours().toArray();
            PawnColour randomColour = (PawnColour) availableColours[(new Random().nextInt(super.getAvailableColours().size()))];
            EnumMap<PawnColour, Integer> toRemoveMap = new EnumMap<PawnColour, Integer>(PawnColour.class);
            toRemoveMap.put(randomColour,1);
            super.removeStudents(toRemoveMap);
            if(pickedStudents.get(randomColour)== null)pickedStudents.put(randomColour,0);
            pickedStudents.put(randomColour, pickedStudents.get(randomColour) +1);
            number--;
        }
        return pickedStudents;
    }

    public Integer getNumberOfPawns(){
        Integer sum =0;
        for(PawnColour colour : PawnColour.values()){
            sum += super.getStudentsByColour(colour);
        }
        return sum;
    }
}
