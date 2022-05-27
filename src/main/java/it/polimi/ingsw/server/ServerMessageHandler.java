package it.polimi.ingsw.server;

import it.polimi.ingsw.network.messages.clienttoserver.events.*;
import it.polimi.ingsw.server.controller.GameController;
import it.polimi.ingsw.network.messages.Message;

public class ServerMessageHandler implements ServerMessageVisitor {

    GameLobby lobby;
    GameController controller;


//
//    public ServerMessageHandler(GameLobby lobby) {
//            this.lobby = lobby;
//    }


    public void parseMessageFromServerToClient(Message message) {
        lobby.sendMessage(message.getUsername(), message);
    }

    @Override
    public void setLobbyInfo(LobbyInfoResponse lobbyInfoResponse) {
        lobby.setInfo(lobbyInfoResponse.getPlayerName(), lobbyInfoResponse.getNumberOfPlayers(), lobbyInfoResponse.isExpertVariant());
    }

    @Override
    public void setNickname(NicknameResponse nicknameResponse) {

    }

    @Override
    public void setChosenAssistant(ChooseAssistantResponse chooseAssistantResponse) {
        controller.setChosenAssistant(chooseAssistantResponse.getUsername(), chooseAssistantResponse.getChosenAssistantIndex());
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



