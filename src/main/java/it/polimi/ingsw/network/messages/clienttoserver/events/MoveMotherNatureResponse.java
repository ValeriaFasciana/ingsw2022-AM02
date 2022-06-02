package it.polimi.ingsw.network.messages.clienttoserver.events;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import it.polimi.ingsw.server.ServerMessageVisitor;
import it.polimi.ingsw.network.messages.MessageFromClientToServer;
import it.polimi.ingsw.network.messages.Type;

public class MoveMotherNatureResponse extends MessageFromClientToServer {

    private final int isleIndex;

    @JsonCreator
    public MoveMotherNatureResponse(String username, Type type, int isleIndex) {
        super(username, type);
        this.isleIndex = isleIndex;
    }

    @Override
    public void callVisitor(ServerMessageVisitor visitor) {
        visitor.moveMotherNature(this);
    }

    @JsonGetter
    public int getIsleIndex() {
        return isleIndex;
    }
}
