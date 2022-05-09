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

    @BeforeAll
    static void setUp() throws IOException {
        Map<String,TowerColour> players = new HashMap<>();
        players.put("player1",TowerColour.BLACK);
        players.put("player2",TowerColour.WHITE);
        players.put("player3",TowerColour.GREY);
        game = new Game(players,3, false);

    }

    @Test
    void addPlayer() throws IOException {
        this.game.addPlayer("player4", TowerColour.GREY);
        assertEquals(TowerColour.GREY, this.game.getPlayers().get("player4").getTowerColour());
    }

    @Test
    void moveMotherNature() {
         this.game.moveMotherNature(4);
         assertEquals(this.game.getGameBoard().getIsleCircle().get(4), this.game.getMotherNaturePosition());
    }

    @Test
    void getPlayableAssistants() {
        Set<Integer> playableCards = this.game.getPlayableAssistants();
        assertEquals(game.getPlayers().get("player1").getDeck().keySet(),playableCards);
    }

    @Test
    void playAssistantCardAndEndTurn() {
        game.playAssistantCard(3);
        assertEquals(1,game.getCurrentRound().getActionOrder().size());
        this.game.endCurrentPlayerTurn();
        assertEquals(this.game.getPlayers().get("player2"), this.game.getCurrentPlayer());
    }


    @Test
    void getMotherNaturePosition() {
        this.game.moveMotherNature(4);
        assertEquals(this.game.getGameBoard().getIsleCircle().get(4), this.game.getMotherNaturePosition());
    }

    @Test
    void getPlayers() {
        assertEquals(TowerColour.BLACK, game.getPlayers().get("player1").getTowerColour());
    }

    @Test
    void getGameBoard() {
         IsleGroup isleGroup = game.getGameBoard().getMotherNaturePosition();
         assertEquals(isleGroup, game.getGameBoard().getMotherNaturePosition());
    }

}