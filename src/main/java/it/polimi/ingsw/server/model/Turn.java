package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.model.player.Player;

public class Turn {
Phase currentPhase;
Player currentPlayer;

    public Turn(Phase currentPhase, Player currentPlayer) {
        this.currentPhase = currentPhase;
        this.currentPlayer = currentPlayer;
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
}
