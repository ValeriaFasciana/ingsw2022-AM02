package it.polimi.ingsw.network.messages.servertoclient.events;

import it.polimi.ingsw.client.ClientMessageVisitor;
import it.polimi.ingsw.network.messages.MessageFromServerToClient;
import it.polimi.ingsw.network.messages.Type;

import java.util.ArrayList;

public class MoveMotherNatureRequest extends MessageFromServerToClient {

    private final ArrayList<Integer> availableIsleIndexes;

    public MoveMotherNatureRequest(String username, Type type,ArrayList<Integer> availableIsleIndexes) {
        super(username, type);
        this.availableIsleIndexes = availableIsleIndexes;
    }

    @Override
    public void callVisitor(ClientMessageVisitor visitor) {

    }
}
