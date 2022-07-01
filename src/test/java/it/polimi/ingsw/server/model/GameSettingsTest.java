package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.model.game.GameSettings;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameSettingsTest {
    GameSettings settings;

    /**
     * set up before every tests
     */
    @BeforeEach
    public void setUp() {
        this.settings = new GameSettings(2,12, 2,7,7,3,3);
    }

    /**
     * from the setting, getNumberOfIslands returns 12
     */
    @Test
    void getNumberOfIslands() {
        assertEquals(12, settings.getNumberOfIslands());
    }
    /**
     * from the setting, getNumberOfClouds returns 2
     */
    @Test
    void getNumberOfClouds() {
        assertEquals(2, settings.getNumberOfClouds());
    }
    /**
     * from the setting, getNumberOfTowersForPlayer returns 7
     */
    @Test
    void getNumberOfTowersForPlayer() {
        assertEquals(7, settings.getNumberOfTowersForPlayer());
    }
    /**
     * from the setting, getStudentsInEntrance returns 7
     */
    @Test
    void getStudentsInEntrance() {
        assertEquals(7, settings.getStudentsInEntrance());
    }
    /**
     * from the setting, getStudentsInClouds returns 3
     */
    @Test
    void getStudentsInClouds() {
        assertEquals(3, settings.getStudentsInClouds());
    }
    /**
     * from the setting, getNumberOfStudentsToMove returns 3
     */
    @Test
    void getNumberOfStudentsToMove() {
        assertEquals(3, settings.getNumberOfStudentsToMove());
    }
}