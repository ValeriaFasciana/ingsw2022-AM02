package it.polimi.ingsw.network.data;

import com.fasterxml.jackson.annotation.*;

import java.util.Map;

@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id", scope = BoardData.class)
@JsonPropertyOrder({"playerBoards", "gameBoard", "characters"})
public class BoardData {
    private final boolean expertMode;
    private final int roundNumber;
    private final String currentPlayerName;
    private Map<String,PlayerBoardData> playerBoards;
    private GameBoardData gameBoard;
    private Map<Integer,CharacterCardData> characters;

    @JsonCreator
    public BoardData(@JsonProperty("expertMode") boolean expertMode,
                     @JsonProperty("roundNumber") int roundNumber,
                     @JsonProperty("currentPlayerName") String currentPlayerName,
                     @JsonProperty("playerBoards") Map<String, PlayerBoardData> playerBoards,
                     @JsonProperty("gameBoard") GameBoardData gameBoard,
                     @JsonProperty("characters") Map<Integer, CharacterCardData> characters) {
        this.expertMode = expertMode;
        this.roundNumber = roundNumber;
        this.currentPlayerName = currentPlayerName;
        this.playerBoards = playerBoards;
        this.gameBoard = gameBoard;
        this.characters = characters;
    }

    @JsonGetter
    public boolean isExpertMode() {
        return expertMode;
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
    public Map<Integer, CharacterCardData> getCharacters() {
        return characters;
    }

    @JsonGetter
    public int getRoundNumber() {
        return roundNumber;
    }

    @JsonGetter
    public String getCurrentPlayerName() {
        return currentPlayerName;
    }
}
