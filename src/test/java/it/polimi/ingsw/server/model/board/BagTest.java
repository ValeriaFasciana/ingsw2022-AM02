package it.polimi.ingsw.server.model.board;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BagTest {
    Bag bag;

    @BeforeEach
    private void setUp() {
        this.bag = new Bag();
    }
    @Test
    void pick() {
        Integer expNumber = 7;
        bag.pick(3);

        assertEquals(expNumber, bag.getNumberOfPawns());
    }

    @Test
    void getNumberOfPawns() {
        Integer expNumber = 10;
        assertEquals(expNumber, bag.getNumberOfPawns());

    }
}