package it.polimi.ingsw.network.messages.servertoclient.events;

import it.polimi.ingsw.client.ClientMessageVisitor;
import it.polimi.ingsw.network.messages.MessageFromServerToClient;
import it.polimi.ingsw.network.messages.Type;

import java.util.ArrayList;

public class ChooseCloudRequest extends MessageFromServerToClient {

    private final ArrayList<Integer> availableCloudIndexes;

    public ChooseCloudRequest(String username, Type type, ArrayList<Integer> availableCloudIndexes) {
        super(username, type);
        this.availableCloudIndexes = availableCloudIndexes;
    }

    @Override
    public void callVisitor(ClientMessageVisitor visitor) {

    }
}
