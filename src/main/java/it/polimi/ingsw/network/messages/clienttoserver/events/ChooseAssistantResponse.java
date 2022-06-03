package it.polimi.ingsw.network.messages.clienttoserver.events;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.server.ServerMessageVisitor;
import it.polimi.ingsw.network.messages.MessageFromClientToServer;
import it.polimi.ingsw.network.messages.Type;

public class ChooseAssistantResponse extends MessageFromClientToServer {

    private final int chosenAssistantIndex;

    @JsonCreator
    public ChooseAssistantResponse(@JsonProperty("playerNickName") String username, @JsonProperty("chosenAssistantIndex")int chosenAssistantIndex) {
        super(username,Type.CLIENT_RESPONSE);
        this.chosenAssistantIndex = chosenAssistantIndex;
    }

    @Override
    public void callVisitor(ServerMessageVisitor visitor) {
        visitor.setChosenAssistant(this);
    }

    @JsonGetter
    public int getChosenAssistantIndex() {
        return chosenAssistantIndex;
    }
}
