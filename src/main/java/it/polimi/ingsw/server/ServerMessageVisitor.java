package it.polimi.ingsw.server;

import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.messages.clienttoserver.events.GameModeRequest;
import it.polimi.ingsw.network.messages.clienttoserver.events.JoinLobbyRequest;
import it.polimi.ingsw.network.messages.clienttoserver.events.LoginRequest;

public interface ServerMessageVisitor {
    default void login(LoginRequest message) {
        cannotHandleMessage(message);
    }

    default void joinLobby(JoinLobbyRequest message) {
        cannotHandleMessage(message);
    }

    default void gameMode(GameModeRequest message) {
        cannotHandleMessage(message);
    }

    void cannotHandleMessage(Message message);
//    void onMatchReloadResponse(ChooseToReloadMatchResponse message);
//
//    void moveStudent(StudentMovementRequest message);
//
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
