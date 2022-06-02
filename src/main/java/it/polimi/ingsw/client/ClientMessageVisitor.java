package it.polimi.ingsw.client;

import it.polimi.ingsw.network.messages.servertoclient.events.BoardUpdateResponse;
import it.polimi.ingsw.network.messages.servertoclient.events.GameCreatedEvent;
import it.polimi.ingsw.network.messages.servertoclient.events.JoinedLobbyResponse;
import it.polimi.ingsw.network.messages.servertoclient.events.LobbyCreatedResponse;
import it.polimi.ingsw.server.model.BoardData;

public interface ClientMessageVisitor {


    void gameCreated(GameCreatedEvent message);


    void newLobbyCreated(LobbyCreatedResponse message);


    void joinedLobby(JoinedLobbyResponse message);

    void boardUpdate(BoardUpdateResponse mesasge);


    void notYourTurn();
}
