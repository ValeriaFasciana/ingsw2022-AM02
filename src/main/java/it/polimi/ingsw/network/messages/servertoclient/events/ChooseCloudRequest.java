package it.polimi.ingsw.network.messages.servertoclient.events;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.client.ClientMessageVisitor;
import it.polimi.ingsw.network.messages.MessageFromServerToClient;
import it.polimi.ingsw.network.messages.Type;

import java.util.ArrayList;

public class ChooseCloudRequest extends MessageFromServerToClient {

    private final ArrayList<Integer> availableCloudIndexes;
    @JsonCreator
    public ChooseCloudRequest(@JsonProperty("username") String username,@JsonProperty("type") Type type,@JsonProperty("availableCloudIndexes") ArrayList<Integer> availableCloudIndexes) {
        super(username, type);
        this.availableCloudIndexes = availableCloudIndexes;
    }

    @JsonGetter
    public ArrayList<Integer> getAvailableCloudIndexes() {
        return availableCloudIndexes;
    }

    @Override
    public void callVisitor(ClientMessageVisitor visitor) {
        visitor.askCloud(this);

    }
}
