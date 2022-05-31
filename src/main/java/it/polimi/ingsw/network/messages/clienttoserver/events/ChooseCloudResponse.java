package it.polimi.ingsw.network.messages.clienttoserver.events;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import it.polimi.ingsw.network.messages.MessageFromClientToServer;
import it.polimi.ingsw.network.messages.Type;
import it.polimi.ingsw.server.ServerMessageVisitor;

public class ChooseCloudResponse extends MessageFromClientToServer {
    int chosenCloudIndex;

    @JsonCreator
    public ChooseCloudResponse(String username, Type type, int chosenCloudIndex) {
        super(username, type);
        this.chosenCloudIndex = chosenCloudIndex;
    }

    @Override
    public void callVisitor(ServerMessageVisitor visitor) {
        visitor.setChosenCloud(this);
    }

    @JsonGetter
    public int getChosenCloudIndex() {
        return chosenCloudIndex;
    }
}
