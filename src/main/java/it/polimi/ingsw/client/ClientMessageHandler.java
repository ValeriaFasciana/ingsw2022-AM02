package it.polimi.ingsw.client;

import it.polimi.ingsw.shared.ViewInterface;
import it.polimi.ingsw.network.messages.servertoclient.events.GameCreatedEvent;
import it.polimi.ingsw.network.messages.servertoclient.events.JoinLobbyResponse;
import it.polimi.ingsw.network.messages.servertoclient.events.LoginResponse;
import it.polimi.ingsw.server.model.BoardData;

public class ClientMessageHandler implements ClientMessageVisitor {

    ViewInterface view;
    Client client;



    @Override
    public void Logged(LoginResponse message) {

    }

    @Override
    public void GameCreated(GameCreatedEvent message) {

    }

    @Override
    public void JoinedLobby(JoinLobbyResponse message) {

    }

    @Override
    public void boardUpdate(BoardData boardData) {

    }

    @Override
    public void notYourTurn() {

    }
}
