package it.polimi.ingsw.network.messages.clienttoserver.events;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import it.polimi.ingsw.network.messages.MessageFromClientToServer;
import it.polimi.ingsw.network.messages.Type;
import it.polimi.ingsw.server.ServerMessageVisitor;
@JsonTypeName("JoinLobbyResponse")
public class JoinLobbyResponse extends MessageFromClientToServer {
    private final String playerNickname;
    private final boolean isRejoin;

    @JsonCreator
    public JoinLobbyResponse(@JsonProperty("username")String username,
                             @JsonProperty("playerNickName")String playerNickname,
                             @JsonProperty("rejoin")boolean isRejoin) {
        super(username, Type.CLIENT_RESPONSE);
        this.playerNickname = playerNickname;
        this.isRejoin = isRejoin;

    }

    @JsonGetter
    public String getPlayerNickName() {
        return playerNickname;
    }

    @Override
    public void callVisitor(ServerMessageVisitor visitor) {
        visitor.joinLobby(this);
    }

    @JsonGetter
    public boolean isRejoin() {
        return isRejoin;
    }
}
