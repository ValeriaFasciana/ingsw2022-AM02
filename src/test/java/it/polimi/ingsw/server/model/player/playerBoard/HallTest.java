package it.polimi.ingsw.server.model.player.playerBoard;

import it.polimi.ingsw.shared.enums.PawnColour;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HallTest {
    Hall hall;

    /**
     * setup before every tests
     */

    @BeforeEach
    public void setUp()  {
        this.hall = new Hall();
    }

    /**
     *
     * It's true if line of given colour reaches 10
     */
    @Test
    void isLineFull() {
        this.hall.addStudentsForEachColour(10);

        for(PawnColour colour : PawnColour.values()){
            assertTrue(this.hall.isLineFull(colour));
        }
    }

    /**
     *
     * It's true if the actual count is equals to the previous one + 1
     */
    @Test
    void addStudent() {
        this.hall.addStudentsForEachColour(7);

        for(PawnColour colour : PawnColour.values()){
            this.hall.addStudent(colour);
            assertEquals(8, hall.getStudentsByColour(colour));
        }

    }
}