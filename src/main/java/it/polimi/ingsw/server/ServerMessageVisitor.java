package it.polimi.ingsw.server;

import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.messages.clienttoserver.events.*;
import it.polimi.ingsw.server.controller.GameController;

public interface ServerMessageVisitor {

    void parseMessageFromServerToClient(Message message);

   // void setLobbyInfo(CreateLobbyResponse createLobbyResponse);

    void setNickname(JoinLobbyResponse joinLobbyResponse);

    void setChosenAssistant(ChooseAssistantResponse chooseAssistantResponse);

    void moveStudentToHall(MoveStudentToHallResponse moveStudentToHallResponse);

    void moveMotherNature(MoveMotherNatureResponse moveMotherNatureResponse);

    void moveStudentToIsle(MoveStudentToIsleResponse moveStudentToIsleResponse);

    void setChosenCloud(ChooseCloudResponse chooseCloudResponse);

    void setController(GameController controller);

    void useCharacterEffect(UseCharacterEffectRequest useCharacterEffectRequest);

    void handleColourChoosing(ChooseColourResponse message);

    void handleIsleChoosing(ChooseIslandResponse message);

    void moveStudentsFromCard(MoveStudentFromCardResponse message);

    void handleStudentExchange(ExchangeStudentsResponse exchangeStudentsResponse);

    void handleClientDisconnection(ClientHandler clientHandler);

    void createLobby(CreateLobbyResponse createLobbyResponse);

    void setLobby(GameLobby gameLobby);

    void joinLobby(JoinLobbyResponse joinLobbyResponse);

//    void moveMotherNature(MotherNatureMovementRequest message);
//
//    void chooseCloud(ChooseCloudRequest message);
//
//    void chooseIsleForInfluenceCalculation(ChooseIsleRequest message);
//
//    void setBanOnIsle(SetBanRequest message);
//
//    void setExcludedColour(SetExcludedColourRequest message);
//
//    void setColourToDiscard(setExcludedColourRequest message);
}
