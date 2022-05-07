package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.model.player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class TurnTest {
    Turn turn;
    Player player;
    ArrayList<AssistantCard> playedCards;
    ArrayList<Player> orderedPlayers;
    AssistantCard card1;
    AssistantCard card2;


    @BeforeEach
    private void setUp() throws IOException {
        this.player = new Player("player1", 3, 3, TowerColour.WHITE, 3);
        this.turn = new Turn(Phase.ACTION, player);
        this.card1 = new AssistantCard(1, 1);
        this.card2 = new AssistantCard(2, 2);
    }

    @Test
    void getCurrentPhase() {
        assertEquals(Phase.ACTION, turn.getCurrentPhase());
    }

    @Test
    void getCurrentPlayer() {
        assertEquals(player, turn.getCurrentPlayer());
    }

    @Test
    void setCurrentPhase() {
        turn.setCurrentPhase(Phase.PLANNING);
        assertEquals(Phase.PLANNING, turn.getCurrentPhase());
    }

    @Test
    void setCurrentPlayer() throws IOException {
        Player player2 = new Player("player2", 3, 3, TowerColour.BLACK, 3);
        turn.setCurrentPlayer(player2);
        assertEquals(player2, turn.getCurrentPlayer());
    }

    @Test
    void addplayedCards() {
       this.turn.addplayedCards(card1);
        assertEquals(card1, turn.getPlayedCards().get(0));
    }
/*
    @Test
    void getPlayedCards() {
    }
*/
    /*
    @Test
    void updateWithPlayedAssistant() throws  IOException {
        turn.updateWithPlayedAssistant(player);
        assertEquals(card1, orderedPlayers.get(0).getChosenAssistant());
    }
    *
     */
}