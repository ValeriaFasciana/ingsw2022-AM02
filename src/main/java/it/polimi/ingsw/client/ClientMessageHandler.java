package it.polimi.ingsw.client;

import it.polimi.ingsw.client.view.ViewInterface;


import it.polimi.ingsw.network.messages.servertoclient.events.*;

public class ClientMessageHandler implements ClientMessageVisitor {

    ViewInterface view;
    Client client;

    public ClientMessageHandler(ViewInterface view) {
        this.view = view;
    }



    @Override
    public void gameCreated(GameCreatedEvent message) {
        view.printBoard(message.getBoardData());
    }

    @Override
    public void newLobbyCreated(LobbyCreatedResponse message) {
        view.askLobbyInfo();
    }

    @Override
    public void joinedLobby(JoinedLobbyResponse message) {
        view.askUserInfo();
    }


    @Override
    public void boardUpdate(BoardUpdateResponse message) {
        view.setBoard(message.getBoardData());
    }


    @Override
    public void notYourTurn() {

    }

    @Override
    public void askAssistant(ChooseAssistantRequest chooseAssistantRequest) {
        view.askAssistant(chooseAssistantRequest.getAvailableAssistantIds());
    }

    @Override
    public void askMoveStudentFromEntrance(MoveStudentFromEntranceRequest moveStudentFromEntranceRequest) {
        view.askMoveStudentFromEntrance(moveStudentFromEntranceRequest.getHallColourAvailability());

    }

    @Override
    public void moveMotherNature (MoveMotherNatureRequest message){
        view.moveMotherNature(message.getAvailableIsleIndexes());
    }

    @Override
    public void askCloud(ChooseCloudRequest message) {
        view.askCloud(message.getAvailableCloudIndexes());
    }
}
