package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.model.cards.AssistantCard;
import it.polimi.ingsw.server.model.cards.characters.CharacterCard;
import it.polimi.ingsw.server.model.game.GameSettings;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class DeserializerTest {

    @Test
    void getSettings() throws IOException {
        Deserializer deserializer = new Deserializer();
        GameSettings settings = deserializer.getSettings(2);
        assertNotNull(settings);
        assertEquals(12,settings.getNumberOfIslands());
        assertEquals(2,settings.getNumberOfClouds());
        assertEquals(8,settings.getNumberOfTowersForPlayer());
        assertEquals(7,settings.getStudentsInEntrance());
        assertEquals(3,settings.getStudentsInClouds());
        assertEquals(3,settings.getNumberOfStudentsToMove());
    }

    @Test
    void getAssistants() throws IOException {
        Deserializer deserializer = new Deserializer();
        Map<Integer, AssistantCard> deck = deserializer.getAssistantDeck();
        assertNotNull(deck);
    }

    @Test
    void getCharacters() throws IOException {
        Deserializer deserializer = new Deserializer();
        Map<Integer, CharacterCard> deck = deserializer.getCharacters();
        assertNotNull(deck);
    }
}