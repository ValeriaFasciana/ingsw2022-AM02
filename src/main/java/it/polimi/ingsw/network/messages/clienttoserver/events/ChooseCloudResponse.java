package it.polimi.ingsw.network.messages.clienttoserver.events;

import it.polimi.ingsw.network.messages.MessageFromClientToServer;
import it.polimi.ingsw.network.messages.Type;
import it.polimi.ingsw.server.ServerMessageVisitor;

public class ChooseCloudResponse extends MessageFromClientToServer {
    int chosenCloudIndex;

    public ChooseCloudResponse(String username, Type type, int chosenCloudIndex) {
        super(username, type);
        this.chosenCloudIndex = chosenCloudIndex;
    }

    @Override
    public void callVisitor(ServerMessageVisitor visitor) {
        visitor.setChosenCloud(this);
    }

    public int getChosenCloudIndex() {
        return chosenCloudIndex;
    }
}
