package it.polimi.ingsw.server.model.board;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

class GameBoardTest {
    GameBoard gameBoard;
    @BeforeEach
    public void setUp() {
        this.gameBoard = new GameBoard(2,12,5);
    }
//
//    @Test
//    void getIsleCircle() {
//        assertEquals(gameBoard.getIsleCircle().head, gameBoard.getIsleCircle().get(11));
//    }


//    @Test
//    void getMotherNatureOppositeIsland() {
//        IsleGroup expIsle = this.gameBoard.getIsleCircle().get(6);
//        this.gameBoard.moveMotherNatureTo(0);
//        assertEquals(expIsle, this.gameBoard.getMotherNatureOppositeIsland());
//
//    }

    /*
    @Test
    void addStudentsToCloud() {

        Cloud cloud = this.gameBoard.getClouds().get(0);
        this.gameBoard.addStudentsToCloud(3);

        assertEquals(3, cloud.getNumberOfStudents());
    }
    */

}