package it.polimi.ingsw.shared;

import it.polimi.ingsw.shared.messages.Message;
import it.polimi.ingsw.shared.messages.fromClientToServer.*;

public interface ServerMessageManager {

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
}
