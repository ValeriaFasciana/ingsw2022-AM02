package it.polimi.ingsw.server.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.server.model.cards.AssistantCard;
import it.polimi.ingsw.shared.enums.PawnColour;

import java.util.HashMap;
import java.util.Map;

public class    PlayerBoardData {
    private HashMap<Integer, AssistantCard> deck;
    private int towerCounter;
    private Map<PawnColour,Integer> entrance;
    private Map<PawnColour,Integer> hall;

    @JsonCreator
    public PlayerBoardData(@JsonProperty("deck")HashMap<Integer,AssistantCard> deck,
                           @JsonProperty("towerCounter") int towerCounter,
                           @JsonProperty("entrance") Map<PawnColour, Integer> entrance,
                           @JsonProperty("hall") Map<PawnColour, Integer> hall) {
        this.deck = deck;
        this.towerCounter = towerCounter;
        this.entrance = entrance;
        this.hall = hall;
    }

    @JsonGetter
    public HashMap<Integer, AssistantCard> getDeck() {
        return deck;
    }

    @JsonGetter
    public int getTowerCounter() {
        return towerCounter;
    }

    @JsonGetter
    public Map<PawnColour, Integer> getEntrance() {
        return entrance;
    }

    @JsonGetter
    public Map<PawnColour, Integer> getHall() {
        return hall;
    }
}
