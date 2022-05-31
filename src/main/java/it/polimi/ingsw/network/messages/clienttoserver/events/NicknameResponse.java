package it.polimi.ingsw.network.messages.clienttoserver.events;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.network.messages.MessageFromClientToServer;
import it.polimi.ingsw.network.messages.Type;
import it.polimi.ingsw.server.ServerMessageVisitor;

public class NicknameResponse extends MessageFromClientToServer {
    String playerNickName;

    public NicknameResponse(@JsonProperty("username")String username,@JsonProperty("playerNickName") String playerNickName) {
        super(username, Type.CLIENT_REQUEST);
        this.playerNickName = playerNickName;
    }

    @Override
    public void callVisitor(ServerMessageVisitor visitor) {
        visitor.setNickname(this);
    }
}
