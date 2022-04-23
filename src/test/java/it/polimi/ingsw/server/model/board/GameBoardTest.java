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

    @Test
    void getMotherNaturePosition() {
        IsleGroup expIsle = this.gameBoard.getIsleCircle().get(4);
        this.gameBoard.moveMotherNatureTo(4);
        assertEquals(expIsle, this.gameBoard.getMotherNaturePosition());
    }

    @Test
    void moveMotherNatureTo() {
        int expIndex = 11;
        IsleGroup expIsle = this.gameBoard.getIsleCircle().get(expIndex);
        this.gameBoard.moveMotherNatureTo(expIndex);
        assertEquals(expIsle, this.gameBoard.getMotherNaturePosition());
    }

    @Test
    void getMotherNatureOppositeIsland() {
        IsleGroup expIsle = this.gameBoard.getIsleCircle().get(6);
        this.gameBoard.moveMotherNatureTo(0);
        assertEquals(expIsle, this.gameBoard.getMotherNatureOppositeIsland());

    }

    /*
    @Test
    void addStudentsToCloud() {

        Cloud cloud = this.gameBoard.getClouds().get(0);
        this.gameBoard.addStudentsToCloud(3);

        assertEquals(3, cloud.getNumberOfStudents());
    }
    */

}