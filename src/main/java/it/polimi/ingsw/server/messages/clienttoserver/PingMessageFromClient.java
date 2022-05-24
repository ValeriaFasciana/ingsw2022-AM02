package it.polimi.ingsw.server.messages.clienttoserver;

import it.polimi.ingsw.server.ClientHandler;

public class PingMessageFromClient extends it.polimi.ingsw.network.messages.clienttoserver.PingMessageFromClient implements ServerMessage {


    public PingMessageFromClient(String message) {
        super(message);
    }

    @Override
    public void processMessage(ClientHandler clientHandler) {
        //empty method because there's no need to process ping message
    }
}
