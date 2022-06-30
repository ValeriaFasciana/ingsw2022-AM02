package it.polimi.ingsw.server;

import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.messages.clienttoserver.events.*;
import it.polimi.ingsw.server.controller.GameController;
import it.polimi.ingsw.server.lobby.GameLobby;

public interface ServerMessageVisitor {

    void parseMessageFromServerToClient(Message message);

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

    void handleClientDisconnection(VirtualClient virtualClient);

    void createLobby(CreateLobbyResponse createLobbyResponse);

    void setLobby(GameLobby gameLobby);

    void joinLobby(JoinLobbyResponse joinLobbyResponse);

    void endLobby();
}
