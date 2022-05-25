package it.polimi.ingsw.client;

import it.polimi.ingsw.network.messages.servertoclient.events.GameCreatedEvent;
import it.polimi.ingsw.network.messages.servertoclient.events.JoinLobbyResponse;
import it.polimi.ingsw.network.messages.servertoclient.events.LoginResponse;

public interface ClientMessageVisitor {

    void Logged(LoginResponse message);

    void GameCreated(GameCreatedEvent message);


    void JoinedLobby(JoinLobbyResponse message);
}
