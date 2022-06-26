package it.polimi.ingsw.network.messages.servertoclient.events;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.client.ClientMessageVisitor;
import it.polimi.ingsw.network.messages.MessageFromServerToClient;
import it.polimi.ingsw.network.messages.Type;

import java.util.ArrayList;
import java.util.Set;

public class MoveMotherNatureRequest extends MessageFromServerToClient {

    private final Set<Integer> availableIsleIndexes;

    @JsonCreator
    public MoveMotherNatureRequest(@JsonProperty("username") String username, @JsonProperty("hallColourAvailability") Set<Integer> availableIsleIndexes) {
        super(username, Type.SERVER_REQUEST);
        this.availableIsleIndexes = availableIsleIndexes;
    }

    @Override
    public void callVisitor(ClientMessageVisitor visitor) {
        visitor.moveMotherNature(this);

    }

    @JsonGetter
    public Set<Integer> getAvailableIsleIndexes() {
        return availableIsleIndexes;
    }
}
