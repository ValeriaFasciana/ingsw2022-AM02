package it.polimi.ingsw.server.model.player;

import it.polimi.ingsw.server.model.*;
import it.polimi.ingsw.server.model.player.playerBoard.PlayerBoard;

import java.util.Map;
import java.util.UUID;

public class Player {
    private final String nickName;
    private Map<UUID, AssistantCard> deck;
    private PlayerBoard board;
    private Integer coins;
    private Integer towerCounter;
    private TowerColour towerColour;
    private AssistantCard chosenAssistant;
    private State currentState;
    private Game game;

    public Player(String nickName, Game game, Integer towerCounter){
        this.nickName = nickName;
        this.board = new PlayerBoard(game.getSettings().getStudentsInEntrance());
        this.towerCounter = towerCounter;
        this.game = game;
    }

    public AssistantCard getChosenAssistant() {
        return chosenAssistant;
    }

    public void setCoins(Integer coins) {
        this.coins = coins;
    }

    public void setState(State state){
        this.currentState = state;
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
        this.game.updateTurnWithPlayedAssistant(this);
    }

    public int getLastPlayedAssistantValue(){
        return this.chosenAssistant.getValue();
    }


}
