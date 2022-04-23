package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.model.board.GameBoard;
import it.polimi.ingsw.server.model.board.IsleCircle;
import it.polimi.ingsw.server.model.board.IsleGroup;
import it.polimi.ingsw.server.model.characters.CharacterCard;
import it.polimi.ingsw.server.model.player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {
    Game game;
    HashMap<String,Player> playerMap = new HashMap<>();
    GameBoard gameBoard =new GameBoard(3,12,3);

     @BeforeEach
    private void setUp() throws IOException {
        Player player1 = new Player("testPlayer",3,8, TowerColour.BLACK);
        this.playerMap.put("testPlayer",player1);
        this.game = new Game(this.playerMap, 2, false);
    }

    @Test
    void addPlayer() {
        this.game.addPlayer("testPlayer2", TowerColour.WHITE);
        assertEquals(TowerColour.WHITE, this.game.getPlayers().get("testPlayer2").getTowerColour());
    }

    @Test
    void moveMotherNature() {
         this.game.moveMotherNature(4);
         assertEquals(this.game.getGameBoard().getIsleCircle().get(4), this.game.getMotherNaturePosition());
    }

    @Test
    void getMotherNaturePosition() {
        this.game.moveMotherNature(4);
        assertEquals(this.game.getGameBoard().getIsleCircle().get(4), this.game.getMotherNaturePosition());
    }

    @Test
    void winningConditions() {
    }

    @Test
    void actionPhase() {
    }
}