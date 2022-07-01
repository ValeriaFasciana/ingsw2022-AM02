package it.polimi.ingsw.server.model.board;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BagTest {
    Bag bag;

    /**
     * Set up of the class before every test
     */
    @BeforeEach
    private void setUp() {
        this.bag = new Bag();
    }

    /**
     * Bag has 10 pawns. Picking up 3 leaves bag with 7 pawns
     */
    @Test
    void pick() {
        Integer expNumber = 7;
        bag.pick(3);

        assertEquals(expNumber, bag.getNumberOfPawns());
    }

    /**
     * Bag has 10 pawns, 10 is the expected number of pawn
     */
    @Test
    void getNumberOfPawns() {
        Integer expNumber = 10;
        assertEquals(expNumber, bag.getNumberOfPawns());

    }
}