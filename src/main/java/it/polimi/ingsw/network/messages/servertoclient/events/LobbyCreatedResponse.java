package it.polimi.ingsw.network.messages.servertoclient.events;

import it.polimi.ingsw.client.ClientMessageVisitor;
import it.polimi.ingsw.network.messages.MessageFromServerToClient;
import it.polimi.ingsw.network.messages.Type;

public class LobbyCreatedResponse extends MessageFromServerToClient {
    /**
     * Message constructor
     *
     * @param username the sender's username
     * @param type     the message type
     */

    public LobbyCreatedResponse(String username, Type type) {
        super(username, type);
    }

    @Override
    public void callVisitor(ClientMessageVisitor visitor) {

    }
}
