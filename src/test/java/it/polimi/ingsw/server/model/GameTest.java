package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.model.game.Game;
import it.polimi.ingsw.shared.enums.Phase;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {
    static Game game;
    static Map<String,TowerColour> players;
    @BeforeAll
    static void setUp() throws IOException {
        players = new HashMap<>();
        players.put("player1",TowerColour.BLACK);
        players.put("player2",TowerColour.WHITE);
        List<String> playerNames = new ArrayList<>();
        playerNames.add("player1");
        playerNames.add("player2");
        game = new Game(playerNames,2, false);

    }

    @Test
    void endCurrentPlayerTurn() {
        assertEquals("player1",game.getCurrentPlayer().getNickName());
        game.playAssistantCard(3);
        game.endCurrentPlayerTurn();
        assertEquals("player2",game.getCurrentPlayer().getNickName());
        game.playAssistantCard(2);
        game.endCurrentPlayerTurn();
        assertEquals(Phase.ACTION,game.getRoundPhase());
        assertEquals("player2",game.getCurrentPlayer().getNickName());
        game.endCurrentPlayerTurn();
        assertEquals("player1",game.getCurrentPlayer().getNickName());
        game.endCurrentPlayerTurn();
        assertEquals(Phase.PLANNING,game.getRoundPhase());
    }




//    @Test
//    void addPlayer() throws IOException {
//        this.game.addPlayer("player4", TowerColour.GREY);
//        assertEquals(TowerColour.GREY, this.game.getPlayers().get("player4").getTowerColour());
//    }


//    @Test
//    void moveMotherNature() {
//         this.game.moveMotherNature(4);
//         assertEquals(this.game.getGameBoard().getIsleCircle().get(4), this.game.getMotherNaturePosition());
//    }

    @Test
    void getPlayableAssistants() {
        Set<Integer> playableCards = this.game.getPlayableAssistants();
        assertEquals(game.getPlayers().get("player1").getDeck().keySet(),playableCards);
    }

    @Test
    void playAssistantCardAndEndTurn() {
        game.playAssistantCard(3);
        assertEquals(1, game.getCurrentRound().getActionOrder().size());
        this.game.endCurrentPlayerTurn();
        assertEquals(this.game.getPlayers().get("player2"), this.game.getCurrentPlayer());
    }

//    @Test
//    void getPlayableAssistants() {
//        Set<Integer> playableCards = this.game.getPlayableAssistants();
//        assertEquals(game.getPlayers().get("player1").getDeck().keySet(),playableCards);
//    }
//
//    @Test
//    void playAssistantCardAndEndTurn() {
//        game.playAssistantCard(3);
//        assertEquals(1,game.getCurrentRound().getActionOrder().size());
//        this.game.endCurrentPlayerTurn();
//        assertEquals(this.game.getPlayers().get("player2"), this.game.getCurrentPlayer());
//    }







    /*
    @Test
    void setPlanningOrder() {

        this.game.initPlayingOrder();
        AssistantCard card1 = new AssistantCard(2, 1);
        AssistantCard card2 = new AssistantCard(1,2);
        AssistantCard card3 = new AssistantCard(3,2);

        game.getPlayers().get("player1").setChosenAssistant(card1);
        game.getPlayers().get("player2").setChosenAssistant(card2);
        game.getPlayers().get("player3").setChosenAssistant(card3);
        game.setActionOrder();
        this.game.setPlanningOrder();
        assertEquals(game.getPlayers().get("player2"), game.getPlayingOrder().get(0));
    }
*/
    /*
    @Test
    void setActionOrder() {
        this.game.initPlayingOrder();
        ArrayList<Player> playingOrderRandom = new ArrayList<>();
        for (Player player :this.game.getPlayingOrder()) {

        }

        AssistantCard card1 = new AssistantCard(2, 1);
        AssistantCard card2 = new AssistantCard(2,1);
        AssistantCard card3 = new AssistantCard(1,1);
        game.getPlayers().get("player1").setChosenAssistant(card1);
        game.getPlayers().get("player2").setChosenAssistant(card2);
        game.getPlayers().get("player3").setChosenAssistant(card3);
        game.setActionOrder();
        assertEquals(game.getPlayers().get("player1"), game.getPlayingOrder().get(1));
    }

     */
}


