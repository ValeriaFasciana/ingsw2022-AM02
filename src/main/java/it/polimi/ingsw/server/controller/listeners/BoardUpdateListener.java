package it.polimi.ingsw.server.controller.listeners;

import it.polimi.ingsw.server.model.BoardData;

public interface BoardUpdateListener {

    void onGameInit(BoardData boardData);

    void onBoardUpdate(BoardData boardData);
}
