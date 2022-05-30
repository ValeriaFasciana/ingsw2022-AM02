package it.polimi.ingsw.server.controller.state;

import it.polimi.ingsw.network.messages.Type;
import it.polimi.ingsw.network.messages.servertoclient.events.ChooseAssistantRequest;
import it.polimi.ingsw.network.messages.servertoclient.events.MoveMotherNatureRequest;
import it.polimi.ingsw.server.controller.GameController;

import java.util.ArrayList;

public class MoveMotherNatureState extends GameState{
    protected MoveMotherNatureState(GameController controller) {
        super(controller);
    }

    @Override
    public void onInit() {
        String currentPlayer =  controller.getCurrentPlayerName();
        controller.respond(new MoveMotherNatureRequest(currentPlayer, Type.SERVER_REQUEST, (ArrayList<Integer>) controller.getGame().getMotherNatureAvailableIslands()));
    }

    @Override
    public void setNext() {
        controller.setState(new ChooseCloudState(controller));
    }
}
