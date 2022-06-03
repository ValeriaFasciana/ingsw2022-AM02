package it.polimi.ingsw.client;

import it.polimi.ingsw.network.messages.servertoclient.events.*;
import it.polimi.ingsw.server.model.BoardData;

public interface ClientMessageVisitor {


    void gameCreated(GameCreatedEvent message);


    void newLobbyCreated(LobbyCreatedResponse message);


    void joinedLobby(JoinedLobbyResponse message);

    void boardUpdate(BoardUpdateResponse message);

    void ChooseAssistant(ChooseAssistantRequest message);

    void moveStudent(MoveStudentFromEntranceRequest message);


    void notYourTurn();

    void moveMotherNature(MoveMotherNatureRequest message);
}
