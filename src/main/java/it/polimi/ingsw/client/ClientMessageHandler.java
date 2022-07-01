package it.polimi.ingsw.client;

import it.polimi.ingsw.client.view.ViewInterface;


import it.polimi.ingsw.network.messages.servertoclient.events.*;

public class ClientMessageHandler implements ClientMessageVisitor {

    ViewInterface view;
    Client client;

    public ClientMessageHandler(ViewInterface view) {
        this.view = view;
    }


    /**
     * Method to handle the game created event
     * @param message game created message
     */
    @Override
    public void gameCreated(GameCreatedEvent message) {
        view.initBoard(message.getBoardData(),message.isExpertMode());
    }

    /**
     * Method to handle the joined lobby event
     * @param message joined lobby message
     */
    @Override
    public void joinedLobby(JoinedLobbyResponse message) {
        view.notifyPlayerHasJoined(message.getUsername());
    }


    @Override
    public void newLobbyCreated(LobbyCreatedResponse message){
        view.askLobbyInfo();
    }

    /**
     * Method to handle the board update event
     * @param message board update message
     */
    @Override
    public void boardUpdate(BoardUpdateResponse message) {
        view.setBoard(message.getBoardData());
    }

    /**
     * Method to handle the not your turn event
     */
    @Override
    public void notYourTurn() {

    }

    /**
     * Method to handle the ask assistant request
     * @param chooseAssistantRequest choose assistant request message
     */
    @Override
    public void askAssistant(ChooseAssistantRequest chooseAssistantRequest) {
        view.askAssistant(chooseAssistantRequest.getAvailableAssistantIds());
    }

    /**
     * Method to handle the move student from entrance request
     * @param moveStudentFromEntranceRequest move student from entrance message
     */
    @Override
    public void askMoveStudentFromEntrance(MoveStudentFromEntranceRequest moveStudentFromEntranceRequest) {
        view.askMoveStudentFromEntrance(moveStudentFromEntranceRequest.getHallColourAvailability());

    }

    /**
     * Method to handle the move mother nature from entrance request
     * @param message message with the available isle indexes to move mother nature to
     */
    @Override
    public void moveMotherNature (MoveMotherNatureRequest message){
        view.moveMotherNature(message.getAvailableIsleIndexes());
    }

    /**
     * Method to handle the ask cloud request
     * @param message message with the available cloud indexes to choose from
     */
    @Override
    public void askCloud(ChooseCloudRequest message) {
        view.askCloud(message.getAvailableCloudIndexes());
    }

    /**
     * Method to handle the ask nickname request
     * @param message rejoin message
     */
    @Override
    public void askNickname(InvalidUsernameResponse message) {
        view.askUserInfo(message.isRejoin());
    }

    /**
     * Method to handle the end game event
     * @param message end game message
     */
    @Override
    public void endGame(EndGameEvent message) {
        view.endGame(message.getCausingUser(),message.getCause());
    }

    /**
     * Method to handle the choose island request
     * @param message info of the chosen island
     */
    @Override
    public void chooseIsland(ChooseIslandRequest message) {
        view.askChooseIsland(message.isSetBan(),message.isCalculateInfluence());
    }

    /**
     * Method to handle the choose colour request
     * @param message info of the chosen colour
     */
    @Override
    public void chooseColour(ChooseColourRequest message) {
        view.askChooseColour(message.getToDiscard(),message.isToExclude());
    }

    /**
     * Method to handle the choose student from card request
     * @param message info of the chosen student
     */
    @Override
    public void moveStudentsFromCard(MoveStudentFromCardRequest message) {
        view.askMoveStudentsFromCard(message.getCharacterId(),message.getDestination(),message.getStudentsToMove(),message.isCanMoveLess());
    }

    /**
     * Method to handle the exchange students request
     * @param message info of the exchange students
     */
    @Override
    public void exchangeStudents(ExchangeStudentsRequest message) {
        view.askExchangeStudents(message.getCharacterId(),message.getNumberOfStudents(),message.getFrom(),message.getTo());
    }

    /**
     * Method to handle the ask login info request
     * @param message info on the lobby created
     */
    @Override
    public void askLoginInfo(AskLoginInfoRequest message) {
        view.askLoginInfo(message.getUsername(),message.canJoinLobby(),message.canRejoinLobby());
    }

    /**
     * Method to handle the notification on the player disconnection
     * @param playerDisconnectedEvent message of the disconnection
     */
    @Override
    public void notifyPlayerDisconnection(PlayerDisconnectedEvent playerDisconnectedEvent) {
        view.notifyDisconnection(playerDisconnectedEvent.getDisconnectedPlayerName());
    }

}
