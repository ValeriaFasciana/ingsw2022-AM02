package it.polimi.ingsw.network.messages.clienttoserver.events;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.network.messages.MessageFromClientToServer;
import it.polimi.ingsw.network.messages.Type;
import it.polimi.ingsw.server.ServerMessageVisitor;

public class NicknameResponse extends MessageFromClientToServer {
    private String playerNickname;

    @JsonCreator
    public NicknameResponse(@JsonProperty("playerNickName") String playerNickname) {
        super(playerNickname, Type.CLIENT_RESPONSE);
        this.playerNickname = playerNickname;
    }

    @JsonGetter
    public String getPlayerNickName() {
        return playerNickname;
    }

    @Override
    public void callVisitor(ServerMessageVisitor visitor) {
        visitor.setNickname(this);
    }
}
