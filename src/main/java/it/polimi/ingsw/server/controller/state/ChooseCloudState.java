package it.polimi.ingsw.server.controller.state;

import it.polimi.ingsw.network.messages.Type;
import it.polimi.ingsw.network.messages.servertoclient.events.ChooseCloudRequest;
import it.polimi.ingsw.server.controller.GameController;
import it.polimi.ingsw.shared.enums.Phase;

import java.util.Set;

public class ChooseCloudState extends GameState {

    public ChooseCloudState(GameController controller) {
        super(controller);
    }

    @Override
    public void onInit() {
        String currentPlayer =  controller.getCurrentPlayerName();
        Set<Integer> availableCloudIndexes = controller.getGame().getAvailableClouds();
        if(!availableCloudIndexes.isEmpty()){
            controller.respond(new ChooseCloudRequest(currentPlayer, Type.SERVER_REQUEST, availableCloudIndexes));
        }else{
            setNext();
        }
    }

    @Override
    public void setNext() {
        controller.getGame().endCurrentPlayerTurn();
        if(controller.getGame().getRoundPhase().equals(Phase.ACTION)){
            controller.setState(new MoveStudentState(controller));
        }else{
            controller.setState(new ChooseAssistantState(controller));
        }

    }
}
