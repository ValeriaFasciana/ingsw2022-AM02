package it.polimi.ingsw.network.messages.servertoclient.events;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonCreator
    public LobbyCreatedResponse(@JsonProperty("username") String username,@JsonProperty("type") Type type) {
        super(username, type);
    }

    @Override
    public void callVisitor(ClientMessageVisitor visitor) {
        System.out.print("lobbyCreated\n");
    }
}
