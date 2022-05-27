package it.polimi.ingsw.server.model.game;

import it.polimi.ingsw.server.controller.listeners.BoardUpdateListener;
import it.polimi.ingsw.server.model.action.Action;

import java.util.Set;

public interface GameInterface {
    void useAction(Action action);

    void addBoardUpdateListener(BoardUpdateListener listener);


    Set<Integer> getPlayableAssistants();

    String getCurrentPlayerName();

    void playAssistantCard(int assistantIndex);

    void endCurrentPlayerTurn();
}
