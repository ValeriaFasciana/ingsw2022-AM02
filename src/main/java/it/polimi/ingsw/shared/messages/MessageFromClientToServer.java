package it.polimi.ingsw.shared.messages;

import it.polimi.ingsw.shared.ServerMessageManager;

public abstract class MessageFromClientToServer extends Message {
    public MessageFromClientToServer(String username, Type type) {
        super(username, type);
    }

    public abstract void callVisitor(ServerMessageManager visitor);
}
