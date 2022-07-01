package it.polimi.ingsw.server.model.game;

import it.polimi.ingsw.server.controller.listeners.BoardUpdateListener;
import it.polimi.ingsw.server.controller.listeners.EndGameListener;
import it.polimi.ingsw.server.controller.action.Action;
import it.polimi.ingsw.server.model.cards.characters.CharacterEffect;
import it.polimi.ingsw.shared.enums.PawnColour;
import it.polimi.ingsw.shared.enums.Phase;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

public interface GameInterface {
    void useAction(Action action);

    /**
     * Method to add listener to board update event
     * @param listener listener listener to add
     */
    void addBoardUpdateListener(BoardUpdateListener listener);

    /**
     * Method to handle calculation of influence
     * @param isleIndex id of isle to calculate its influence
     * @param excludedColour colour to exclude from calculation
     */
    void calculateInfluence(int isleIndex, Optional<PawnColour> excludedColour);

    Set<Integer> getPlayableAssistants();

    String getCurrentPlayerName();

    void playAssistantCard(int assistantIndex);

    void endCurrentPlayerTurn();

    Phase getRoundPhase();

    Set<Integer> getMotherNatureAvailableIslands();

    Map<PawnColour, Boolean> getPlayerHallAvailability(String currentPlayer);


    Set<Integer> getAvailableClouds();

    GameSettings getSettings();

    void create();

    CharacterEffect getCharacterEffect(int characterId);

    /**
     * Method to add listener to end game event
     * @param listener listener to add
     */
    void addEndGameListener(EndGameListener listener);

    /**
     * Method to exclude colour when calculating influence
     * @param chosenColour colour to exclude
     */
    void excludeColourFromInfluence(PawnColour chosenColour);

    /**
     * Method to handle deactivation of player
     * @param nickname selected player
     */
    void deactivatePlayer(String nickname);

    /**
     * Method to handle activation of player
     * @param nickname selected player
     */
    void activatePlayer(String nickname);
}
