package it.polimi.ingsw.server.model.player.playerBoard;

import it.polimi.ingsw.server.model.PawnColour;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.EnumMap;

import static org.junit.jupiter.api.Assertions.*;

class PlayerBoardTest {
    PlayerBoard playerBoard;
    EnumMap<PawnColour,Integer> studentMap;

    @BeforeEach
    public void setUp() {
        this.playerBoard = new PlayerBoard(7);
        this.studentMap = new EnumMap<PawnColour, Integer>(PawnColour.class);
    }
    @Test
    void addStudentsToHall() {

        this.studentMap.put(PawnColour.GREEN, 3);
        this.playerBoard.addStudentsToHall(studentMap);
        assertEquals(3, this.playerBoard.getStudentsInTable(PawnColour.GREEN));
    }

    @Test
    void getStudentsInHall() {

        for(PawnColour colour : PawnColour.values()) {
            this.studentMap.put(colour, 3);
        }
        this.playerBoard.addStudentsToHall(studentMap);
        assertEquals(this.studentMap, this.playerBoard.getStudentsInHall());
    }

    @Test
    void getStudentsInTable() {

        this.studentMap.put(PawnColour.GREEN, 3);
        this.playerBoard.addStudentsToHall(this.studentMap);
        assertEquals(3, this.playerBoard.getStudentsInTable(PawnColour.GREEN));
    }
    @Test
    void getHall() {
        playerBoard.getHall().addStudent(PawnColour.RED);
        assertEquals(1, playerBoard.getHall().getStudentsByColour(PawnColour.RED));
    }
}