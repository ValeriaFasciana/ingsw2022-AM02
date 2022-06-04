package it.polimi.ingsw.server.controller.listeners;

import it.polimi.ingsw.network.data.BoardData;

public interface BoardUpdateListener {

    void onGameInit(BoardData boardData);

    void onBoardUpdate(BoardData boardData);
}
