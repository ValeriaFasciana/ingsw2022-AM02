package it.polimi.ingsw.server.model.player;

import it.polimi.ingsw.server.model.PawnColour;
import it.polimi.ingsw.server.model.TowerColour;
import it.polimi.ingsw.server.model.player.playerBoard.PlayerBoard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.EnumMap;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {
    Player player;

    @BeforeEach
    private void setUp() {
        this.player = new Player("testPlayer", 7, 3, TowerColour.WHITE);
    }

    @Test
    void addStudentsToHall() {
        EnumMap<PawnColour,Integer> studentMap = new EnumMap<PawnColour, Integer>(PawnColour.class);
        studentMap.put(PawnColour.GREEN, 3);
        this.player.addStudentsToHall(studentMap);
        assertEquals(3, this.player.getBoard().getStudentsInTable(PawnColour.GREEN));

    }
}