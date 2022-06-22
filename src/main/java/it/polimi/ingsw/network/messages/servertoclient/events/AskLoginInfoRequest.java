package it.polimi.ingsw.network.messages.servertoclient.events;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.client.ClientMessageVisitor;
import it.polimi.ingsw.network.messages.MessageFromServerToClient;
import it.polimi.ingsw.network.messages.Type;


public class AskLoginInfoRequest extends MessageFromServerToClient {

    private final boolean canJoinLobby;
    private final boolean canRejoinLobby;

    @JsonCreator
    public AskLoginInfoRequest(@JsonProperty("username") String username,
                               @JsonProperty("canJoinLobby") boolean canJoinLobby,
                               @JsonProperty("canRejoinLobby") boolean canRejoinLobby) {
        super(username, Type.SERVER_REQUEST);
        this.canJoinLobby = canJoinLobby;
        this.canRejoinLobby = canRejoinLobby;
    }

    @Override
    public void callVisitor(ClientMessageVisitor visitor) {
        visitor.askLoginInfo(this);
    }

    @JsonGetter
    public boolean canJoinLobby() {
        return canJoinLobby;
    }

    @JsonGetter
    public boolean canRejoinLobby() {
        return canRejoinLobby;
    }
}
