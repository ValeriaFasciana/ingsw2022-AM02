package it.polimi.ingsw.network.messages.servertoclient.events;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.client.ClientMessageVisitor;
import it.polimi.ingsw.network.messages.MessageFromServerToClient;

import static it.polimi.ingsw.network.messages.Type.INVALID_NAME;

public class InvalidUsernameResponse extends MessageFromServerToClient {
    /**
     * Message constructor
     *
     * @param username the sender's username
     */
    @JsonCreator
    public InvalidUsernameResponse(@JsonProperty("username") String username) {
        super(username, INVALID_NAME);
    }

    @Override
    public void callVisitor(ClientMessageVisitor visitor) {
        visitor.askNickname();
    }

}
