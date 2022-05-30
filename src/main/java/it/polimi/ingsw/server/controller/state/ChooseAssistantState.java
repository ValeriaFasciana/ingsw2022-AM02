package it.polimi.ingsw.server.controller.state;

import it.polimi.ingsw.network.messages.Type;
import it.polimi.ingsw.network.messages.servertoclient.events.ChooseAssistantRequest;
import it.polimi.ingsw.server.controller.GameController;
import it.polimi.ingsw.shared.enums.Phase;

public class ChooseAssistantState extends GameState{

    public ChooseAssistantState(GameController controller) {
        super(controller);
    }

    @Override
    public void onInit() {
        String currentPlayer =  controller.getCurrentPlayerName();
        controller.respond(new ChooseAssistantRequest(currentPlayer, Type.SERVER_REQUEST,controller.getGame().getPlayableAssistants()));
    }

    @Override
    public void setNext() {
        controller.getGame().endCurrentPlayerTurn();
        if(controller.getGame().getRoundPhase().equals(Phase.ACTION)){
            controller.setState(new MoveStudentState(controller));
            return;
        }
        onInit();
    }

}
