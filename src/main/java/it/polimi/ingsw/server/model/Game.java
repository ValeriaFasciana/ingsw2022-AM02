package it.polimi.ingsw.server.model;
import it.polimi.ingsw.server.model.board.GameBoard;
import it.polimi.ingsw.server.model.board.IsleGroup;
import it.polimi.ingsw.server.model.characters.CharacterCard;
import it.polimi.ingsw.server.model.player.Player;

import java.io.IOException;
import java.util.*;

public class Game {

    private Map<String, Player> players;
    private GameBoard gameBoard;
    private Integer numberOfPlayers;
    private State state;
    private Player currentPlayer;
    private Boolean expertVariant;
    private GameSettings settings;
    private Turn currentTurn;
    private Map<UUID, CharacterCard> characterMap;
    private Map<PawnColour,Professor> professorMap;
    private Deserializer deserializer = new Deserializer();

    public Game(Map<String, Player> players, Integer numberOfPlayers,Boolean expertVariant) throws IOException {
        this.settings = deserializer.getSettings(numberOfPlayers);
        this.gameBoard = new GameBoard(settings.getNumberOfClouds(), settings.getNumberOfIslands(),settings.getStudentsInClouds());
        this.players = players;
        this.professorMap = new EnumMap<PawnColour, Professor>(PawnColour.class);
        if(expertVariant) initCharacterCards();
    }




    public void addPlayer(String nickname, TowerColour towerColour){
        if(this.players.containsKey(nickname))return;
        Player newPlayer = new Player(nickname,this.settings.getStudentsInEntrance(),this.settings.getNumberOfTowersForPlayer(),towerColour);
        this.players.put(nickname,newPlayer);
    }


    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public void moveMotherNature(int isleIndex){
        //this.gameBoard.moveMotherNatureTo(isleIndex);
    }

    public void getPlayableAssistants(){

    }

    public IsleGroup getMotherNaturePosition(){
        return this.gameBoard.getMotherNaturePosition();
    }


    private void initCharacterCards() {
        //Deserializator.getCharacters();
    }


    public void updateTurnWithPlayedAssistant(Player player) {
        this.currentTurn.updateWithPlayedAssistant(player);
    }

    public void startPlanningPhase(){
        this.currentTurn.setCurrentPhase(Phase.PLANNING);
        this.gameBoard.addStudentsToCloud(this.settings.getStudentsInClouds());
    }


    public Map<String, Player> getPlayers() {
        return players;
    }




    public GameBoard getGameBoard() {
        return gameBoard;
    }

    public Integer getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public GameSettings getSettings(){
        return settings;
    }

    public Map<PawnColour, Professor> getProfessorMap() {
        return this.professorMap;
    }


}
