package it.polimi.ingsw.network.data;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;

public class RoundData {

    private final String currentPlayerName;
    private final boolean isLastRound;
    private final int roundNumber;

    @JsonCreator
    public RoundData(@JsonProperty("currentPlayerName") String currentPlayerName,
                     @JsonProperty("roundNumber") int roundNumber,
                     @JsonProperty("isLastRound") boolean isLastRound) {
        this.currentPlayerName = currentPlayerName;
        this.roundNumber = roundNumber;
        this.isLastRound = isLastRound;
    }

    @JsonGetter
    public String getCurrentPlayerName() {
        return currentPlayerName;
    }

    @JsonGetter
    public boolean isLastRound() {
        return isLastRound;
    }

    @JsonGetter
    public int getRoundNumber() {
        return roundNumber;
    }
}
