package it.polimi.ingsw.server.model.player;

import it.polimi.ingsw.network.data.PlayerBoardData;
import it.polimi.ingsw.server.model.*;
import it.polimi.ingsw.server.model.cards.AssistantCard;
import it.polimi.ingsw.server.model.player.playerBoard.PlayerBoard;
import it.polimi.ingsw.shared.enums.PawnColour;
import it.polimi.ingsw.shared.enums.TowerColour;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Class of the player
 */
public class Player {
    private final String nickName;
    private HashMap<Integer, AssistantCard> deck;
    private PlayerBoard board;
    private Integer coins;
    private int towerCounter;
    private TowerColour towerColour;
    private Optional<AssistantCard> chosenAssistant = Optional.empty();
    private boolean hasPlayedCharacter;
    private boolean isActive;


    /**
     * Default Constructor
     * @param nickName nickname of player
     * @param studentsInEntrance students in entrance
     * @param towerCounter tower counter
     * @param deck assistant card deck
     * @param coins amount of coins
     */
    public Player(String nickName,int studentsInEntrance, int towerCounter,HashMap<Integer, AssistantCard> deck,int coins){
        this.nickName = nickName;
        this.board = new PlayerBoard(studentsInEntrance);
        this.towerCounter = towerCounter;
        this.deck = deck;
        this.coins = coins;
        this.hasPlayedCharacter = false;
        this.isActive = true;
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


    /**
     * Method to add student to hall
     * @param studentColour colour of student to add
     * @param expertVariant if true, game mode is expert
     */
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

    /**
     * Method to remove students from entrance
     * @param studentMap student map
     */
    public void removeStudentsFromEntrance(Map<PawnColour, Integer> studentMap) {
        this.board.removeStudentsFromEntrance(studentMap);
    }

    /**
     * Method to remove tower
     */
    public void removeTower() {
        this.towerCounter--;
    }

    /**
     * Method to add tower
     */
    public void addTower() {
        this.towerCounter++;
    }

    /**
     * Method to handle when an assistant card is played
     * @param assistantId id of played assistant card
     */
    public void playAssistant(Integer assistantId) {
        this.chosenAssistant = Optional.ofNullable(deck.get(assistantId));
        this.deck.remove(assistantId);
    }

    /**
     * Method to add students to entrance
     * @param studentMap student map
     */
    public void addStudentsToEntrance(Map<PawnColour,Integer> studentMap) {
        this.board.addStudentsToEntrance(studentMap);
    }

    public PlayerBoardData getBoardData(Map<PawnColour,Professor> professorMap){
        Set<PawnColour> playerProfessors = professorMap.entrySet().stream().filter(professor ->professor.getValue().getPlayer().equals(nickName)).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)).keySet();

        return new PlayerBoardData(this.deck,this.towerCounter,this.towerColour,board.getStudentsInEntrance(),board.getStudentsInHall(),playerProfessors,coins,hasPlayedCharacter,isActive,chosenAssistant.isPresent() ? chosenAssistant.get().getId() : null);
    }

    public Map<PawnColour, Boolean> getHallAvailability() {
        return board.getHall().getAvailableColourMap();
    }



    /**
     * Method to pay coins when player wants to play character card
     * @param price price of character card
     */
    public void payCoins(int price) {
        coins = coins - price;
    }


    public void setHasPlayedCharacter(boolean hasPlayedCharacter) {
        this.hasPlayedCharacter = hasPlayedCharacter;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

    public boolean isActive() {
        return isActive;
    }


}
