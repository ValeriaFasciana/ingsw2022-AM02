package it.polimi.ingsw.client.messages.servertoclient;

import it.polimi.ingsw.client.ServerHandler;

public class PingMessageFromServer extends it.polimi.ingsw.network.messages.servertoclient.PingMessageFromServer implements ClientMessage{

    @Override
    public void processMessage(ServerHandler serverHandler) {
        //empty method because PingMessage doesn't need to be processed
    }
}
