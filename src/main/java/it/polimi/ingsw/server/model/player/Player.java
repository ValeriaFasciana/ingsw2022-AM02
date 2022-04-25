package it.polimi.ingsw.server.model.player;

import it.polimi.ingsw.server.model.*;
import it.polimi.ingsw.server.model.player.playerBoard.PlayerBoard;

import java.io.IOException;
import java.util.*;

public class Player {
    private final String nickName;
    private Map<Integer, AssistantCard> deck;
    private PlayerBoard board;
    private Integer coins;
    private int towerCounter;
    private TowerColour towerColour;
    private AssistantCard chosenAssistant;
    private State currentState;
    private Game game;
    private Deserializer deserializer = new Deserializer();

    public Player(String nickName,int studentsInEntrance, int towerCounter,TowerColour towerColour,Integer deckNumber) throws IOException {
        this.nickName = nickName;
        this.board = new PlayerBoard(studentsInEntrance);
        this.towerCounter = towerCounter;
        this.towerColour = towerColour;
        this.deck = deserializer.getDecks(deckNumber);
    }

    public AssistantCard getChosenAssistant() {
        return chosenAssistant;
    }

    public String getNickName() {
        return nickName;
    }

    public PlayerBoard getBoard(){
        return board;
    }

    public void setCoins(Integer coins) {
        this.coins = coins;
    }

    public Integer getCoins() {
        return coins;
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

    public void setChosenAssistant(AssistantCard chosenAssistant) {
        this.chosenAssistant = chosenAssistant;
    }

    public void playAssistant(UUID assistantId){
        this.chosenAssistant = deck.get(assistantId);
        deck.remove(assistantId);
        this.game.updateTurnWithPlayedAssistant(this);
    }

    public int getChosenAssistantValue(){
        return this.chosenAssistant.getValue();
    }

    public int getTowerCounter() {return towerCounter;}

    public int getChosenAssistantMovements(){return this.chosenAssistant.getMovement();}

    public int getStudentsOnHallTable(PawnColour colour){
        return this.board.getStudentsInTable(colour);
    }

    public Map<Integer, AssistantCard> getDeck() {return deck;}

    public void addStudentsToHall(EnumMap<PawnColour,Integer> studentMap){
        this.board.addStudentsToHall(studentMap);
    }


    public TowerColour getTowerColour() {
        return towerColour;
    }

    public Collection<AssistantCard> getAvailableCards(Turn turn){
        Collection<AssistantCard> availableCards = deck.values();
        Collection<AssistantCard> playedCards = turn.getPlayedCards();
        if (playedCards.containsAll(availableCards)){
            return availableCards;
        }
        availableCards.removeAll(playedCards);
        return availableCards;

    }

    public ArrayList<PawnColour> getAvailableDestination(){
        ArrayList<PawnColour> AvailableDestination = new ArrayList<>();
        for (PawnColour colour : PawnColour.values()) {
            if(!(this.board.getHall().isLineFull(colour))){
                AvailableDestination.add(colour);
            }

        }

        return AvailableDestination;

    }




}
