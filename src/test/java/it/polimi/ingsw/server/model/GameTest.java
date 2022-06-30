package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.model.board.IsleGroup;
import it.polimi.ingsw.server.model.game.Game;
import it.polimi.ingsw.shared.enums.PawnColour;
import it.polimi.ingsw.shared.enums.Phase;
import it.polimi.ingsw.shared.enums.TowerColour;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {
    static Game game;
    static Map<String, TowerColour> players;
    @BeforeEach
    void setUp() {
        players = new HashMap<>();
        players.put("player1",TowerColour.BLACK);
        players.put("player2",TowerColour.WHITE);
        players.put("player3",TowerColour.GREY);

        List<String> playerNames = new ArrayList<>();
        playerNames.add("player1");
        playerNames.add("player2");
        playerNames.add("player3");
        game = new Game(playerNames,2, false);

    }

    @Test
    void endCurrentPlayerTurn() {
        assertEquals("player1",game.getCurrentPlayer().getNickName());
        System.out.print("\nplayer: "+game.getCurrentPlayer().getNickName()+ "\navailableAssistants:\n"+ game.getPlayableAssistants());
        game.playAssistantCard(3);
        game.endCurrentPlayerTurn();
        assertEquals("player2",game.getCurrentPlayer().getNickName());
        assertFalse(game.getPlayableAssistants().contains(3));
        System.out.print("\nplayer: "+game.getCurrentPlayer().getNickName()+ "\navailableAssistants:\n"+ game.getPlayableAssistants());
        game.playAssistantCard(2);
        game.endCurrentPlayerTurn();
        assertFalse(game.getPlayableAssistants().contains(3));
        assertFalse(game.getPlayableAssistants().contains(2));
        assertEquals(Phase.PLANNING,game.getRoundPhase());
        assertEquals("player3",game.getCurrentPlayer().getNickName());
        game.playAssistantCard(6);
        game.endCurrentPlayerTurn();
        assertEquals(Phase.ACTION,game.getRoundPhase());
        assertEquals("player2",game.getCurrentPlayer().getNickName());
        System.out.print("\nplayer: "+game.getCurrentPlayer().getNickName()+ "\navailableAssistants:\n"+ game.getPlayableAssistants());
        assertEquals(Phase.ACTION,game.getRoundPhase());
        game.endCurrentPlayerTurn();
        assertEquals("player1",game.getCurrentPlayer().getNickName());
        assertEquals(Phase.ACTION,game.getRoundPhase());
        game.endCurrentPlayerTurn();
        assertEquals("player3",game.getCurrentPlayer().getNickName());
        assertEquals(Phase.ACTION,game.getRoundPhase());
        game.endCurrentPlayerTurn();
        assertEquals(Phase.PLANNING,game.getRoundPhase());
        assertEquals("player2",game.getCurrentPlayer().getNickName());
        game.playAssistantCard(5);
        game.endCurrentPlayerTurn();
        assertEquals("player3",game.getCurrentPlayer().getNickName());
        assertEquals(Phase.PLANNING,game.getRoundPhase());
        game.playAssistantCard(7);
        game.endCurrentPlayerTurn();
        assertEquals(Phase.PLANNING,game.getRoundPhase());
        assertEquals("player1",game.getCurrentPlayer().getNickName());
        game.playAssistantCard(9);
        game.endCurrentPlayerTurn();
        assertEquals(Phase.ACTION,game.getRoundPhase());
        assertEquals("player2",game.getCurrentPlayer().getNickName());

    }

    @Test
    void influenceCalculationTest(){
        game.getProfessorMap().get(PawnColour.YELLOW).setPlayer("player1");
        Map<PawnColour,Integer> yellowStudentMap = new HashMap<>();
        yellowStudentMap.put(PawnColour.YELLOW,3);
        game.getGameBoard().getIsleCircle().get(3).empty();
        game.getGameBoard().getIsleCircle().get(3).addStudents(yellowStudentMap);
        game.calculateInfluence(3,Optional.empty());
        assertEquals(TowerColour.BLACK,game.getGameBoard().getIsleCircle().get(3).getTower());
        game.getProfessorMap().get(PawnColour.BLUE).setPlayer("player2");
        Map<PawnColour,Integer> blueStudentMap = new HashMap<>();
        blueStudentMap.put(PawnColour.BLUE,3);
        game.getGameBoard().getIsleCircle().get(3).addStudents(blueStudentMap);
        game.calculateInfluence(3,Optional.empty());
        assertEquals(TowerColour.BLACK,game.getGameBoard().getIsleCircle().get(3).getTower());
        Map<PawnColour,Integer> redStudentMap = new HashMap<>();
        game.getProfessorMap().get(PawnColour.RED).setPlayer("player3");
        redStudentMap.put(PawnColour.RED,4);
        game.getGameBoard().getIsleCircle().get(3).addStudents(redStudentMap);
        game.calculateInfluence(3,Optional.empty());
        assertEquals(TowerColour.BLACK,game.getGameBoard().getIsleCircle().get(3).getTower());
        game.getProfessorMap().get(PawnColour.GREEN).setPlayer("player3");
        Map<PawnColour,Integer> greenStudentMap = new HashMap<>();
        greenStudentMap.put(PawnColour.GREEN,1);
        game.getGameBoard().getIsleCircle().get(3).addStudents(greenStudentMap);
        game.calculateInfluence(3,Optional.empty());
        //assertEquals(TowerColour.GREY,game.getGameBoard().getIsleCircle().get(3).getTower());
    }



    @Test
    void moveMotherNature() {
//        this.game.getGameBoard().getIsleCircle().printList();
//        System.out.println("MotherNaturePosition: "+this.game.getGameBoard().getMotherNaturePosition());
//        this.game.moveMotherNature(4);
//        System.out.println("MotherNaturePosition: "+this.game.getGameBoard().getMotherNaturePosition());

    }


    void playAssistantCardAndEndTurn() {
//        game.playAssistantCard(3);
//        assertEquals(1, game.getCurrentRound().getActionOrder().size());
//        this.game.endCurrentPlayerTurn();
//        assertEquals(this.game.getPlayers().get("player2"), this.game.getCurrentPlayer());
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


