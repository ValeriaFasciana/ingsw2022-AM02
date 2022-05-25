package it.polimi.ingsw.client;

import it.polimi.ingsw.network.messages.servertoclient.events.GameCreatedEvent;
import it.polimi.ingsw.network.messages.servertoclient.events.JoinLobbyResponse;
import it.polimi.ingsw.network.messages.servertoclient.events.LoginResponse;

public class ClientMessageHandler implements ClientMessageVisitor {
    @Override
    public void Logged(LoginResponse message) {

    }

    @Override
    public void GameCreated(GameCreatedEvent message) {

    }

    @Override
    public void JoinedLobby(JoinLobbyResponse message) {

    }
}
