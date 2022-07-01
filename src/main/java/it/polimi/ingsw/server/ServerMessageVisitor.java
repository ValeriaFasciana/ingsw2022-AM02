package it.polimi.ingsw.server;

import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.messages.clienttoserver.events.*;
import it.polimi.ingsw.server.controller.GameController;
import it.polimi.ingsw.server.lobby.GameLobby;

public interface ServerMessageVisitor {

    /**
     * Method to parse message from server to client
     * @param message message to parse
     */
    void parseMessageFromServerToClient(Message message);

    /**
     * Method to handle the chosen card
     * @param chooseAssistantResponse choose assistant response
     */
    void setChosenAssistant(ChooseAssistantResponse chooseAssistantResponse);

    /**
     *Method to handle Move Student To Hall response
     * @param moveStudentToHallResponse move student to hall response message
     */
    void moveStudentToHall(MoveStudentToHallResponse moveStudentToHallResponse);

    /**
     *Method to handle move mother nature response
     * @param moveMotherNatureResponse move mother nature response message
     */
    void moveMotherNature(MoveMotherNatureResponse moveMotherNatureResponse);

    /**
     *Method to handle Move Student To isle response
     * @param moveStudentToIsleResponse move student to isle response message
     */
    void moveStudentToIsle(MoveStudentToIsleResponse moveStudentToIsleResponse);

    /**
     *Method to handle choose a cloud
     * @param chooseCloudResponse choose a cloud message response
     */
    void setChosenCloud(ChooseCloudResponse chooseCloudResponse);

    void setController(GameController controller);

    /**
     * Method to handle use character effect response
     * @param useCharacterEffectRequest server message visitor
     */
    void useCharacterEffect(UseCharacterEffectRequest useCharacterEffectRequest);

    /**
     * Method to handle colour choosing
     * @param message choose a colour message response
     */
    void handleColourChoosing(ChooseColourResponse message);

    /**
     * Method to handle isle choosing
     * @param message choose an isle message response
     */
    void handleIsleChoosing(ChooseIslandResponse message);

    /**
     *Method to handle to move a student from card
     * @param message move a student from a card message response
     */
    void moveStudentsFromCard(MoveStudentFromCardResponse message);

    /**
     *Method to handle student exchange
     * @param exchangeStudentsResponse student exchange message response
     */
    void handleStudentExchange(ExchangeStudentsResponse exchangeStudentsResponse);

    /**
     *Method to handle client disconnection
     * @param virtualClient instance that represent client inside the server
     */
    void handleClientDisconnection(VirtualClient virtualClient);

    /**
     *Method to handle create lobby response
     * @param createLobbyResponse create lobby response message
     */
    void createLobby(CreateLobbyResponse createLobbyResponse);

    /**
     * Method to set a lobby
     * @param gameLobby created lobby
     */
    void setLobby(GameLobby gameLobby);

    /**
     * Method to handle join lobby response
     * @param joinLobbyResponse joined lobby response
     */
    void joinLobby(JoinLobbyResponse joinLobbyResponse);

    /**
     *Method to handle the end of a lobby
     */
    void endLobby();
}
