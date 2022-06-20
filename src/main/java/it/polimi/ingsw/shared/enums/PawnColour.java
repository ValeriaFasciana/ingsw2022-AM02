package it.polimi.ingsw.shared.enums;

import java.util.HashMap;
import java.util.Map;

public enum PawnColour {
    RED (0),
    YELLOW(1),
    GREEN(2),
    BLUE(3),
    PINK(4);

    private int value;
    private static Map<Integer, PawnColour> map = new HashMap<>();

    PawnColour(int value){
        this.value = value;
    }

    public static PawnColour valueOf(int value){
        return map.get(value);
    }

    static{
        for(PawnColour color : PawnColour.values()) map.put(color.value, color);
    }
}
