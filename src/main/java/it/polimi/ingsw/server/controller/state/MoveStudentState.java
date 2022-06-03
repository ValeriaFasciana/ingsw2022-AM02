package it.polimi.ingsw.server.controller.state;

import it.polimi.ingsw.network.messages.Type;
import it.polimi.ingsw.network.messages.servertoclient.events.ChooseAssistantRequest;
import it.polimi.ingsw.network.messages.servertoclient.events.MoveStudentFromEntranceRequest;
import it.polimi.ingsw.server.controller.GameController;

public class MoveStudentState extends GameState {
    private int movementCounter;
    public MoveStudentState(GameController controller) {
        super(controller);
    }
    @Override
    public void onInit() {
        String currentPlayer =  controller.getCurrentPlayerName();
        controller.respond(new MoveStudentFromEntranceRequest(currentPlayer,controller.getGame().getPlayerHallAvailability(currentPlayer)));
    }

    @Override

    public void setNext() {
        movementCounter++;
        if(movementCounter == controller.getGame().getSettings().getNumberOfStudentsToMove()){
            controller.setState(new MoveMotherNatureState(controller));
            return;
        }
        onInit();
    }
}
