package it.polimi.ingsw.network.messages.clienttoserver.events;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.server.ServerMessageVisitor;
import it.polimi.ingsw.network.messages.MessageFromClientToServer;
import it.polimi.ingsw.network.messages.Type;

public class JoinLobbyRequest extends MessageFromClientToServer {
    private final String lobbyName;

    @JsonCreator
    public JoinLobbyRequest(@JsonProperty("username") String username,
                            @JsonProperty("lobby name") String lobbyName)
    {
        super(username, Type.CLIENT_REQUEST);
        this.lobbyName = lobbyName;
    }

    public String getLobbyName() {
        return lobbyName;
    }

    @Override
    public void callVisitor(ServerMessageVisitor visitor) {
        visitor.joinLobby(this);
    }
}
