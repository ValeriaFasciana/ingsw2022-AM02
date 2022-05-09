package it.polimi.ingsw.server.model.board;

import it.polimi.ingsw.server.model.PawnColour;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.EnumMap;

import static org.junit.jupiter.api.Assertions.*;

class IsleCircleTest {
    IsleCircle isleCircle;
    @BeforeEach
    public void setUp() {
        this.isleCircle = new IsleCircle(12);
    }

    @Test
    void addIsles() {
        assertEquals(12, isleCircle.getSize());
    }

    @Test
    void getSize() {
        assertEquals(12, isleCircle.getSize());
    }


    @Test
    void get() {
        IsleGroup isle = new IsleGroup();
        isle = isleCircle.get(4);
        assertEquals(isle, isleCircle.get(3).getNext());

    }

    @Test
    void getNextIslands() {
        IsleGroup isle = new IsleGroup();
        isle = isleCircle.get(0);
        int expSize = 5;
        int testedSize = isleCircle.getNextIslands(isle, 4).size();
        assertEquals(expSize, testedSize);
    }

    @Test
    void remove() {
        isleCircle.remove(2);
        assertEquals(11, isleCircle.getSize());
    }

    @Test
    void getOppositeOfIsle() {
        assertEquals(isleCircle.get(0), isleCircle.getOppositeOfIsle(isleCircle.get(6)));
    }

    @Test
    void initialPopulation() {
        Bag bag = new Bag();
        isleCircle.initialPopulation(isleCircle.get(1), bag);
        int student = 0;

        for(PawnColour colour : PawnColour.values()) {
            student = student + isleCircle.get(2).getStudentsByColour(colour);
        }
        assertEquals(1, student);
    }

    @Test
    void addStudentsToIsle() {
        EnumMap<PawnColour,Integer> studentMap = new EnumMap<PawnColour, Integer>(PawnColour.class);
        studentMap.put(PawnColour.GREEN, 3);
        isleCircle.addStudentsToIsle(2, studentMap);

        int student = 0;
        for(PawnColour colour : PawnColour.values()) {
            student = student + isleCircle.get(2).getStudentsByColour(colour);
        }
        assertEquals(3, student);
    }
}