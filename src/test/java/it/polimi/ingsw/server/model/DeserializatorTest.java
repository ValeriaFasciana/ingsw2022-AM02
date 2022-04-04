package it.polimi.ingsw.server.model;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class DeserializatorTest {

    @Test
    void getSettings() throws IOException {
        GameSettings settings = Deserializator.getSettings(2);
        assertNotNull(settings);
        assertEquals(12,settings.getNumberOfIslands());
        assertEquals(2,settings.getNumberOfClouds());
        assertEquals(8,settings.getNumberOfTowersForPlayer());
        assertEquals(7,settings.getStudentsInEntrance());
        assertEquals(3,settings.getStudentsInClouds());
        assertEquals(3,settings.getNumberOfStudentsToMove());

    }
}