package it.polimi.ingsw.network.messages.servertoclient.events;

import com.fasterxml.jackson.annotation.JsonCreator;
import it.polimi.ingsw.client.ClientMessageVisitor;
import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.messages.MessageFromServerToClient;
import it.polimi.ingsw.network.messages.Type;

public class NotYourTurnResponse extends MessageFromServerToClient {

    @JsonCreator
    public NotYourTurnResponse(String username, Type type) {
        super(username, type);
    }

    @Override
    public void callVisitor(ClientMessageVisitor visitor) {
        visitor.notYourTurn();
    }
}
