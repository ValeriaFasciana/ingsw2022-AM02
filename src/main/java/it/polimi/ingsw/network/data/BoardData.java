package it.polimi.ingsw.network.data;

import com.fasterxml.jackson.annotation.*;

import java.util.Map;
import java.util.Optional;

@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id", scope = BoardData.class)
@JsonPropertyOrder({"playerBoards", "gameBoard"})
public class BoardData {
    private Map<String,PlayerBoardData> playerBoards;
    private GameBoardData gameBoard;
    private Optional<Map<Integer,CharacterCardData>> characters;

    @JsonCreator
    public BoardData(@JsonProperty("playerBoards") Map<String,PlayerBoardData> playerBoards,
                     @JsonProperty("gameBoard") GameBoardData gameBoard) {
        this(playerBoards,gameBoard,null);
    }

    @JsonCreator
    public BoardData(@JsonProperty("playerBoards") Map<String,PlayerBoardData> playerBoards,
                     @JsonProperty("gameBoard") GameBoardData gameBoard,
                     @JsonProperty("characters")Map<Integer,CharacterCardData> characters) {
        this.playerBoards = playerBoards;
        this.gameBoard = gameBoard;
        this.characters = Optional.ofNullable(characters);
    }

    @JsonGetter
    public Map<String, PlayerBoardData> getPlayerBoards() {
        return playerBoards;
    }

    @JsonGetter
    public GameBoardData getGameBoard() {
        return gameBoard;
    }

    @JsonGetter
    public Optional<Map<Integer, CharacterCardData>> getCharacters() {
        return characters;
    }
}
