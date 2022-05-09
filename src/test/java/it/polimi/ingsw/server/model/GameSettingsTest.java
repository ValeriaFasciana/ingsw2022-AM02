package it.polimi.ingsw.server.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameSettingsTest {
    GameSettings settings;

    @BeforeEach
    public void setUp() {
        this.settings = new GameSettings(2,12, 2,7,7,3,3);
    }

    @Test
    void getNumberOfIslands() {
        assertEquals(12, settings.getNumberOfIslands());
    }

    @Test
    void getNumberOfClouds() {
        assertEquals(2, settings.getNumberOfClouds());
    }

    @Test
    void getNumberOfTowersForPlayer() {
        assertEquals(7, settings.getNumberOfTowersForPlayer());
    }

    @Test
    void getStudentsInEntrance() {
        assertEquals(7, settings.getStudentsInEntrance());
    }

    @Test
    void getStudentsInClouds() {
        assertEquals(3, settings.getStudentsInClouds());
    }

    @Test
    void getNumberOfStudentsToMove() {
        assertEquals(3, settings.getNumberOfStudentsToMove());
    }
}