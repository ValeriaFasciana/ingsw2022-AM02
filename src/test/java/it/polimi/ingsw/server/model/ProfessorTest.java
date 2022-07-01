package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.model.player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class ProfessorTest {
    Professor professor = new Professor();

/*
    @Test
    void getCounter() {
    }

    @Test
    void getPlayer() {
    }
*/

    /**
     * setting the player "player", .getPlayer() returns "player"
     */
    @Test
    void setPlayer() {
        professor.setPlayer("player");
        assertEquals("player", professor.getPlayer());
    }
    /**
     * setting the counter to1, getCounter returns 1
     */
    @Test
    void setCounter() {
        professor.setCounter(1);
        assertEquals(1, professor.getCounter());
    }
}