package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.model.player.Player;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class Turn {
    private Phase currentPhase;
    private Player currentPlayer;
    private Player playingOrder[];



    public Turn(Phase currentPhase, Player currentPlayer, Game game) {
        this.currentPhase = currentPhase;
        this.currentPlayer = currentPlayer;
        this.playingOrder = new Player[game.getNumberOfPlayers()];
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

    public void resetPlayingOrder(Game Game){
        Collection<Player> players = Game.getPlayersPlayers();
        this.playingOrder= (Player[]) players.toArray();
        int n = playingOrder.length;
        Player temp = new Player("temp",Game,1);
        for(int i=0; i < n; i++){
            for(int j=1; j < (n-i); j++){
                if(playingOrder[j-1].getChosenAssistant().getValue() > playingOrder[j].getChosenAssistant().getValue()){
                    temp = playingOrder[j-1];
                    playingOrder[j-1] = playingOrder[j];
                    playingOrder[j] = temp;

                }}}}


}
