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
    private static Deserializer deserializer = new Deserializer();
    private static final HashMap<Integer, AssistantCard> assistantDeck;

    static {
        Map<Integer,AssistantCard> tempDeck = new HashMap<>();

            tempDeck = deserializer.getAssistantDeck();

        assistantDeck = (HashMap<Integer, AssistantCard>) tempDeck;
    }



    @BeforeAll
    private static void setUp() throws IOException {
        player1 = new Player("player1", 3, 3,assistantDeck,0);
        player2 = new Player("player2", 3, 3,assistantDeck,0);
        player3 = new Player("player3", 3, 3,assistantDeck,0);

        playerMap.put(player1.getNickName(),player1);
        playerMap.put(player2.getNickName(),player2);
        playerMap.put(player3.getNickName(),player3);
        round = new Round(player1,playerMap.keySet().stream().toList(),1);

    }


    @Test
    void updateWithPlayedAssistant() {
        assertEquals("player1",round.getCurrentPlayer().getNickName());
        AssistantCard card1 = assistantDeck.get(2);
        System.out.print("card1: "+card1.getValue()+ "   "+ card1.getMovement()+"\n");
        this.round.updateWithPlayedAssistant(card1);
        assertEquals("player1",this.round.getActionOrder().get(0).playerId);
        round.setNextPlayer(playerMap);
        assertEquals("player2",round.getCurrentPlayer().getNickName());
        AssistantCard card2 = assistantDeck.get(4);
        System.out.print("card2: "+card2.getValue()+ "   "+ card2.getMovement()+"\n");
        round.updateWithPlayedAssistant(card2);
        assertEquals("player1",this.round.getActionOrder().get(0).playerId);
        round.setNextPlayer(playerMap);
        assertEquals("player3",round.getCurrentPlayer().getNickName());
        AssistantCard card3 = assistantDeck.get(0);
        System.out.print("card3: "+card3.getValue()+ "   "+ card3.getMovement()+"\n");
        round.updateWithPlayedAssistant(card3);
        assertEquals(Phase.PLANNING,round.getCurrentPhase());
        round.setNextPlayer(playerMap);
        assertEquals(Phase.ACTION,round.getCurrentPhase());
        System.out.print(round.getActionOrder().get(0).playerId+"\n");
        System.out.print(round.getActionOrder().get(1).playerId+"\n");
        assertEquals("player1",round.getActionOrder().get(0).playerId);
        assertEquals("player2",round.getActionOrder().get(1).playerId);
        assertEquals(2,round.getActionOrder().size());
        assertEquals("player3",round.getCurrentPlayer().getNickName());
        round.setNextPlayer(playerMap);
        assertEquals("player1",round.getCurrentPlayer().getNickName());
        round.setNextPlayer(playerMap);
        assertEquals("player2",round.getCurrentPlayer().getNickName());
        round.setNextPlayer(playerMap);
    }

    @Test
    void setNextPlayer() {
//        this.round.setNextPlayer(this.playerMap);
//        assertEquals("player2",this.round.getCurrentPlayer());
//        this.round.updateWithPlayedAssistant(card2);
//        this.round.setNextPlayer(this.playerMap);
//        assertEquals("player3",this.round.getCurrentPlayer());
//        this.round.updateWithPlayedAssistant(card3);
    }

    @Test
    void setActionPhase() {
//        this.round.setNextPlayer(this.playerMap);
//        assertEquals(Phase.ACTION,this.round.getCurrentPhase());
//        assertEquals("player2",this.round.getCurrentPlayer());
    }

    @Test
    void setNextActionTurn() {
//        this.round.setNextPlayer(this.playerMap);
//        assertEquals(Phase.ACTION,this.round.getCurrentPhase());
//        assertEquals("player3",this.round.getCurrentPlayer());
//        assertEquals(1,this.round.getActionOrder().size());
//        this.round.setNextPlayer(this.playerMap);
//        assertEquals("player1",this.round.getCurrentPlayer());
//        assertEquals(0,this.round.getActionOrder().size());
//        assertTrue(round.isEnded());
//        this.round.setNextPlayer(this.playerMap);
//        round = round.initNextRound(playerMap);
//        assertEquals(Phase.PLANNING,round.getCurrentPhase());
//        assertEquals("player2",round.getCurrentPlayer());
    }



}