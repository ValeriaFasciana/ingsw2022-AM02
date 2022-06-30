package it.polimi.ingsw.server.controller.listeners;

public interface EndGameListener {

    /**
     *
     * @param winnerPlayer
     */
    void onEndGame(String winnerPlayer);

}
