package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.model.cards.AssistantCard;
import it.polimi.ingsw.server.model.player.Player;
import it.polimi.ingsw.shared.enums.Phase;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RoundTest {
    static Round round;
    static Player player1;
    static Player player2;
    static Player player3;
    static HashMap<String,Player> playerMap = new HashMap<>();
    static AssistantCard card1;
    static AssistantCard card2;
    static AssistantCard card3;


    @BeforeAll
    private static void setUp() throws IOException {
//        player1 = new Player("player1", 3, 3, TowerColour.WHITE);
//        player2 = new Player("player2", 3, 3, TowerColour.BLACK);
//        player3 = new Player("player3", 3, 3, TowerColour.GREY);

        playerMap.put(player1.getNickName(),player1);
        playerMap.put(player2.getNickName(),player2);
        playerMap.put(player3.getNickName(),player3);

        ArrayList<String> playerList = new ArrayList<>();
        playerList.add(player1.getNickName());
        playerList.add(player2.getNickName());
        playerList.add(player3.getNickName());
        round = new Round(player1,playerList);
        card1 = new AssistantCard(0, 1,1);
        card2 = new AssistantCard(1, 2,2);
        card3 = card2;

    }


    @Test
    void updateWithPlayedAssistant() {
        this.round.updateWithPlayedAssistant(this.card1);
        assertEquals(card1.getId(), this.round.getPlayedAssistants().get(0));
        List<Round.OrderElement> order = this.round.getActionOrder();
        assertEquals("player1",order.get(0).playerId);
    }

    @Test
    void setNextPlayer() {
        this.round.setNextPlayer(this.playerMap);
        assertEquals("player2",this.round.getCurrentPlayer());
        this.round.updateWithPlayedAssistant(card2);
        this.round.setNextPlayer(this.playerMap);
        assertEquals("player3",this.round.getCurrentPlayer());
        this.round.updateWithPlayedAssistant(card3);
    }

    @Test
    void setActionPhase() {
        this.round.setNextPlayer(this.playerMap);
        assertEquals(Phase.ACTION,this.round.getCurrentPhase());
        assertEquals("player2",this.round.getCurrentPlayer());
    }

    @Test
    void setNextActionTurn() {
        this.round.setNextPlayer(this.playerMap);
        assertEquals(Phase.ACTION,this.round.getCurrentPhase());
        assertEquals("player3",this.round.getCurrentPlayer());
        assertEquals(1,this.round.getActionOrder().size());
        this.round.setNextPlayer(this.playerMap);
        assertEquals("player1",this.round.getCurrentPlayer());
        assertEquals(0,this.round.getActionOrder().size());
        assertTrue(round.isEnded());
        this.round.setNextPlayer(this.playerMap);
        round = round.initNextRound(playerMap);
        assertEquals(Phase.PLANNING,round.getCurrentPhase());
        assertEquals("player2",round.getCurrentPlayer());
    }



}