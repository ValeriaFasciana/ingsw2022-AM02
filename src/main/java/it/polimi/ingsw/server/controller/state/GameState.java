package it.polimi.ingsw.server.controller.state;

import it.polimi.ingsw.server.controller.GameController;
import it.polimi.ingsw.shared.enums.Phase;

/**
 * Class to handle different states of the game
 */
public abstract class GameState {
    GameController controller;
    boolean isOver;

    protected GameState(GameController controller) {
        this.controller = controller;
        this.isOver = false;
    }

    /**
     * Method overridden to handle state at initialization
     */
    public abstract void onInit();

    /**
     * Set Next state
     */
    public abstract void setNext();

    /**
     * State of disconnection of a player
     * @param playerName player disconnected
     */
    public void onDisconnect(String playerName){
        if(controller.getCurrentPlayerName().equals(playerName)){
            controller.getGame().endCurrentPlayerTurn();
            if(controller.getGame().getRoundPhase().equals(Phase.ACTION)){
                controller.setState(new ChooseAssistantState(controller));
            }
            if(controller.getGame().getRoundPhase().equals(Phase.PLANNING)){
                controller.setState(new MoveStudentState(controller));
            }
        }
    }

    public GameController getController() {
        return controller;
    }

    public void setController(GameController controller) {
        this.controller = controller;
    }

    public boolean isOver() {
        return isOver;
    }

    public void setOver(boolean over) {
        isOver = over;
    }
}
