package it.polimi.ingsw.server;

import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.messages.clienttoserver.events.*;
import it.polimi.ingsw.shared.enums.GameMode;

public interface ServerMessageVisitor {

    void parseMessageFromServerToClient(Message message);

    void setLobbyInfo(LobbyInfoResponse lobbyInfoResponse);

    void setNickname(NicknameResponse nicknameResponse);

    void setChosenAssistant(ChooseAssistantResponse chooseAssistantResponse);

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
