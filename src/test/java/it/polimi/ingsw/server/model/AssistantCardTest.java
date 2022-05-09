package it.polimi.ingsw.server.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AssistantCardTest {
    AssistantCard card;

    @BeforeEach
    public void setUp() {
        card = new AssistantCard(0,5, 5);
    }


    @Test
    void getValue() {
        assertEquals(5,this.card.getValue());
    }

    @Test
    void setValue() {
        this.card.setValue(6);
        assertEquals(6,this.card.getValue());
    }

    @Test
    void getMovement() {
        assertEquals(5,this.card.getMovement());

    }

    @Test
    void setMovement() {
        this.card.setValue(6);
        assertEquals(6,this.card.getValue());
    }
}