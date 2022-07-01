package it.polimi.ingsw.server.controller.state;

import it.polimi.ingsw.network.messages.servertoclient.events.MoveStudentFromEntranceRequest;
import it.polimi.ingsw.server.controller.GameController;

public class MoveStudentState extends GameState {

    private int movementCounter;

    public MoveStudentState(GameController controller) {
        super(controller);
        this.movementCounter =  controller.getGame().getSettings().getNumberOfStudentsToMove();
    }

    /**
     *
     */
    @Override
    public void onInit() {
        String currentPlayer =  controller.getCurrentPlayerName();
        if(movementCounter > 0){
            controller.respond(new MoveStudentFromEntranceRequest(currentPlayer,controller.getGame().getPlayerHallAvailability(currentPlayer)));
            return;
        }
        setNext();
    }

    /**
     *
     */
    @Override
    public void setNext() {
        movementCounter--;
        if(movementCounter == 0){
            isOver = true;
            controller.setState(new MoveMotherNatureState(controller));
            return;
        }
        onInit();
    }
}
