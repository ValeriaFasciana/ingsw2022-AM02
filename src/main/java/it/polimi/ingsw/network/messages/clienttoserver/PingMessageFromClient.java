package it.polimi.ingsw.network.messages.clienttoserver;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.server.ClientHandler;
import it.polimi.ingsw.server.ServerMessageVisitor;
import it.polimi.ingsw.network.messages.MessageFromClientToServer;
import it.polimi.ingsw.network.messages.Type;

public class PingMessageFromClient extends MessageFromClientToServer {



    @JsonCreator
    public PingMessageFromClient(@JsonProperty("username") String username,
                            @JsonProperty("type") Type type){
        super(username,type);
    }

    public String getPingMessage(){
        return "PING";
    }

    public void processMessage(ClientHandler clientHandler) {
        //empty method because there's no need to process ping message
    }

    @Override
    public void callVisitor(ServerMessageVisitor visitor) {

    }
}
