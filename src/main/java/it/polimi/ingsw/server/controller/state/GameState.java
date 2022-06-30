package it.polimi.ingsw.server.controller.state;

import it.polimi.ingsw.server.controller.GameController;
import it.polimi.ingsw.shared.enums.Phase;

public abstract class GameState {
    GameController controller;

    protected GameState(GameController controller) {
        this.controller = controller;
    }

    public abstract void onInit();

    public abstract void setNext();

    /**
     *
     * @param playerName
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
}
