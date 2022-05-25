package it.polimi.ingsw.network.messages.clienttoserver.events;

import it.polimi.ingsw.server.ServerMessageVisitor;
import it.polimi.ingsw.network.messages.MessageFromClientToServer;
import it.polimi.ingsw.network.messages.Type;

public class ChooseAssistantResponse extends MessageFromClientToServer {

    private final int chosenAssistantIndex;

    public ChooseAssistantResponse(String username, Type type, int chosenAssistantIndex) {
        super(username, type);
        this.chosenAssistantIndex = chosenAssistantIndex;
    }

    @Override
    public void callVisitor(ServerMessageVisitor visitor) {

    }
}
