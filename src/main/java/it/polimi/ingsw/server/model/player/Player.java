package it.polimi.ingsw.server.model.player;

import it.polimi.ingsw.server.model.*;
import it.polimi.ingsw.server.model.cards.AssistantCard;
import it.polimi.ingsw.server.model.player.playerBoard.PlayerBoard;
import it.polimi.ingsw.shared.enums.PawnColour;

import java.util.*;

public class Player {
    private final String nickName;
    private HashMap<Integer, AssistantCard> deck;
    private PlayerBoard board;
    private Integer coins;
    private int towerCounter;
    private TowerColour towerColour;
    private Optional<AssistantCard> chosenAssistant = Optional.empty();

    public Player(String nickName,int studentsInEntrance, int towerCounter,TowerColour towerColour) {
        this(nickName,studentsInEntrance, towerCounter,towerColour,null);
    }

    public Player(String nickName,int studentsInEntrance, int towerCounter,TowerColour towerColour,HashMap<Integer, AssistantCard> deck){
        this.nickName = nickName;
        this.board = new PlayerBoard(studentsInEntrance);
        this.towerCounter = towerCounter;
        this.towerColour = towerColour;
        this.deck = deck;
    }

    public Optional<AssistantCard> getChosenAssistant() {
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


    public void setTowerCounter(Integer towerCounter) {
        this.towerCounter = towerCounter;
    }

    public void setTowerColour(TowerColour towercolour) {
        this.towerColour = towercolour;
    }

    public int getChosenAssistantValue(){
        return this.chosenAssistant.get().getValue();
    }

    public int getTowerCounter() {return towerCounter;}

    public int getChosenAssistantMovements(){return this.chosenAssistant.get().getMovement();}

    public int getStudentsOnHallTable(PawnColour colour){
        return this.board.getStudentsInTable(colour);
    }

    public Map<Integer, AssistantCard> getDeck() {return deck;}

    public void addStudentToHall(PawnColour studentColour){
        this.board.addStudentToHall(studentColour);
    }

    public TowerColour getTowerColour() {
        return towerColour;
    }

    public void removeStudentsFromEntrance(Map<PawnColour, Integer> studentMap) {
        this.board.removeStudentsFromEntrance(studentMap);
    }

    public void removeTower() {
        this.towerCounter--;
    }

    public void addTower() {
        this.towerCounter++;
    }

    public void playAssistant(Integer assistantId) {
        this.chosenAssistant = Optional.ofNullable(deck.get(assistantId));
        this.deck.remove(assistantId);
    }

    public void addStudentsToEntrance(Map<PawnColour,Integer> studentMap) {
        this.board.addStudentsToEntrance(studentMap);
    }

    public PlayerBoardData getBoardData(){
        return new PlayerBoardData(this.deck,this.towerCounter,board.getStudentsInEntrance(),board.getStudentsInHall());
    }
}
