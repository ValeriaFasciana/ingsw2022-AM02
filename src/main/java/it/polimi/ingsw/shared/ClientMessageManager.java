package it.polimi.ingsw.shared;

import it.polimi.ingsw.shared.messages.fromServerToClient.*;

public interface ClientMessageManager {

    void Logged(LoginResponse message);

    void GameCreated(GameCreatedEvent message);

    void GameMode(GameModeResponse message);

    void JoinedLobby(JoinLobbyResponse message);
}
