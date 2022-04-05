package it.polimi.ingsw.server.model.board;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MotherNatureTest {

    @Test
    void getPosition() {
        System.out.println("getPosition");
        MotherNature motherNature = new MotherNature(new IsleGroup(3));
        IsleGroup isle = new IsleGroup(4);
        motherNature.setPosition(isle);
        int expPosition = 4;
        assertEquals(expPosition, motherNature.getPosition().getIndex());
    }

    @Test
    void setPosition() {
        System.out.println("setPosition");
        MotherNature motherNature = new MotherNature(new IsleGroup(4));
        IsleGroup expPosition = new IsleGroup(5);
        motherNature.setPosition(expPosition);
        int indexMother = 5;
        assertEquals(indexMother, motherNature.getPosition().getIndex());

    }
}