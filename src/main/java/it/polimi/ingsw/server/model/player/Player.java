package it.polimi.ingsw.server.model.player;

import it.polimi.ingsw.network.data.PlayerBoardData;
import it.polimi.ingsw.server.model.*;
import it.polimi.ingsw.server.model.cards.AssistantCard;
import it.polimi.ingsw.server.model.player.playerBoard.PlayerBoard;
import it.polimi.ingsw.shared.enums.PawnColour;
import it.polimi.ingsw.shared.enums.TowerColour;

import java.util.*;
import java.util.stream.Collectors;

public class Player {
    private final String nickName;
    private HashMap<Integer, AssistantCard> deck;
    private PlayerBoard board;
    private Integer coins;
    private int towerCounter;
    private TowerColour towerColour;
    private Optional<AssistantCard> chosenAssistant = Optional.empty();
    private boolean hasPlayedCharacter;




    public Player(String nickName,int studentsInEntrance, int towerCounter,HashMap<Integer, AssistantCard> deck,int coins){
        this.nickName = nickName;
        this.board = new PlayerBoard(studentsInEntrance);
        this.towerCounter = towerCounter;
        this.deck = deck;
        this.coins = coins;
        this.hasPlayedCharacter = false;

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

    public void setTowerColour(TowerColour towercolour) {
        this.towerColour = towercolour;
    }

    public int getTowerCounter() {return towerCounter;}

    public Map<Integer, AssistantCard> getDeck() {return deck;}

    public void addStudentToHall(PawnColour studentColour,boolean expertVariant){
        this.board.addStudentToHall(studentColour);
        List<Integer> hallCoinsPosition = new ArrayList<>();
        hallCoinsPosition.add(3);
        hallCoinsPosition.add(6);
        hallCoinsPosition.add(9);

        if(expertVariant && hallCoinsPosition.contains(board.getStudentsInTable(studentColour))){
            coins++;
        }
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

    public PlayerBoardData getBoardData(Map<PawnColour,Professor> professorMap){
        Set<PawnColour> playerProfessors = professorMap.entrySet().stream().filter(professor ->professor.getValue().getPlayer().equals(nickName)).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)).keySet();
        return new PlayerBoardData(this.deck,this.towerCounter,this.towerColour,board.getStudentsInEntrance(),board.getStudentsInHall(),playerProfessors,coins,hasPlayedCharacter);
    }

    public Map<PawnColour, Boolean> getHallAvailability() {
        return board.getHall().getAvailableColourMap();
    }

    public void payCoins(int price) {
        coins = coins - price;
    }

    public void setHasPlayedCharacter(boolean hasPlayedCharacter) {
        this.hasPlayedCharacter = hasPlayedCharacter;
    }
}
