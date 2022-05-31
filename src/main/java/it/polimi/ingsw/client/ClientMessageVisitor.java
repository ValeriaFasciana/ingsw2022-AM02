package it.polimi.ingsw.client;

import it.polimi.ingsw.network.messages.servertoclient.events.GameCreatedEvent;
import it.polimi.ingsw.server.model.BoardData;

public interface ClientMessageVisitor {


    void GameCreated(GameCreatedEvent message);


    void boardUpdate(BoardData boardData);


    void notYourTurn();
}
