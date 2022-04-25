package it.polimi.ingsw.server.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CharacterContextTest {
    CharacterContext characterContext;

    @BeforeEach
    private void setUp() {
        this.characterContext = new CharacterContext(5);
    }

    @Test
    void getId() {
        assertEquals(5, characterContext.getId());
    }

    @Test
    void getPrice() {
        assertEquals(2, characterContext.getPrice());
    }

    @Test
    void isAlreadyPlayed() {
        assertEquals(false, characterContext.isAlreadyPlayed());
    }

    @Test
    void setAlreadyPlayed() {
        characterContext.setAlreadyPlayed(true);
        assertEquals(true, characterContext.isAlreadyPlayed());
    }
}