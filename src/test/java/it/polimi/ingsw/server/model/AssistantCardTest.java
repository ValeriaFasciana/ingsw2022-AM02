package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.model.cards.AssistantCard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AssistantCardTest {
    AssistantCard card;

    /**
     * set up before tests
     */
    @BeforeEach
    public void setUp() {
        card = new AssistantCard(0,5, 5);
    }


    /**
     * value of created assistant card is 5, so getValue returns 5
     */
    @Test
    void getValue() {
        assertEquals(5,this.card.getValue());
    }

    /**
     * set up the value of card 6, getValue returns 6
     */
    @Test
    void setValue() {
        this.card.setValue(6);
        assertEquals(6,this.card.getValue());
    }

    /**
     * value of card is 5, so getMovement returns 5
     */
    @Test
    void getMovement() {
        assertEquals(5,this.card.getMovement());

    }
    /**
     * set up the value of card 6, getValue returns 6
     */
    @Test
    void setMovement() {
        this.card.setValue(6);
        assertEquals(6,this.card.getValue());
    }
}