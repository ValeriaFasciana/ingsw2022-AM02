package it.polimi.ingsw.server.controller.listeners;

public interface EndGameListener {

    /**
     * Listener that ends the game
     * @param winnerPlayer winner player
     */
    void onEndGame(String winnerPlayer);

}
