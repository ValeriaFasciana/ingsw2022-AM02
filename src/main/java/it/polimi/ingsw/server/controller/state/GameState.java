package it.polimi.ingsw.server.controller.state;

import it.polimi.ingsw.server.controller.GameController;

public abstract class GameState {
    GameController controller;

    protected GameState(GameController controller) {
        this.controller = controller;
    }

    public abstract void onInit();

    public abstract void setNext();
}
