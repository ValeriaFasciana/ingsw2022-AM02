package it.polimi.ingsw.network.messages.servertoclient.events;

import it.polimi.ingsw.client.ClientMessageVisitor;
import it.polimi.ingsw.network.messages.MessageFromServerToClient;
import it.polimi.ingsw.network.messages.Type;

public class JoinedLobbyResponse extends MessageFromServerToClient {
    public JoinedLobbyResponse(String username, Type type) {
        super(username,type);
    }

    @Override
    public void callVisitor(ClientMessageVisitor visitor) {

    }
}
