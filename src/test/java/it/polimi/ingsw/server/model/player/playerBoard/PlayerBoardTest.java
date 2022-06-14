package it.polimi.ingsw.server.model.player.playerBoard;

import it.polimi.ingsw.shared.enums.PawnColour;
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
}