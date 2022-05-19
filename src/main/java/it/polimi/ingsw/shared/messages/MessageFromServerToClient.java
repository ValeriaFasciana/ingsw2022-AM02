package it.polimi.ingsw.shared.messages;

import it.polimi.ingsw.shared.ClientMessageManager;
import it.polimi.ingsw.shared.ServerMessageManager;

public abstract class MessageFromServerToClient extends Message {
    public MessageFromServerToClient(String username, Type type) {
        super(username, type);
    }


    public abstract void callVisitor(ClientMessageManager visitor);
}
