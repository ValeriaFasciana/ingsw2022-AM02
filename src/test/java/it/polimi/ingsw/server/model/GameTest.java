package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.model.board.GameBoard;
import it.polimi.ingsw.server.model.board.IsleCircle;
import it.polimi.ingsw.server.model.board.IsleGroup;
import it.polimi.ingsw.server.model.characters.CharacterCard;
import it.polimi.ingsw.server.model.player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {
    Game game;
    HashMap<String,Player> playerMap = new HashMap<>();
    GameBoard gameBoard =new GameBoard(3,12,3);
    ArrayList<Player> playingOrder;

     @BeforeEach
    private void setUp() throws IOException {
        Player player1 = new Player("player1",3,8, TowerColour.BLACK,1);
        Player player2 = new Player("player2",3,8, TowerColour.WHITE,1);
        this.playerMap.put("player1",player1);
        this.playerMap.put("player2",player2);
        this.game = new Game(this.playerMap, 2, false);
    }

    @Test
    void addPlayer() throws IOException {
        this.game.addPlayer("player3", TowerColour.GREY,1);
        assertEquals(TowerColour.GREY, this.game.getPlayers().get("player3").getTowerColour());
    }

    @Test
    void moveMotherNature() {
         this.game.moveMotherNature(4);
         assertEquals(this.game.getGameBoard().getIsleCircle().get(4), this.game.getMotherNaturePosition());
    }

    @Test
    void getMotherNaturePosition() {
        this.game.moveMotherNature(4);
        assertEquals(this.game.getGameBoard().getIsleCircle().get(4), this.game.getMotherNaturePosition());
    }
/*
    @Test
    void winningConditions() {
    }

    @Test
    void actionPhase() {
    }
    @Test
    void setCurrentPlayer() {

    }
    @Test
    void getPlayableAssistants() {
    }

    @Test
    void updateTurnWithPlayedAssistant() {
    }

    @Test
    void startPlanningPhase() {
    }

    @Test
    void assignProfessors() {
    }
*/
    @Test
    void getPlayers() {
        assertEquals(TowerColour.BLACK, game.getPlayers().get("player1").getTowerColour());
    }

    @Test
    void getGameBoard() {
         IsleGroup isleGroup = game.getGameBoard().getMotherNaturePosition();
         assertEquals(isleGroup, game.getGameBoard().getMotherNaturePosition());
    }

    @Test
    void getNumberOfPlayers() {
         assertEquals(2, game.getNumberOfPlayers());
    }

    @Test
    void getSettings() {
         assertEquals(3, game.getSettings().getStudentsInClouds());
    }
/*
    @Test
    void getProfessorMap() {

    }

    @Test
    void initPlayingOrder() {
    }

    @Test
    void setPlanningOrder() {
    }

    @Test
    void setActionOrder() {
    }

    @Test
    void getPlayingOrder() {
    }
    */
}