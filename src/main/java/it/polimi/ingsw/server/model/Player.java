package it.polimi.ingsw.server.model;

import java.util.Map;
import java.util.UUID;

public class Player {
    private final String nickName;
    private Map<UUID,AssistantCard> deck;
    private PlayerBoard board;
    private Integer coins;
    private Integer towerCounter;
    private TowerColour towerColour;
    private AssistantCard chosenAssistant;
    private State currentState;

    public Player(String nickName,Game game){
        this.nickName = nickName;
        this.board = new PlayerBoard(game);
    }

    public AssistantCard getChosenAssistant() {
        return chosenAssistant;
    }

    public void setCoins(Integer coins) {
        this.coins = coins;
    }

    public void setTowerCounter(Integer towerCounter) {
        this.towerCounter = towerCounter;
    }

    public void setTowerColour(TowerColour towercolour) {
        this.towerColour = towercolour;
    }

    public void playAssistant(UUID assistantId){
        this.chosenAssistant = deck.get(assistantId);
        deck.remove(assistantId);
    }

}
