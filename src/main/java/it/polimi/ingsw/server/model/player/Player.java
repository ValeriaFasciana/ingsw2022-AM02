package it.polimi.ingsw.server.model.player;

import it.polimi.ingsw.server.model.*;
import it.polimi.ingsw.server.model.player.playerBoard.PlayerBoard;

import java.io.IOException;
import java.util.*;

public class Player {
    private final String nickName;
    private HashMap<Integer, AssistantCard> deck;
    private PlayerBoard board;
    private Integer coins;
    private int towerCounter;
    private TowerColour towerColour;
    private AssistantCard chosenAssistant;
    private State currentState;

    public Player(String nickName,int studentsInEntrance, int towerCounter,TowerColour towerColour){
        this.nickName = nickName;
        this.board = new PlayerBoard(studentsInEntrance);
        this.towerCounter = towerCounter;
        this.towerColour = towerColour;
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

    public State getCurrentState() {
        return this.currentState;
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

    public int getChosenAssistantValue(){
        return this.chosenAssistant.getValue();
    }

    public int getTowerCounter() {return towerCounter;}

    public int getChosenAssistantMovements(){return this.chosenAssistant.getMovement();}

    public int getStudentsOnHallTable(PawnColour colour){
        return this.board.getStudentsInTable(colour);
    }

    public HashMap<Integer, AssistantCard> getDeck() {return deck;}

    public void addStudentsToHall(EnumMap<PawnColour,Integer> studentMap){
        this.board.addStudentsToHall(studentMap);
    }


    public TowerColour getTowerColour() {
        return towerColour;
    }







}
