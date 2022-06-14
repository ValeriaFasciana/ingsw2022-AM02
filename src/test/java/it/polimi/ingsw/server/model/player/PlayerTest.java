package it.polimi.ingsw.server.model.player;

import it.polimi.ingsw.server.model.cards.AssistantCard;
import it.polimi.ingsw.shared.enums.PawnColour;
import it.polimi.ingsw.shared.enums.TowerColour;
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

    }


/* game in Player.playAssistant non viene inizializzato
    @Test
    void playAssistant() {
        player.playAssistant(1);
        assertFalse(player.getDeck().containsValue(player.getDeck().get(1)));
    }
*/




//    @Test
//    void getAvailableCards() {
//        Turn turn = new Turn(Phase.PLANNING, player);
//        assertEquals(10, player.getAvailableCards(turn).size());
//    }

//    @Test
//    void getAvailableDestination() {
//        player.getBoard().getHall().addStudentsForEachColour(7);
//        assertEquals(PawnColour.RED, player.getAvailableDestination().get(0));
//    }
}