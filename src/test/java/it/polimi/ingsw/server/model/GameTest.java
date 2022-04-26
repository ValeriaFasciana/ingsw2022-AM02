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
    Player[] playerOrder;

     @BeforeEach
    private void setUp() throws IOException {
        Player player1 = new Player("player1",3,8, TowerColour.BLACK,1);
        Player player2 = new Player("player2",3,8, TowerColour.WHITE,1);
        Player player3 = new Player("player3",3,8, TowerColour.GREY,1);
        this.playerMap.put("player1",player1);
        this.playerMap.put("player2",player2);
        this.playerMap.put("player3",player2);

        this.game = new Game(this.playerMap,3, false);
        this.playerOrder = new Player[game.getNumberOfPlayers()];
        this.playingOrder = new ArrayList<>();
    }

    @Test
    void addPlayer() throws IOException {
        this.game.addPlayer("player4", TowerColour.GREY,1);
        assertEquals(TowerColour.GREY, this.game.getPlayers().get("player4").getTowerColour());
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
         assertEquals(3, game.getNumberOfPlayers());
    }

    @Test
    void getSettings() {
         assertEquals(4, game.getSettings().getStudentsInClouds());
    }
/*
    @Test
    void getProfessorMap() {

    }
*/
    @Test
    void initPlayingOrder() {
        this.game.initPlayingOrder();
        playingOrder = this.game.getPlayingOrder();
        assertEquals(true, this.playingOrder.contains(this.game.getPlayers().get("player1")));
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
    @Test
    void setActionOrder() {
        this.game.initPlayingOrder();
        AssistantCard card1 = new AssistantCard(2, 1);
        AssistantCard card2 = new AssistantCard(1,2);
        game.getPlayers().get("player1").setChosenAssistant(card1);
        game.getPlayers().get("player2").setChosenAssistant(card2);
        game.setActionOrder();
        assertEquals(game.getPlayers().get("player2"), game.getPlayingOrder().get(0));
    }
}