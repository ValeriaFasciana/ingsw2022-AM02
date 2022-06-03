package it.polimi.ingsw.client;

import it.polimi.ingsw.client.view.ViewInterface;
import it.polimi.ingsw.network.messages.servertoclient.events.*;
import it.polimi.ingsw.server.model.BoardData;

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
        view.printBoard(message.getBoardData());
    }

    @Override
    public void ChooseAssistant(ChooseAssistantRequest message) {
        view.askAssistantCard(message.getAvailableAssistantIds());
    }

    @Override
    public void moveStudent(MoveStudentFromEntranceRequest message) {
        view.moveStudent(message.getHallColourAvailability());

    }

    @Override
    public void notYourTurn() {

    }
}
