package it.polimi.ingsw.server.model.game;

import it.polimi.ingsw.server.controller.listeners.BoardUpdateListener;
import it.polimi.ingsw.server.model.action.Action;
import it.polimi.ingsw.shared.enums.PawnColour;
import it.polimi.ingsw.shared.enums.Phase;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface GameInterface {
    void useAction(Action action);

    void addBoardUpdateListener(BoardUpdateListener listener);


    Set<Integer> getPlayableAssistants();

    String getCurrentPlayerName();

    void playAssistantCard(int assistantIndex);

    void endCurrentPlayerTurn();

    Phase getRoundPhase();

    List<Integer> getMotherNatureAvailableIslands();

    Map<PawnColour, Boolean> getPlayerHallAvailability(String currentPlayer);

    int getNumberOfPlayers();

    List<Integer> getAvailableClouds();

    GameSettings getSettings();
}
