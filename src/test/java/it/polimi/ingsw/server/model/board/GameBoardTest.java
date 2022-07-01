package it.polimi.ingsw.server.model.board;

import it.polimi.ingsw.shared.enums.TowerColour;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class GameBoardTest {
    GameBoard gameBoard;

    /**
     * Set up of the class before every test
     */

    @BeforeEach
    public void setUp() {
        this.gameBoard = new GameBoard(2,12,5);

    }


    /**
     * Move mother nature to isle 5. The mother nature position is expected to be 5
     * Playing assistant card with value 4 gives mother nature the
     * possibility to move on one of the next 4 islands
     */
    @Test
    void motherNatureMovementTest() {
        gameBoard.moveMotherNatureTo(5);
        assertEquals(5,gameBoard.getMotherNaturePosition());
        Set<Integer> motherNatureNextIslands = gameBoard.getMotherNatureNextIslands(4);
        assertArrayEquals(new Integer[]{6, 7, 8, 9},motherNatureNextIslands.toArray());
    }


    /**
     * Set the ban on isle 4. Is Isle Banned is true
     * Only 1 ban was set, so getBanCounter on isle 4 returns 1
     * When moving mother nature to island 4, it removes the ban, so isle ban
     * on isle 4 is now false
     */
    @Test
    void isleBanTest() {
        gameBoard.setBanOnIsle(4);
        assertTrue(gameBoard.isIsleBanned(4));
        assertEquals(1,gameBoard.getIsleCircle().get(4).getBanCounter());
        gameBoard.moveMotherNatureTo(4);
        assertFalse(gameBoard.isIsleBanned(4));
        assertEquals(0,gameBoard.getIsleCircle().get(4).getBanCounter());
    }


    /**
     * Students on cloud is set to 5 in the setup, so getStudentsOnCloud returns 5
     * The maximum students when there are 2 clouds is 5, so isFull returns true
     * cloud 1 is emptied, so isEmpty returns true
     * addStudentsToCloud adds only 3 students on cloud 1, so isFull returns false
     * addStudentsToCloud adds 4 students, but it would be too many students, so getNumberOfStudents
     * keeps returning only 3
     */
    @Test
    void cloudTest(){
        assertEquals(5,gameBoard.getStudentsOnCloud(1).size());
        assertTrue(gameBoard.getClouds().get(1).isFull());
        gameBoard.addStudentsToClouds(3);
        assertTrue(gameBoard.getClouds().get(1).isFull());
        gameBoard.emptyCloud(1);
        assertTrue(gameBoard.getClouds().get(1).isEmpty());
        assertFalse(gameBoard.getClouds().get(1).isFull());
        gameBoard.addStudentsToClouds(3);
        assertFalse(gameBoard.getClouds().get(1).isFull());
        gameBoard.addStudentsToClouds(4);
        assertEquals(3,gameBoard.getClouds().get(1).getNumberOfStudents());
    }


    /**
     * placeTowerOnIsle places tower.black on isle 5, so getIsleTowerColour returns 5
     * placeTowerOnIsle places tower.black on isle 6, so now 5 and 6 can merge
     * after merging, get size of isle circle returns 11
     */
    @Test
    void towerPlacingTest(){
       gameBoard.placeTowerOnIsle(5, TowerColour.BLACK);
       assertEquals(TowerColour.BLACK,gameBoard.getIsleTowerColour(5));
       gameBoard.placeTowerOnIsle(6, TowerColour.BLACK);
       gameBoard.manageIsleMerge(5);
       assertEquals(11,gameBoard.getIsleCircle().getSize());

    }





}