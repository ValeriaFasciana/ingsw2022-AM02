package it.polimi.ingsw.client;



import it.polimi.ingsw.network.messages.servertoclient.events.*;



public interface ClientMessageVisitor {


    void gameCreated(GameCreatedEvent message);


    void newLobbyCreated(LobbyCreatedResponse message);


    void joinedLobby(JoinedLobbyResponse message);


    void boardUpdate(BoardUpdateResponse message);




    void moveStudent(MoveStudentFromEntranceRequest message);


    void notYourTurn();

    void askAssistant(ChooseAssistantRequest chooseAssistantRequest);

    void askMoveStudentFromEntrance(MoveStudentFromEntranceRequest moveStudentFromEntranceRequest);
}
