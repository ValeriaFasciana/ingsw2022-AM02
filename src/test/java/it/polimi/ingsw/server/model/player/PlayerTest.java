package it.polimi.ingsw.server.model.player;

import it.polimi.ingsw.server.model.AssistantCard;
import it.polimi.ingsw.server.model.PawnColour;
import it.polimi.ingsw.server.model.TowerColour;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.EnumMap;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {
    Player player;
    AssistantCard card1;

    @BeforeEach
    private void setUp() throws IOException {
        this.player = new Player("player1", 7, 3, TowerColour.WHITE,1);
        this.card1 = new AssistantCard(1, 1);
        this.player.setChosenAssistant(card1);

    }

    @Test
    void addStudentsToHall() {
        EnumMap<PawnColour,Integer> studentMap = new EnumMap<PawnColour, Integer>(PawnColour.class);
        studentMap.put(PawnColour.GREEN, 3);
        this.player.addStudentsToHall(studentMap);
        assertEquals(3, this.player.getBoard().getStudentsInTable(PawnColour.GREEN));

    }

    @Test
    void getChosenAssistant() {
        assertEquals(card1, player.getChosenAssistant());
    }

    @Test
    void getNickName() {
        assertEquals("player1", player.getNickName());
    }

    @Test
    void getBoard() {
        player.getBoard().getHall().addStudent(PawnColour.RED);
        assertEquals(1, player.getBoard().getHall().getStudentsByColour(PawnColour.RED));
    }

    @Test
    void setCoins() {
        player.setCoins(3);
        assertEquals(3, player.getCoins());
    }

    @Test
    void setState() {
    }

    @Test
    void setTowerCounter() {
        player.setTowerCounter(3);
        assertEquals(3, player.getTowerCounter());
    }

    @Test
    void setTowerColour() {
        player.setTowerColour(TowerColour.BLACK);
        assertEquals(TowerColour.BLACK, player.getTowerColour());
    }

    @Test
    void setChosenAssistant() {
        AssistantCard card2 = new AssistantCard(2, 2);
        player.setChosenAssistant(card2);
        assertEquals(card2, player.getChosenAssistant());
    }

    @Test
    void playAssistant() {
    }

    @Test
    void getChosenAssistantValue() {
        assertEquals(1, player.getChosenAssistantValue());
    }

    @Test
    void getTowerCounter() {
        assertEquals(3, player.getTowerCounter());
    }

    @Test
    void getChosenAssistantMovements() {
        assertEquals(1, player.getChosenAssistantMovements());
    }

    @Test
    void getStudentsOnHallTable() {
        player.getBoard().getHall().addStudent(PawnColour.RED);
        assertEquals(1, player.getStudentsOnHallTable(PawnColour.RED));
    }

    @Test
    void getDeck() {
    }


    @Test
    void getTowerColour() {
        assertEquals(TowerColour.WHITE, player.getTowerColour());
    }

    @Test
    void getAvailableCards() {
    }

    @Test
    void getAvailableDestination() {
        player.getBoard().getHall().addStudentsForEachColour(7);
        assertEquals(PawnColour.RED, player.getAvailableDestination().get(0));
    }
}