package it.polimi.ingsw.server.model.board;

import it.polimi.ingsw.shared.enums.PawnColour;
import it.polimi.ingsw.shared.enums.TowerColour;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IsleCircleTest {
    static IsleCircle isleCircle;
    static MotherNature motherNature;

    /**
     * Set up of the class before every test
     */
    @BeforeAll
    public static void setUp() {
        isleCircle = new IsleCircle(12);
        motherNature = new MotherNature(3);
        assertEquals(12,isleCircle.getSize());
        isleCircle.initialPopulation(motherNature.getPosition(),new Bag());
        isleCircle.printList();
        assertEquals(7,isleCircle.getNextIndexOf(6));
        assertEquals(0,isleCircle.getNextIndexOf(11));
    }

    /**
     * set black tower on both isle 2 and 3
     * merging them, the size of the isle circle should be 11
     * mother nature goes back to isle index of the first merged isle (2)
     * set black tower on isle 3, merging it with the previous merge,
     * makes get size of isle circle go to 10
     *
     * the same happens with tower of different colour
     */
    @Test
    void testMerge(){
        isleCircle.get(2).setTower(TowerColour.BLACK);
        isleCircle.get(3).setTower(TowerColour.BLACK);
        isleCircle.manageIsleMerge(2,motherNature);
        assertEquals(2,motherNature.getPosition());
        assertEquals(11,isleCircle.getSize());
        isleCircle.printList();
        isleCircle.get(3).setTower(TowerColour.BLACK);
        isleCircle.manageIsleMerge(3,motherNature);
        isleCircle.printList();
        assertEquals(10,isleCircle.getSize());

        isleCircle.get(0).setTower(TowerColour.GREY);
        isleCircle.get(9).setTower(TowerColour.GREY);
        isleCircle.manageIsleMerge(9,motherNature);
        isleCircle.printList();
        assertEquals(9,isleCircle.getSize());
        isleCircle.get(1).setTower(TowerColour.BLACK);
        isleCircle.manageIsleMerge(2,motherNature);
        isleCircle.printList();

//        assertEquals(8,isleCircle.getSize());
//        isleCircle.get(7).setTower(TowerColour.BLACK);
//        isleCircle.manageIsleMerge(8,motherNature);
//        assertEquals(8,isleCircle.getSize());
//        isleCircle.printList();
//
//        isleCircle.get(6).setTower(TowerColour.BLACK);
//        isleCircle.manageIsleMerge(6,motherNature);
//        isleCircle.printList();
//
//        assertEquals(7,isleCircle.getSize());
//
//        isleCircle.printList();
//        isleCircle.get(3).setTower(TowerColour.BLACK);
//        isleCircle.manageIsleMerge(3,motherNature);
//        assertEquals(7,isleCircle.getSize());
//        isleCircle.printList();

    }


    /**
     * isle 4 is the next isle of 3
     */
    @Test
    void get() {
        IsleGroup isle = isleCircle.get(4);
        assertEquals(isle, isleCircle.get(3).getNext());

    }

    @Test
    void getNextIslands() {
//        IsleGroup isle = new IsleGroup();
//        isle = isleCircle.get(0);
//        int expSize = 5;
//        int testedSize = isleCircle.getNextIslands(isle, 4).size();
//        assertEquals(expSize, testedSize);
    }



    @Test
    void getOppositeOfIsle() {
        //assertEquals(isleCircle.get(0), isleCircle.getOppositeOfIsle(6));
    }

    @Test
    void initialPopulation() {
//        Bag bag = new Bag();
//        isleCircle.initialPopulation(1, bag);
//        int student = 0;
//
//        for(PawnColour colour : PawnColour.values()) {
//            student = student + isleCircle.get(2).getStudentsByColour(colour);
//        }
//        assertEquals(1, student);
    }

//    @Test
//    void addStudentsToIsle() {
//        EnumMap<PawnColour,Integer> studentMap = new EnumMap<PawnColour, Integer>(PawnColour.class);
//        studentMap.put(PawnColour.GREEN, 3);
//        isleCircle.addStudentsToIsle(2, studentMap);
//
//        int student = 0;
//        for(PawnColour colour : PawnColour.values()) {
//            student = student + isleCircle.get(2).getStudentsByColour(colour);
//        }
//        assertEquals(3, student);
//    }
}