package it.polimi.ingsw.server.controller.listeners;

import it.polimi.ingsw.network.data.BoardData;

public interface BoardUpdateListener {

    /**
     *
     * @param boardData
     * @param expertMode
     */
    void onGameInit(BoardData boardData, boolean expertMode);

    /**
     *
     * @param boardData
     */
    void onBoardUpdate(BoardData boardData);
}
