package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.model.board.GameBoard;
import it.polimi.ingsw.server.model.board.IsleCircle;
import it.polimi.ingsw.server.model.board.IsleGroup;
import it.polimi.ingsw.server.model.characters.CharacterCard;
import it.polimi.ingsw.server.model.player.Player;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {
    static Game game;


    @BeforeEach
    private void setUp () throws IOException {
//            Player player1 = new Player("player1", 3, 8, TowerColour.BLACK, 1);
//            Player player2 = new Player("player2", 3, 8, TowerColour.WHITE, 1);
//            Player player3 = new Player("player3", 3, 8, TowerColour.GREY, 1);
//            this.playerMap.put("player1", player1);
//            this.playerMap.put("player2", player2);
//            this.playerMap.put("player3", player3);

    }

    @Test
    void playAssistantCardAndEndTurn () {
        game.playAssistantCard(3);
        assertEquals(1, game.getCurrentRound().getActionOrder().size());
        this.game.endCurrentPlayerTurn();
        assertEquals(this.game.getPlayers().get("player2"), this.game.getCurrentPlayer());
    }


    @Test
    void getMotherNaturePosition () {
        this.game.moveMotherNature(4);
        assertEquals(this.game.getGameBoard().getIsleCircle().get(4), this.game.getMotherNaturePosition());
    }

    @Test
    void getPlayers () {
        assertEquals(TowerColour.BLACK, game.getPlayers().get("player1").getTowerColour());
    }

    @Test
    void getGameBoard () {
        IsleGroup isleGroup = game.getGameBoard().getMotherNaturePosition();
        assertEquals(isleGroup, game.getGameBoard().getMotherNaturePosition());
    }




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

