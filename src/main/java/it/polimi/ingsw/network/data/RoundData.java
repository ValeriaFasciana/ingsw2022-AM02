package it.polimi.ingsw.network.data;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.shared.enums.Phase;

public class RoundData {

    private final String currentPlayerName;
    private final boolean isLastRound;
    private final int roundNumber;
    private Phase roundPhase;

    @JsonCreator
    public RoundData(@JsonProperty("currentPlayerName") String currentPlayerName,
                     @JsonProperty("roundNumber") int roundNumber,
                     @JsonProperty("lastRound") boolean isLastRound,
                     @JsonProperty("roundPhase") Phase roundPhase) {
        this.currentPlayerName = currentPlayerName;
        this.roundNumber = roundNumber;
        this.isLastRound = isLastRound;
        this.roundPhase =roundPhase;

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

    public Phase getRoundPhase() {
        return roundPhase;
    }
}
