package it.polimi.ingsw.server.controller.state;

import it.polimi.ingsw.network.messages.Type;
import it.polimi.ingsw.network.messages.servertoclient.events.ChooseCloudRequest;
import it.polimi.ingsw.server.controller.GameController;

import java.util.ArrayList;

public class ChooseCloudState extends GameState {
    public ChooseCloudState(GameController controller) {
        super(controller);
    }

    @Override
    public void onInit() {
        String currentPlayer =  controller.getCurrentPlayerName();
        controller.respond(new ChooseCloudRequest(currentPlayer, Type.SERVER_REQUEST, controller.getGame().getAvailableClouds()));
    }

    @Override
    public void setNext() {
        controller.getGame().endCurrentPlayerTurn();;
        controller.setState(new ChooseAssistantState(controller));
    }
}
