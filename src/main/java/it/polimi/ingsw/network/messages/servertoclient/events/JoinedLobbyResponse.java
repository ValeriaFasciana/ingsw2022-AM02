package it.polimi.ingsw.network.messages.servertoclient.events;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.client.ClientMessageVisitor;
import it.polimi.ingsw.network.ReservedRecipients;
import it.polimi.ingsw.network.messages.MessageFromServerToClient;
import it.polimi.ingsw.network.messages.Type;

public class JoinedLobbyResponse extends MessageFromServerToClient {
    @JsonCreator
    public JoinedLobbyResponse(@JsonProperty("username")String username, @JsonProperty("type") Type type) {
        super(username,type);
    }

    @Override
    public void callVisitor(ClientMessageVisitor visitor) {
        visitor.joinedLobby(this);
    }
}
