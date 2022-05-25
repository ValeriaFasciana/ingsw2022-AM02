package it.polimi.ingsw.network.messages.servertoclient;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.client.ClientMessageVisitor;
import it.polimi.ingsw.network.messages.MessageFromServerToClient;
import it.polimi.ingsw.network.messages.Type;

public class PingMessageFromServer extends MessageFromServerToClient {


    @JsonCreator
    public PingMessageFromServer(@JsonProperty("username") String username,
                                 @JsonProperty("type") Type type){
        super(username,type);
    }


    @Override
    public void callVisitor(ClientMessageVisitor visitor) {

    }
}
