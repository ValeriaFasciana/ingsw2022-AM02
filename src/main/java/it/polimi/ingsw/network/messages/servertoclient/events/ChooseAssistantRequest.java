package it.polimi.ingsw.network.messages.servertoclient.events;

import it.polimi.ingsw.client.ClientMessageVisitor;
import it.polimi.ingsw.network.messages.MessageFromServerToClient;
import it.polimi.ingsw.network.messages.Type;

import java.util.ArrayList;
import java.util.Set;

public class ChooseAssistantRequest extends MessageFromServerToClient {

    private final Set<Integer> availableAssistantIds;
    /**
     * Message constructor
     *
     * @param username the sender's username
     * @param type     the message type
     */
    public ChooseAssistantRequest(String username, Type type, Set<Integer> availableAssistantIds) {
        super(username, type);
        this.availableAssistantIds = availableAssistantIds;
    }

    @Override
    public void callVisitor(ClientMessageVisitor visitor) {

    }
}
