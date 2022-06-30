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
        view.initBoard(message.getBoardData(),message.isExpertMode());
    }


    @Override
    public void newLobbyCreated(LobbyCreatedResponse message){
        view.askLobbyInfo();
    }

    @Override
    public void joinedLobby(JoinedLobbyResponse message) {
        view.notifyPlayerHasJoined(message.getUsername());
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

    @Override
    public void askNickname(InvalidUsernameResponse message) {
        view.askUserInfo(message.isRejoin());
    }

    @Override
    public void endGame(EndGameEvent message) {
        view.endGame(message.getCausingUser(),message.getCause());
    }

    @Override
    public void chooseIsland(ChooseIslandRequest message) {
        view.askChooseIsland(message.isSetBan(),message.isCalculateInfluence());
    }

    @Override
    public void chooseColour(ChooseColourRequest message) {
        view.askChooseColour(message.getToDiscard(),message.isToExclude());
    }

    @Override
    public void moveStudentsFromCard(MoveStudentFromCardRequest message) {
        view.askMoveStudentsFromCard(message.getCharacterId(),message.getDestination(),message.getStudentsToMove(),message.isCanMoveLess());
    }

    @Override
    public void exchangeStudents(ExchangeStudentsRequest message) {
        view.askExchangeStudents(message.getCharacterId(),message.getNumberOfStudents(),message.getFrom(),message.getTo());
    }

    @Override
    public void askLoginInfo(AskLoginInfoRequest message) {
        view.askLoginInfo(message.getUsername(),message.canJoinLobby(),message.canRejoinLobby());
    }

    @Override
    public void notifyPlayerDisconnection(PlayerDisconnectedEvent playerDisconnectedEvent) {
        view.notifyDisconnection(playerDisconnectedEvent.getDisconnectedPlayerName());
    }

}
