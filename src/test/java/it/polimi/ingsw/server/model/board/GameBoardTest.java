package it.polimi.ingsw.server.model.board;

import it.polimi.ingsw.shared.enums.TowerColour;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class GameBoardTest {
    GameBoard gameBoard;
    @BeforeEach
    public void setUp() {
        this.gameBoard = new GameBoard(2,12,5);

    }

    @Test
    void motherNatureMovementTest() {
        gameBoard.moveMotherNatureTo(5);
        assertEquals(5,gameBoard.getMotherNaturePosition());
        Set<Integer> motherNatureNextIslands = gameBoard.getMotherNatureNextIslands(4);
        assertArrayEquals(new Integer[]{6, 7, 8, 9},motherNatureNextIslands.toArray());
    }

    @Test
    void isleBanTest() {
        gameBoard.setBanOnIsle(4);
        assertTrue(gameBoard.isIsleBanned(4));
        assertEquals(1,gameBoard.getIsleCircle().get(4).getBanCounter());
        gameBoard.moveMotherNatureTo(4);
        assertFalse(gameBoard.isIsleBanned(4));
        assertEquals(0,gameBoard.getIsleCircle().get(4).getBanCounter());
    }

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

    @Test
    void towerPlacingTest(){
       gameBoard.placeTowerOnIsle(5, TowerColour.BLACK);
       assertEquals(TowerColour.BLACK,gameBoard.getIsleTowerColour(5));
       gameBoard.placeTowerOnIsle(6, TowerColour.BLACK);
       gameBoard.manageIsleMerge(5);
       assertEquals(11,gameBoard.getIsleCircle().getSize());

    }





}