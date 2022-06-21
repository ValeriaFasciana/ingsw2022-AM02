package it.polimi.ingsw.network.messages;

import it.polimi.ingsw.client.ClientMessageVisitor;


public abstract class MessageFromServerToClient extends Message {
    public MessageFromServerToClient(String username, Type type) {
        super(username, type);
    }


    public abstract void callVisitor(ClientMessageVisitor visitor);

}
