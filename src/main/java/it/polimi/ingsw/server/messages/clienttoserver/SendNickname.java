package it.polimi.ingsw.server.messages.clienttoserver;

import it.polimi.ingsw.server.ClientHandler;

public class SendNickname extends it.polimi.ingsw.network.messages.clienttoserver.SendNickname implements ServerMessage {

    public SendNickname(String nickname) {
        super(nickname);
    }

    @Override
    public void processMessage(ClientHandler clientHandler) {
        clientHandler.setNickname(nickname);
        System.out.println("nickname:"+nickname);
    }
}
