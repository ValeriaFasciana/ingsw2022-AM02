package it.polimi.ingsw.server.controller.listeners;

import it.polimi.ingsw.network.data.BoardData;

public interface BoardUpdateListener {

    /**
     * The listener that notifies the initialization of the game
     * @param boardData board data
     * @param expertMode if it's true, the game mode is expert
     */
    void onGameInit(BoardData boardData, boolean expertMode);

    /**
     * The listener that updates the board
     * @param boardData board data
     */
    void onBoardUpdate(BoardData boardData);
}
