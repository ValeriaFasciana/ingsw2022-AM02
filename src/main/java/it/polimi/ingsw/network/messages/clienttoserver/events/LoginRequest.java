package it.polimi.ingsw.network.messages.clienttoserver.events;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.server.ServerMessageVisitor;
import it.polimi.ingsw.network.messages.MessageFromClientToServer;
import it.polimi.ingsw.network.messages.Type;

/**
 * Login Request, from client to server
 * <p>
 * Sends the username chosen by the user to be validated from the server.
 */
public class LoginRequest extends MessageFromClientToServer {

    public LoginRequest(@JsonProperty("username") String username) {
        super(username, Type.CLIENT_REQUEST);
    }

    @Override
    public void callVisitor(ServerMessageVisitor visitor) {
        visitor.login(this);
    }
}
