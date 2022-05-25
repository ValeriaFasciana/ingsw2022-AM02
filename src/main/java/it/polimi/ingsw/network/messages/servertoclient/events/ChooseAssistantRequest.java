package it.polimi.ingsw.network.messages.servertoclient.events;

import it.polimi.ingsw.client.ClientMessageVisitor;
import it.polimi.ingsw.network.messages.MessageFromServerToClient;
import it.polimi.ingsw.network.messages.Type;

import java.util.ArrayList;

public class ChooseAssistantRequest extends MessageFromServerToClient {

    private final ArrayList<Integer> availableAssistantIds;
    /**
     * Message constructor
     *
     * @param username the sender's username
     * @param type     the message type
     */
    public ChooseAssistantRequest(String username, Type type, ArrayList<Integer> availableAssistantIds) {
        super(username, type);
        this.availableAssistantIds = availableAssistantIds;
    }

    @Override
    public void callVisitor(ClientMessageVisitor visitor) {

    }
}
