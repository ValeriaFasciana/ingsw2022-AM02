package it.polimi.ingsw.server.model.board;

import it.polimi.ingsw.shared.enums.PawnColour;
import it.polimi.ingsw.server.model.StudentContainer;

import java.util.EnumMap;
import java.util.Map;
import java.util.Random;

public class Bag extends StudentContainer {

    private Random rand = new Random();

    public Bag(){
        super(150);
        addStudentsForEachColour(2);
    }

    /**
     * pick the desired number of pawns randomly
     * @param number of students to pick
     * @return map of picked students
     */
    public Map<PawnColour, Integer> pick(Integer number){
        EnumMap<PawnColour, Integer> pickedStudents = new EnumMap<>(PawnColour.class);

        while(number>0 && !super.isEmpty()){
            Object[] availableColours = super.getAvailableColours().keySet().toArray();
            PawnColour randomColour = (PawnColour) availableColours[(rand.nextInt(super.getAvailableColours().keySet().size()))];
            EnumMap<PawnColour, Integer> toRemoveMap = new EnumMap<>(PawnColour.class);
            toRemoveMap.put(randomColour,1);
            super.removeStudents(toRemoveMap);
            pickedStudents.putIfAbsent(randomColour, 0);
            pickedStudents.put(randomColour, pickedStudents.get(randomColour) +1);
            number--;
        }
        return pickedStudents;
    }

    /**
     * Method to get number of pawns
     * @return number of pawns
     */
    public Integer getNumberOfPawns(){
        Integer sum =0;
        for(PawnColour colour : PawnColour.values()){
            sum += super.getStudentsByColour(colour);
        }
        return sum;
    }
}
