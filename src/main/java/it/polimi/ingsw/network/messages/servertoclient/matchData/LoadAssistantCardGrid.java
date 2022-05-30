package it.polimi.ingsw.network.messages.servertoclient.matchData;

import it.polimi.ingsw.network.messages.Type;

import java.util.List;

/**
 * Message to load assistant card grid informations client-side
 */

public class LoadAssistantCardGrid extends MatchDataMessage {
    private List<Integer> availableCardsIds;

    public LoadAssistantCardGrid(String nickname, Type type, List<Integer> availableCardsIds) {
        super(nickname, type);
        this.availableCardsIds = availableCardsIds;
    }

    public List<Integer> getAvailableCardsIds() {
        return availableCardsIds;
    }
}
