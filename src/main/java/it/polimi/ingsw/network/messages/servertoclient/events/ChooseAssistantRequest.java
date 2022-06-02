package it.polimi.ingsw.network.messages.servertoclient.events;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.client.ClientMessageVisitor;
import it.polimi.ingsw.network.messages.MessageFromServerToClient;
import it.polimi.ingsw.network.messages.Type;

import java.util.Set;

public class ChooseAssistantRequest extends MessageFromServerToClient {

    private final Set<Integer> availableAssistantIds;
    /**
     * Message constructor
     *
     * @param username the sender's username
     * @param availableAssistantIds
     */
    @JsonCreator
    public ChooseAssistantRequest(@JsonProperty("username") String username, @JsonProperty("availableAssistantIds") Set<Integer> availableAssistantIds) {
        super(username, Type.SERVER_REQUEST);
        this.availableAssistantIds = availableAssistantIds;
    }

    @Override
    public void callVisitor(ClientMessageVisitor visitor) {
        visitor.askAssistant(this);
    }

    @JsonGetter
    public Set<Integer> getAvailableAssistantIds() {
        return availableAssistantIds;
    }

    @JsonGetter
    public Set<Integer> getAvailableAssistantIds() {
        return availableAssistantIds;
    }
}
