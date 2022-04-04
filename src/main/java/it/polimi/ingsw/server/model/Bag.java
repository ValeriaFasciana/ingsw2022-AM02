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

        while(number>0 && !super.isEmpty()){
            PawnColour randomColour = (PawnColour) super.getAvailableColours().toArray()[(new Random().nextInt(super.getAvailableColours().size()))];
            super.removeStudents(randomColour,1);
            if(pickedStudents.get(randomColour)== null)pickedStudents.put(randomColour,0);
            pickedStudents.put(randomColour,pickedStudents.get(randomColour).intValue()+1);
            number--;
        }
        return pickedStudents;
    }

    public Integer getNumberOfPawns(){
        Integer sum =0;
        for(PawnColour colour : PawnColour.values()){
            sum += super.getCount(colour);
        }
        return sum;
    }
}
