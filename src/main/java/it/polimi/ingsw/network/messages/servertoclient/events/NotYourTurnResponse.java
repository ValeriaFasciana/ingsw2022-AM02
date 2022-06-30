package it.polimi.ingsw.network.messages.servertoclient.events;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.client.ClientMessageVisitor;
import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.messages.MessageFromServerToClient;
import it.polimi.ingsw.network.messages.Type;

public class NotYourTurnResponse extends MessageFromServerToClient {

    @JsonCreator

    public NotYourTurnResponse(@JsonProperty("username") String username,
                               @JsonProperty("type") Type type) {

        super(username, type);
    }

    /**
     * Method to handle the not your turn event
     * @param visitor not your turn message
     */
    @Override
    public void callVisitor(ClientMessageVisitor visitor) {
        visitor.notYourTurn();
    }
}
