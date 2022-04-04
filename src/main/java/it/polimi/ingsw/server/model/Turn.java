package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.model.player.Player;

import java.util.Collection;
import java.util.TreeMap;


public class Turn {
    private Phase currentPhase;
    private Player currentPlayer;
    private TreeMap<Player,Integer> playingOrder;



    public Turn(Phase currentPhase, Player currentPlayer) {
        this.currentPhase = currentPhase;
        this.currentPlayer = currentPlayer;
        this.playingOrder = new TreeMap<Player,Integer>();

    }

    public Phase getCurrentPhase() {
        return currentPhase;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPhase(Phase currentPhase) {
        this.currentPhase = currentPhase;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }


    public void setPlayingOrder(Game Game){
        Collection<Player> players = Game.getPlayersPlayers();
        players.forEach(player -> playingOrder.put(player,player.getChosenAssistant().getValue()));
    }
    public void resetPlayingOrder(){
        for (var entry : playingOrder.entrySet()) {
            entry.setValue(entry.getKey().getChosenAssistant().getValue());

        }
    }


}
