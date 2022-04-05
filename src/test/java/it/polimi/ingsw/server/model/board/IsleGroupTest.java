package it.polimi.ingsw.server.model.board;

import it.polimi.ingsw.server.model.TowerColour;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IsleGroupTest {

    @Test
    void getSize() {
        System.out.println("getSizeIsleGroup");
        IsleGroup instance = new IsleGroup(1);
        int expResult = 5;
        instance.setSize(5);
        int result = instance.getSize();
        assertEquals(expResult, result);
    }

    @Test
    void setSize() {
        System.out.println("setSizeIsleGroup");
        int size = 3;
        IsleGroup instance = new IsleGroup(1);
        instance.setSize(size);
        assertEquals(instance.getSize(), size);
    }

    @Test
    void getBanned() {
        System.out.println("getBanned");
        IsleGroup instance = new IsleGroup(1);
        instance.setBanned(true);
        assertEquals(true, instance.getBanned());
    }

    @Test
    void setBanned() {
        System.out.println("setBanned");
        IsleGroup instance = new IsleGroup(1);
        boolean isItBanned = true;
        instance.setBanned(true);
        assertEquals(instance.getBanned(), isItBanned);
    }

    @Test
    void getTower() {
        System.out.println("getTower");
        IsleGroup instance = new IsleGroup(1);
        TowerColour expResult = TowerColour.BLACK;
        instance.setTower(TowerColour.BLACK);
        assertEquals(expResult, instance.getTower());
    }

    @Test
    void setTower() {
        System.out.println("setTower");
        IsleGroup instance = new IsleGroup(1);
        TowerColour expTower = TowerColour.BLACK;
        instance.setTower(TowerColour.BLACK);
        assertEquals(instance.getTower(), expTower);
    }

    @Test
    void getIndex() {
        System.out.println("getIndex");
        IsleGroup instance = new IsleGroup(4);
        int expIndex = 4;
        assertEquals(expIndex, instance.getIndex());
    }
}