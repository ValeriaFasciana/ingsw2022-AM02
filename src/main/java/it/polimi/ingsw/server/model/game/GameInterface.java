package it.polimi.ingsw.server.model.game;

import it.polimi.ingsw.server.controller.GameController;
import it.polimi.ingsw.server.controller.listeners.BoardUpdateListener;
import it.polimi.ingsw.server.controller.listeners.EndGameListener;
import it.polimi.ingsw.server.model.action.Action;
import it.polimi.ingsw.server.model.cards.characters.CharacterEffect;
import it.polimi.ingsw.shared.enums.PawnColour;
import it.polimi.ingsw.shared.enums.Phase;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public interface GameInterface {
    void useAction(Action action);


    void addBoardUpdateListener(BoardUpdateListener listener);

    void calculateInfluence(int isleIndex, Optional<PawnColour> excludedColour);

    Set<Integer> getPlayableAssistants();

    String getCurrentPlayerName();

    void playAssistantCard(int assistantIndex);

    void endCurrentPlayerTurn();

    Phase getRoundPhase();

    List<Integer> getMotherNatureAvailableIslands();

    Map<PawnColour, Boolean> getPlayerHallAvailability(String currentPlayer);


    Set<Integer> getAvailableClouds();

    GameSettings getSettings();

    void create();

    CharacterEffect getCharacterEffect(int characterId);

    void addEndGameListener(EndGameListener listener);

    void excludeColourFromInfluence(PawnColour chosenColour);

    void deactivatePlayer(String nickname);

    void activatePlayer(String nickname);
}
