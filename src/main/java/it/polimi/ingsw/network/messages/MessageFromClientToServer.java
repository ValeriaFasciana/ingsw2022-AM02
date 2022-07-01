package it.polimi.ingsw.network.messages;

import it.polimi.ingsw.server.ServerMessageVisitor;

public abstract class MessageFromClientToServer extends Message {
    /**
     * Default constructor
     * @param username client username
     * @param type     message type
     */
    public MessageFromClientToServer(String username, Type type) {
        super(username, type);
    }

    public abstract void callVisitor(ServerMessageVisitor visitor);
}
