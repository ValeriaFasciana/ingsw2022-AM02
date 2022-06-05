package it.polimi.ingsw.network.data;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.shared.enums.TowerColour;
import it.polimi.ingsw.server.model.cards.AssistantCard;
import it.polimi.ingsw.shared.enums.PawnColour;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class PlayerBoardData {
    private HashMap<Integer, AssistantCard> deck;
    private int towerCounter;
    private TowerColour towerColour;
    private Map<PawnColour,Integer> entrance;
    private Map<PawnColour,Integer> hall;
    private Set<PawnColour> professors;

    @JsonCreator
    public PlayerBoardData(@JsonProperty("deck")HashMap<Integer,AssistantCard> deck,
                           @JsonProperty("towerCounter") int towerCounter,
                           @JsonProperty("towerColour") TowerColour towerColour,
                           @JsonProperty("entrance") Map<PawnColour, Integer> entrance,
                           @JsonProperty("hall") Map<PawnColour, Integer> hall,
                           @JsonProperty("professors") Set<PawnColour> professors) {
        this.deck = deck;
        this.towerCounter = towerCounter;
        this.towerColour = towerColour;
        this.entrance = entrance;
        this.hall = hall;
        this.professors = professors;
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

    @JsonGetter
    public TowerColour getTowerColour() {
        return towerColour;
    }

    @JsonGetter
    public Set<PawnColour> getProfessors() {
        return professors;
    }
}