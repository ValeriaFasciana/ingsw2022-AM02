package it.polimi.ingsw.server.model.board;

import it.polimi.ingsw.shared.enums.PawnColour;

import java.util.EnumMap;

public class CloudData {
    EnumMap<PawnColour,Integer> studentMap;
    boolean side;


    public CloudData(EnumMap<PawnColour, Integer> studentMap, boolean side) {
        this.studentMap = studentMap;
        this.side = side;
    }
}
