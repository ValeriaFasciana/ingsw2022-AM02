package it.polimi.ingsw.network.messages.clienttoserver.events;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.network.messages.MessageFromClientToServer;
import it.polimi.ingsw.network.messages.Type;
import it.polimi.ingsw.server.ServerMessageVisitor;

public class ChooseCloudResponse extends MessageFromClientToServer {
    private int chosenCloudIndex;

    @JsonCreator
    public ChooseCloudResponse(@JsonProperty("username")String username, @JsonProperty("chosenCloudIndex")int chosenCloudIndex) {
        super(username,Type.CLIENT_RESPONSE);
        this.chosenCloudIndex = chosenCloudIndex;
    }

    /**
     * Method to handle choose cloud response
     * @param visitor server message visitor
     */
    @Override
    public void callVisitor(ServerMessageVisitor visitor) {
        visitor.setChosenCloud(this);
    }

    @JsonGetter
    public int getChosenCloudIndex() {
        return chosenCloudIndex;
    }
}
