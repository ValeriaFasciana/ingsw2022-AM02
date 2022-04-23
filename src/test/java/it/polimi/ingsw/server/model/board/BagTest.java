package it.polimi.ingsw.server.model.board;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import it.polimi.ingsw.server.model.PawnColour;
import it.polimi.ingsw.server.model.StudentContainer;

import java.util.EnumMap;

import static org.junit.jupiter.api.Assertions.*;

class BagTest {
    Bag bag;

    @BeforeEach
    private void setUp() {
        this.bag = new Bag();
        this.bag.addStudentsForEachColour(2);
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