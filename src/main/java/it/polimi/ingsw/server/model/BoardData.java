package it.polimi.ingsw.server.model;

import com.fasterxml.jackson.annotation.*;
import it.polimi.ingsw.server.model.board.GameBoardData;

import java.util.ArrayList;
import java.util.Map;
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id", scope = BoardData.class)
@JsonPropertyOrder({"playerBoards", "gameBoard"})
public class BoardData {
    private Map<String,PlayerBoardData> playerBoards;
    private GameBoardData gameBoard;

    @JsonCreator
    public BoardData(@JsonProperty("playerBoards") Map<String,PlayerBoardData> playerBoards,@JsonProperty("gameBoard") GameBoardData gameBoard) {
        this.playerBoards = playerBoards;
        this.gameBoard = gameBoard;
    }

    @JsonGetter
    public Map<String, PlayerBoardData> getPlayerBoards() {
        return playerBoards;
    }

    @JsonGetter
    public GameBoardData getGameBoard() {
        return gameBoard;
    }
}
