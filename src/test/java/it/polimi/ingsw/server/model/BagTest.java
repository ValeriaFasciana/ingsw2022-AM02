package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.model.board.Bag;
import it.polimi.ingsw.shared.enums.PawnColour;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.EnumMap;

import static org.junit.jupiter.api.Assertions.*;

class BagTest {
    Bag bag;

    /**
     * set up before tests
     */
    @BeforeEach
    public void setUp(){
        this.bag = new Bag();
        assertEquals(10,this.bag.getNumberOfPawns());
    }

    /**
     * in the bag there are 10 students, if you pick 4, bag.getNumberOfPawns
     * returns 6
     */
    @Test
    void pick() {
        EnumMap<PawnColour, Integer> pickedPawns = (EnumMap<PawnColour, Integer>) this.bag.pick(4);
        for(PawnColour colour : PawnColour.values()){
            System.out.println(colour+ ": " +pickedPawns.get(colour));
        }
        assertEquals(6,this.bag.getNumberOfPawns());
    }


}