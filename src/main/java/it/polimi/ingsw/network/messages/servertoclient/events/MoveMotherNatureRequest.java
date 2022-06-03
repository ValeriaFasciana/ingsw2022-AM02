package it.polimi.ingsw.network.messages.servertoclient.events;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.client.ClientMessageVisitor;
import it.polimi.ingsw.network.messages.MessageFromServerToClient;
import it.polimi.ingsw.network.messages.Type;

import java.util.ArrayList;

public class MoveMotherNatureRequest extends MessageFromServerToClient {

    private final ArrayList<Integer> availableIsleIndexes;

    public MoveMotherNatureRequest(@JsonProperty("username") String username, @JsonProperty("hallColourAvailability")ArrayList<Integer> availableIsleIndexes) {
        super(username, Type.SERVER_REQUEST);
        this.availableIsleIndexes = availableIsleIndexes;
    }

    @Override
    public void callVisitor(ClientMessageVisitor visitor) {
        visitor.moveMotherNature(this);

    }

    public ArrayList<Integer> getAvailableIsleIndexes() {
        return availableIsleIndexes;
    }
}
