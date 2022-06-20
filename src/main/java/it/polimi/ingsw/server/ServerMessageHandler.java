package it.polimi.ingsw.server;

import it.polimi.ingsw.network.messages.clienttoserver.events.*;
import it.polimi.ingsw.server.controller.GameController;
import it.polimi.ingsw.network.messages.Message;

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

    @Override
    public void useCharacterEffect(UseCharacterEffectRequest message) {
        this.controller.useCharacterEffect(message.getUsername(),message.getCharacterId());
    }

    public void setLobby(GameLobby lobby) {
        this.lobby = lobby;
    }

    @Override
    public void joinLobby(JoinLobbyResponse joinLobbyResponse) {
        lobbyManager.addPlayerToLobby(joinLobbyResponse.getUsername(),joinLobbyResponse.getPlayerNickName(),joinLobbyResponse.isRejoin());
    }

    public void parseMessageFromServerToClient(Message message) {
        lobby.sendMessage(message.getUsername(), message);
    }

//    @Override
//    public void setLobbyInfo(CreateLobbyResponse createLobbyResponse) {
//        lobby.setInfo(createLobbyResponse.getPlayerName(), createLobbyResponse.getNumberOfPlayers(), createLobbyResponse.getExpertVariant());
//    }

    @Override
    public void setNickname(JoinLobbyResponse joinLobbyResponse) {
        lobby.setUsername(joinLobbyResponse.getPlayerNickName());
    }

    @Override
    public void setChosenAssistant(ChooseAssistantResponse chooseAssistantResponse) {
        controller.setChosenAssistant(chooseAssistantResponse.getUsername(), chooseAssistantResponse.getChosenAssistantIndex());
    }

    @Override
    public void moveStudentToHall(MoveStudentToHallResponse moveStudentToHallResponse) {
        controller.moveStudentToHall(moveStudentToHallResponse.getUsername(),moveStudentToHallResponse.getStudentColour());
    }

    @Override
    public void moveMotherNature(MoveMotherNatureResponse moveMotherNatureResponse) {
        controller.moveMotherNature(moveMotherNatureResponse.getUsername(),moveMotherNatureResponse.getIsleIndex());
    }

    @Override
    public void moveStudentToIsle(MoveStudentToIsleResponse moveStudentToIsleResponse) {
        controller.moveStudentToIsle(   moveStudentToIsleResponse.getUsername(),
                                        moveStudentToIsleResponse.getStudentColour(),
                                        moveStudentToIsleResponse.getIsleIndex());
    }

    @Override
    public void setChosenCloud(ChooseCloudResponse chooseCloudResponse) {
        controller.setChosenCloud(chooseCloudResponse.getUsername(),chooseCloudResponse.getChosenCloudIndex());
    }

    @Override
    public void handleColourChoosing(ChooseColourResponse message) {
        controller.handleColourChoosing(message.getUsername(),message.getChosenColour(),message.getToDiscard(),message.isToExclude());
    }

    @Override
    public void handleIsleChoosing(ChooseIslandResponse message) {
        controller.handleIsleChoosing(message.getUsername(),message.getIsleIndex(),message.isCalculateInfluence(),message.isSetBan());
    }

    @Override
    public void moveStudentsFromCard(MoveStudentFromCardResponse message) {
        controller.moveStudentsFromCard(message.getUsername(),message.getCharacterId(),message.getDestination(),message.getMovedStudents(),message.getIsleIndex());
    }

    @Override
    public void handleStudentExchange(ExchangeStudentsResponse message) {
        controller.handleStudentExchange(message.getUsername(),message.getCharacterId(),message.getFrom(),message.getTo(),message.getFromMap(),message.getToMap());
    }


    @Override
    public void handleClientDisconnection(ClientHandler client) {
        lobby.handleClientDisconnection(client);
    }

    @Override
    public void createLobby(CreateLobbyResponse message) {
        lobbyManager.createLobby(message.getUsername(),message.getPlayerName(),message.getExpertVariant(),message.getNumberOfPlayers());
    }

//    @Override
//    public void moveStudent(StudentMovementRequest message) {
//        //controller.moveStudent(message.getUsername(), message.getPayload());
//    }

//    @Override
//    public void onMatchReloadResponse(ChooseToReloadMatchResponse message) {
//        lobby.reloadMatch(message.wantToReload());
//    }
//

//    public void playAssistantCard(PlayAssistantRequest message) {
//       // controller.playAssistant(message.getUserName(),message.getPayload());
//    }
//


//    @Override
//    public void moveMotherNature(MotherNatureMovementRequest message) {
//       // controller.moveMotherNature(message.getUsername(), message.getGod());
//    }

//    @Override
//    public void chooseCloud(ChooseCloudRequest message) {
//        controller.chooseCloud(message.getUsername(),message.getPayload());
//    }
//

//    @Override
//    public void chooseIsleForInfluenceCalculation(ChooseIsleRequest message) {
//        controller.chooseIsleForInfluenceCalculation(message.getUsername(),message.getPayload());
//    }
//

//    @Override
//    public void setBanOnIsle(SetBanRequest message) {
//        controller.setBanOnIsle(message.getUsername(), message.getTargetWorker());
//    }
//

//    @Override
//    public void setExcludedColour(SetExcludedColourRequest message) {
//        controller.setExcludedColour(message.getUsername(), message.getTargetWorker());
//    }

//    @Override
//    public void setColourToDiscard(setExcludedColourRequest message) {
//        controller.setColourToDiscard(message.getUsername(), message.getTargetWorker());
//    }

}



