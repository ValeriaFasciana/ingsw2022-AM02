package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.model.player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class ProfessorTest {
    Professor professor = new Professor();

    @BeforeEach
    private void setUp() throws IOException {
        Player player = new Player("player1", 3, 7, TowerColour.WHITE, 1);
    }
/*
    @Test
    void getCounter() {
    }

    @Test
    void getPlayer() {
    }
*/
    @Test
    void setPlayer() {
        professor.setPlayer("player");
        assertEquals("player", professor.getPlayer());
    }

    @Test
    void setCounter() {
        professor.setCounter(1);
        assertEquals(1, professor.getCounter());
    }
}