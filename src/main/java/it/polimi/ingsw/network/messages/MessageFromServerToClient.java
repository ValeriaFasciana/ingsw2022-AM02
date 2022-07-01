package it.polimi.ingsw.network.messages;

import it.polimi.ingsw.client.ClientMessageVisitor;


public abstract class MessageFromServerToClient extends Message {

    /**
     * Default constructor
     * @param username client username
     * @param type message type
     */
    public MessageFromServerToClient(String username, Type type) {
        super(username, type);
    }


    public abstract void callVisitor(ClientMessageVisitor visitor);

}
