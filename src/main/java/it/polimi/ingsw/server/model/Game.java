package it.polimi.ingsw.server.model;
import it.polimi.ingsw.server.model.board.GameBoard;
import it.polimi.ingsw.server.model.player.Player;

import java.util.Map;
import java.util.Set;

public class Game {

    private Map<String, Player> players;
    private GameBoard gameBoard;
    private Integer numberOfPlayers;
    private State state;
    private Player currentPlayer;
    private Boolean expertVariant;

    public Set<String> getPlayers() {
        return players.keySet();
    }


    public GameBoard getGameBoard() {
        return gameBoard;
    }

    public void setGameBoard() {
        this.gameBoard = gameBoard;
    }

    public Integer getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public void addPlayer(String nickname){

        Player newPlayer = new Player(nickname,this,7);
        this.players.put(nickname,newPlayer);
    }


    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public void moveMotherNature(){}


}
