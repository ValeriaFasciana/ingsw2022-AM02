package it.polimi.ingsw.server.controller.action;

import it.polimi.ingsw.server.model.StudentContainer;
import it.polimi.ingsw.server.model.board.GameBoard;
import it.polimi.ingsw.server.model.cards.characters.CharacterCard;
import it.polimi.ingsw.server.model.player.Player;
import it.polimi.ingsw.shared.enums.PawnColour;

import java.util.Map;
import java.util.Optional;

public interface ActionVisitor {

    /**
     *
     * @param assistantId
     */
    void playAssistantCard(int assistantId);

    /**
     *
     * @param isleIndex
     */
    void moveMotherNature(int isleIndex);

    /**
     *
     * @param isleIndex
     */
    void setBanOnIsland(int isleIndex);

    /**
     *
     * @param cloudIndex
     */
    void emptyCloud(int cloudIndex);

    /**
     *
     * @param isleIndex
     * @param excludedColour
     */
    void calculateInfluence(int isleIndex, Optional<PawnColour> excludedColour);

    /**
     *
     * @param characterId
     */
    void activateCharacter(int characterId);

    /**
     *
     * @return
     */
    Player getCurrentPlayer();

    /**
     *
     */
    void notifyBoardListeners();

    /**
     *
     * @return
     */
    GameBoard getGameBoard();

    /**
     *
     * @param characterId
     * @return
     */
    CharacterCard getCharacter(int characterId);

    /**
     *
     * @param movedStudents
     */
    void addStudentsToCurrentPlayerHall(Map<PawnColour, Integer> movedStudents);

    Map<String, Player> getPlayers();
}
