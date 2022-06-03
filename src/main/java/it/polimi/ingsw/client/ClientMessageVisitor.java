package it.polimi.ingsw.client;

import it.polimi.ingsw.network.messages.servertoclient.events.*;
import it.polimi.ingsw.network.messages.servertoclient.events.*;
import it.polimi.ingsw.server.model.BoardData;

public interface ClientMessageVisitor {


    void gameCreated(GameCreatedEvent message);


    void newLobbyCreated(LobbyCreatedResponse message);


    void joinedLobby(JoinedLobbyResponse message);

    void boardUpdate(BoardUpdateResponse mesasge);


    void notYourTurn();

    void askAssistant(ChooseAssistantRequest chooseAssistantRequest);

    void askMoveStudentFromEntrance(MoveStudentFromEntranceRequest moveStudentFromEntranceRequest);

    void moveMotherNature(MoveMotherNatureRequest message);

    void askCloud(ChooseCloudRequest message);
}
