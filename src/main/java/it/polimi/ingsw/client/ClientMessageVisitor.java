package it.polimi.ingsw.client;

import it.polimi.ingsw.network.messages.servertoclient.events.GameCreatedEvent;
import it.polimi.ingsw.network.messages.servertoclient.events.JoinLobbyResponse;
import it.polimi.ingsw.network.messages.servertoclient.events.LobbyCreatedResponse;
import it.polimi.ingsw.network.messages.servertoclient.events.LoginResponse;
import it.polimi.ingsw.server.model.BoardData;

public interface ClientMessageVisitor {

    void Logged(LoginResponse message);

    void GameCreated(GameCreatedEvent message);


    void newLobbyCreated(LobbyCreatedResponse message);

    void JoinedLobby(JoinLobbyResponse message);

    void boardUpdate(BoardData boardData);


    void notYourTurn();
}
