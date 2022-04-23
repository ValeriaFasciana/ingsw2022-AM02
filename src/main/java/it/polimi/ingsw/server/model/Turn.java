package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.model.characters.DefaultRuleSet;
import it.polimi.ingsw.server.model.characters.RuleSet;
import it.polimi.ingsw.server.model.player.Player;

import java.lang.reflect.Array;
import java.util.*;

public class Turn {
    private Phase currentPhase;
    private Player currentPlayer;
    private ArrayList<Player> orderedPlayers;
    private RuleSet ruleSet;


    public Turn(Phase currentPhase, Player currentPlayer) {
        this.currentPhase = currentPhase;
        this.currentPlayer = currentPlayer;
        this.orderedPlayers = new ArrayList<>();
        this.ruleSet = new DefaultRuleSet();
    }

//    public ArrayList<> getAvailableActions(){
//        return this.ruleSet.getAvailableActions(this.currentPhase);
//
//    }

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
                Comparator.comparingInt(Player::getChosenAssistantValue));
    }


}
