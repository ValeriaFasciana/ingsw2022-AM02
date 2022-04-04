package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.model.board.Cloud;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CloudTest {
    Cloud cloud;
    @BeforeEach
    public void setUp() {
        this.cloud = new Cloud(3,true,0);
    }
    @Test
    void getSide() {
        assertEquals(true,this.cloud.getSide());
    }
}