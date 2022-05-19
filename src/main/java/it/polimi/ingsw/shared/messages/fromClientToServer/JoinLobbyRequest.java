package it.polimi.ingsw.shared.messages.fromClientToServer;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.shared.ServerMessageManager;
import it.polimi.ingsw.shared.messages.MessageFromClientToServer;
import it.polimi.ingsw.shared.messages.Type;

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
    public void callVisitor(ServerMessageManager visitor) {
        visitor.joinLobby(this);
    }
}
