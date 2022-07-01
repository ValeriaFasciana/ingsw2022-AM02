package it.polimi.ingsw.server;

import it.polimi.ingsw.network.messages.clienttoserver.events.*;
import it.polimi.ingsw.server.controller.GameController;
import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.server.lobby.GameLobby;
import it.polimi.ingsw.server.lobby.LobbyManager;

public class ServerMessageHandler implements ServerMessageVisitor {

    GameLobby lobby;
    GameController controller;
    LobbyManager lobbyManager;

    public ServerMessageHandler(LobbyManager lobbyManager) {
        this.lobbyManager = lobbyManager;
    }

    public void setController(GameController controller){
        this.controller = controller;
    }

    /**
     * Method to handle use character effect response
     * @param message server message visitor
     */
    @Override
    public void useCharacterEffect(UseCharacterEffectRequest message) {
        this.controller.useCharacterEffect(message.getUsername(),message.getCharacterId());
    }

    /**
     * Method to set a lobby
     * @param lobby created lobby
     */
    public void setLobby(GameLobby lobby) {
        this.lobby = lobby;
    }

    /**
     * Method to handle join lobby response
     * @param joinLobbyResponse joined lobby response
     */
    @Override
    public void joinLobby(JoinLobbyResponse joinLobbyResponse) {
        lobbyManager.addPlayerToLobby(joinLobbyResponse.getUsername(),joinLobbyResponse.getPlayerNickName(),joinLobbyResponse.isRejoin());
    }

    /**
     *Method to handle the end of a lobby
     */
    @Override
    public void endLobby() {
        lobbyManager.endLobby(lobby);
    }

    /**
     * Method to parse message from server to client
     * @param message message to parse
     */
    public void parseMessageFromServerToClient(Message message) {
        lobby.sendMessage(message.getUsername(), message);
    }

    /**
     * Method to handle the chosen card
     * @param chooseAssistantResponse choose assistant response
     */
    @Override
    public void setChosenAssistant(ChooseAssistantResponse chooseAssistantResponse) {
        controller.setChosenAssistant(chooseAssistantResponse.getUsername(), chooseAssistantResponse.getChosenAssistantIndex());
    }

    /**
     *Method to handle Move Student To Hall response
     * @param moveStudentToHallResponse move student to hall response message
     */
    @Override
    public void moveStudentToHall(MoveStudentToHallResponse moveStudentToHallResponse) {
        controller.moveStudentToHall(moveStudentToHallResponse.getUsername(),moveStudentToHallResponse.getStudentColour());
    }

    /**
     *Method to handle move mother nature response
     * @param moveMotherNatureResponse move mother nature response message
     */
    @Override
    public void moveMotherNature(MoveMotherNatureResponse moveMotherNatureResponse) {
        controller.moveMotherNature(moveMotherNatureResponse.getUsername(),moveMotherNatureResponse.getIsleIndex());
    }

    /**
     *Method to handle Move Student To isle response
     * @param moveStudentToIsleResponse move student to isle response message
     */
    @Override
    public void moveStudentToIsle(MoveStudentToIsleResponse moveStudentToIsleResponse) {
        controller.moveStudentToIsle(   moveStudentToIsleResponse.getUsername(),
                                        moveStudentToIsleResponse.getStudentColour(),
                                        moveStudentToIsleResponse.getIsleIndex());
    }

    /**
     *Method to handle choose a cloud
     * @param chooseCloudResponse choose a cloud message response
     */
    @Override
    public void setChosenCloud(ChooseCloudResponse chooseCloudResponse) {
        controller.setChosenCloud(chooseCloudResponse.getUsername(),chooseCloudResponse.getChosenCloudIndex());
    }

    /**
     * Method to handle colour choosing
     * @param message choose a colour message response
     */
    @Override
    public void handleColourChoosing(ChooseColourResponse message) {
        controller.handleColourChoosing(message.getUsername(),message.getChosenColour(),message.getToDiscard(),message.isToExclude());
    }

    /**
     * Method to handle isle choosing
     * @param message choose an isle message response
     */
    @Override
    public void handleIsleChoosing(ChooseIslandResponse message) {
        controller.handleIsleChoosing(message.getUsername(),message.getIsleIndex(),message.isCalculateInfluence(),message.isSetBan());
    }

    /**
     *Method to handle to move a student from card
     * @param message move a student from a card message response
     */
    @Override
    public void moveStudentsFromCard(MoveStudentFromCardResponse message) {
        controller.moveStudentsFromCard(message.getUsername(),message.getCharacterId(),message.getDestination(),message.getMovedStudents(),message.getIsleIndex());
    }

    /**
     *Method to handle student exchange
     * @param message student exchange message response
     */
    @Override
    public void handleStudentExchange(ExchangeStudentsResponse message) {
        controller.handleStudentExchange(message.getUsername(),message.getCharacterId(),message.getFrom(),message.getTo(),message.getFromMap(),message.getToMap());
    }

    /**
     *Method to handle client disconnection
     * @param client instance that represent client inside the server
     */
    @Override
    public void handleClientDisconnection(VirtualClient client) {
        if(lobby == null){
            return;
        }
        lobbyManager.handleClientDisconnection(client);
    }

    /**
     *Method to handle create lobby response
     * @param message create lobby response message
     */
    @Override
    public void createLobby(CreateLobbyResponse message) {
        lobbyManager.createLobby(message.getUsername(),message.getPlayerName(),message.getExpertVariant(),message.getNumberOfPlayers());
    }

}



