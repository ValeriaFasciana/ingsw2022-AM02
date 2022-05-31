package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.model.cards.AssistantCard;
import it.polimi.ingsw.shared.enums.PawnColour;

import java.util.HashMap;
import java.util.Map;

public class PlayerBoardData {
    private HashMap<Integer, AssistantCard> deck;
    private int towerCounter;
    private Map<PawnColour,Integer> entrance;
    private Map<PawnColour,Integer> hall;

    public PlayerBoardData(HashMap<Integer, AssistantCard> deck,int towerCounter, Map<PawnColour, Integer> entrance, Map<PawnColour, Integer> hall) {
        this.deck = deck;
        this.towerCounter = towerCounter;
        this.entrance = entrance;
        this.hall = hall;
    }
}
