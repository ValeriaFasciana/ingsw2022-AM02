package it.polimi.ingsw.server.model;
import it.polimi.ingsw.server.model.board.GameBoard;
import it.polimi.ingsw.server.model.player.Player;

import javax.swing.*;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class Game {

    private Map<String, Player> players;
    private GameBoard gameBoard;
    private Integer numberOfPlayers;
    private State state;
    private Player currentPlayer;
    private Boolean expertVariant;
    private GameSettings settings;
    private Turn currentTurn;

    public Game(Set<String> playersNicknames, Integer numberOfPlayers,Boolean expertVariant){
        //this.settings = Deserializator.getSettings(numberOfPlayers);
        this.gameBoard = new GameBoard(settings.getNumberOfClouds(), settings.getNumberOfIslands(),settings.getStudentsInClouds());
    }
    public Set<String> getPlayers() {
        return players.keySet();
    }
    public Collection<Player> getPlayersPlayers() {return players.values();}

    public GameBoard getGameBoard() {
        return gameBoard;
    }

    public Integer getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public GameSettings getSettings(){
        return settings;
    }

    public void addPlayer(String nickname){
        if(this.players.containsKey(nickname))return;
        Player newPlayer = new Player(nickname,this,this.settings.getNumberOfTowersForPlayer());
        this.players.put(nickname,newPlayer);
    }


    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public void moveMotherNature(int isleIndex){
        this.gameBoard.moveMotherNatureTo(isleIndex);
    }

    public void getPlayableAssistants(){

    }

    public Integer getMotherNaturePosition(){
        return this.gameBoard.getMotherNaturePosition().getIndex();
    }

    public void getMotherNatureAvailableMoves(){
        int start = this.getMotherNaturePosition();
        int end = this.getMotherNaturePosition();

    }




}
