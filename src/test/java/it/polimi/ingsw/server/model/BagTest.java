package it.polimi.ingsw.server.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.EnumMap;

import static org.junit.jupiter.api.Assertions.*;

class BagTest {
    Bag bag;

    @BeforeEach
    public void setUp() {
        this.bag = new Bag();
        assertEquals(10,this.bag.getNumberOfPawns());
    }
    @Test
    void pick() {
        EnumMap<PawnColour, Integer> pickedPawns = this.bag.pick(4);
        for(PawnColour colour : PawnColour.values()){
            System.out.println(colour+ ": " +pickedPawns.get(colour));
        }
        assertEquals(6,this.bag.getNumberOfPawns());
    }


}