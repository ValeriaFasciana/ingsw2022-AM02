package it.polimi.ingsw.server.model;

import it.polimi.ingsw.shared.enums.PawnColour;

import java.util.Map;

public class PlayerBoardData {
    Map<PawnColour,Integer> entrance;
    Map<PawnColour,Integer> hall;

    public PlayerBoardData(Map<PawnColour, Integer> entrance, Map<PawnColour, Integer> hall) {
        this.entrance = entrance;
        this.hall = hall;
    }
}
