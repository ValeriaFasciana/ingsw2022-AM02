package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.model.player.Player;

import java.util.*;

public class Turn {
    private Phase currentPhase;
    private Player currentPlayer;
    private ArrayList<Player> orderedPlayers;



    public Turn(Phase currentPhase, Player currentPlayer) {
        this.currentPhase = currentPhase;
        this.currentPlayer = currentPlayer;
        this.orderedPlayers = new ArrayList<>();
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


    public void updateWithPlayedAssistant(Player player) {
        this.orderedPlayers.add(player);
        Collections.sort(orderedPlayers,
                Comparator.comparingInt(Player::getLastPlayedAssistantValue));
    }


}
