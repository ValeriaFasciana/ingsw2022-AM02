package it.polimi.ingsw.server.controller.state;

import it.polimi.ingsw.network.messages.servertoclient.events.ChooseAssistantRequest;
import it.polimi.ingsw.server.controller.GameController;
import it.polimi.ingsw.shared.enums.Phase;

public class ChooseAssistantState extends GameState{

    public ChooseAssistantState(GameController controller) {
        super(controller);
    }

    /**
     * Method to handle assistant cards state at initialization
     */
    @Override
    public void onInit() {
        String currentPlayer =  controller.getCurrentPlayerName();
        controller.respond(new ChooseAssistantRequest(currentPlayer,controller.getGame().getPlayableAssistants()));
    }

    /**
     * Set Next state
     */
    @Override
    public void setNext() {
        controller.getGame().endCurrentPlayerTurn();
        if(controller.getGame().getRoundPhase().equals(Phase.ACTION)){
            isOver = true;
            controller.setState(new MoveStudentState(controller));
            return;
        }
        onInit();
    }

    /**
     * State of disconnection of a player
     * @param playerName player disconnected
     */
    public void onDisconnect(String playerName){
        setNext();
    }

}
