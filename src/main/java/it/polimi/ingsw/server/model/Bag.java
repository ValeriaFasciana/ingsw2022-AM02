package it.polimi.ingsw.server.model;

import java.util.EnumMap;
import java.util.Random;

public class Bag extends StudentContainer{

    public Bag() {
        super(150);
        addStudentsForEachColour(2);
    }

    /*
        pick the desired number of pawns randomly
    */
    public EnumMap<PawnColour, Integer> pick(Integer number){
        EnumMap<PawnColour, Integer> pickedStudents = new EnumMap<PawnColour,Integer>(PawnColour.class);
        while(number>0){
            PawnColour randomColour = PawnColour.values()[new Random().nextInt(PawnColour.values().length)];
            super.removeStudents(randomColour,1);
            pickedStudents.put(randomColour,pickedStudents.get(randomColour)+1);
            number--;
        }
        return pickedStudents;
    }
}
