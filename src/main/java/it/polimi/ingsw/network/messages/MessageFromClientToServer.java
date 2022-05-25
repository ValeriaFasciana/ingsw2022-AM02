package it.polimi.ingsw.network.messages;

import it.polimi.ingsw.server.ServerMessageVisitor;

public abstract class MessageFromClientToServer extends Message {
    public MessageFromClientToServer(String username, Type type) {
        super(username, type);
    }

    public abstract void callVisitor(ServerMessageVisitor visitor);
}
