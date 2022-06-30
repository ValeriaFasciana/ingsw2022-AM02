package it.polimi.ingsw.network.messages.clienttoserver.events;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;

import com.fasterxml.jackson.annotation.JsonProperty;

import it.polimi.ingsw.server.ServerMessageVisitor;
import it.polimi.ingsw.network.messages.MessageFromClientToServer;
import it.polimi.ingsw.network.messages.Type;

public class MoveMotherNatureResponse extends MessageFromClientToServer {

    int isleIndex;

    @JsonCreator
    public MoveMotherNatureResponse(@JsonProperty("playerName")String username,@JsonProperty("isleIndex") int isleIndex) {
        super(username, Type.CLIENT_RESPONSE);
        this.isleIndex = isleIndex;
    }

    /**
     * Method to handle move mother nature response
     * @param visitor server message visitor
     */
    @Override
    public void callVisitor(ServerMessageVisitor visitor) {
        visitor.moveMotherNature(this);
    }

    @JsonGetter
    public int getIsleIndex() {
        return isleIndex;
    }
}
