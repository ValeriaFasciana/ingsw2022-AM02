package it.polimi.ingsw.client;



import it.polimi.ingsw.network.messages.servertoclient.events.*;


public interface ClientMessageVisitor {

    /**
     * Method to handle the game created event
     * @param message game created message
     */
    void gameCreated(GameCreatedEvent message);

    /**
     * Method to handle the joined lobby event
     * @param message joined lobby message
     */
    void joinedLobby(JoinedLobbyResponse message);

    /**
     * Method to handle new lobby created event
     * @param message lobby created event
     */
    void newLobbyCreated(LobbyCreatedResponse message);

    /**
     * Method to handle the board update event
     * @param message board update message
     */
    void boardUpdate(BoardUpdateResponse message);

    /**
     * Method to handle the not your turn event
     */
    void notYourTurn();

    /**
     * Method to handle the ask assistant request
     * @param chooseAssistantRequest choose assistant request message
     */
    void askAssistant(ChooseAssistantRequest chooseAssistantRequest);

    /**
     * Method to handle the move student from entrance request
     * @param moveStudentFromEntranceRequest move student from entrance message
     */
    void askMoveStudentFromEntrance(MoveStudentFromEntranceRequest moveStudentFromEntranceRequest);

    /**
     * Method to handle the move mother nature from entrance request
     * @param message message with the available isle indexes to move mother nature to
     */
    void moveMotherNature(MoveMotherNatureRequest message);

    /**
     * Method to handle the ask cloud request
     * @param message message with the available cloud indexes to choose from
     */
    void askCloud(ChooseCloudRequest message);

    /**
     * Method to handle the ask nickname request
     * @param message invalid username response message
     */
    void askNickname(InvalidUsernameResponse message);

    /**
     * Method to handle the end game event
     * @param endGameEvent end game message
     */
    void endGame(EndGameEvent endGameEvent);

    /**
     * Method to handle the choose island request
     * @param chooseIslandRequest info of the chosen island
     */
    void chooseIsland(ChooseIslandRequest chooseIslandRequest);

    /**
     * Method to handle the choose colour request
     * @param chooseColourRequest info of the chosen colour
     */
    void chooseColour(ChooseColourRequest chooseColourRequest);

    /**
     * Method to handle the choose student from card request
     * @param moveStudentFromCardRequest info of the chosen student
     */
    void moveStudentsFromCard(MoveStudentFromCardRequest moveStudentFromCardRequest);

    /**
     * Method to handle the exchange students request
     * @param exchangeStudentsRequest info of the exchange students
     */
    void exchangeStudents(ExchangeStudentsRequest exchangeStudentsRequest);

    /**
     * Method to handle the ask login info request
     * @param askLobbyInfoRequest info on the lobby created
     */
    void askLoginInfo(AskLoginInfoRequest askLobbyInfoRequest);

    /**
     * Method to handle the notification on the player disconnection
     * @param playerDisconnectedEvent message of the disconnection
     */
    void notifyPlayerDisconnection(PlayerDisconnectedEvent playerDisconnectedEvent);
}
