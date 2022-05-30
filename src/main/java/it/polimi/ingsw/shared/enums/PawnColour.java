package it.polimi.ingsw.shared.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * Enumeration representing all the possible colours of the students available
 */

public enum PawnColour {
    RED(0),
    BLUE(1),
    GREEN(2),
    PINK(3),
    YELLOW(4);

    private static Map<Integer, PawnColour> map = new HashMap<>();
    private int value;

    PawnColour(int value){
        this.value = value;
    }

    static{
        for(PawnColour colour : PawnColour.values()){
            map.put(colour.value, colour);
        }
    }

    /**
     * Get the resource corresponding to an int
     * @param colour the integer to be converted to a Resource
     * @return the resource corresponding to an int
     */
    public static PawnColour valueOf(int colour){
        return map.get(colour);
    }

    /**
     * Get the int value of a PawnColour
     * @return the int value of a Colour
     */
    public int getValue(){
        return value;
    }
}
