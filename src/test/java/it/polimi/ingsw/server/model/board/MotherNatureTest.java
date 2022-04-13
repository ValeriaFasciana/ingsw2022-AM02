package it.polimi.ingsw.server.model.board;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MotherNatureTest {

    @Test
    void getPosition() {
        System.out.println("getPosition");
        MotherNature motherNature = new MotherNature(new IsleGroup());
        IsleGroup isle = new IsleGroup();
        motherNature.setPosition(isle);
        int expPosition = 4;
        assertEquals(expPosition, motherNature.getPosition());
    }

    @Test
    void setPosition() {
        System.out.println("setPosition");
        MotherNature motherNature = new MotherNature(new IsleGroup());
        IsleGroup expPosition = new IsleGroup();
        motherNature.setPosition(expPosition);
        int indexMother = 5;
        assertEquals(indexMother, motherNature.getPosition());

    }
}