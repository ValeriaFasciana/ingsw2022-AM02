package it.polimi.ingsw.shared.messages.fromClientToServer;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.shared.ServerMessageManager;
import it.polimi.ingsw.shared.messages.MessageFromClientToServer;
import it.polimi.ingsw.shared.messages.Type;

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
    public void callVisitor(ServerMessageManager visitor) {
        visitor.login(this);
    }
}
