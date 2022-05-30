package it.polimi.ingsw.network.messages.servertoclient.matchData;

import it.polimi.ingsw.network.messages.Type;
import it.polimi.ingsw.shared.LightAssistantCard;

import java.util.List;

/**
 * Message to load all the assistant cards client-side
 */
public class LoadAssistantCardMessage extends MatchDataMessage{

    private List<LightAssistantCard> lightAssistantCards;
    public LoadAssistantCardMessage(String nickname, Type type) {
        super(nickname, type);
    }
}
