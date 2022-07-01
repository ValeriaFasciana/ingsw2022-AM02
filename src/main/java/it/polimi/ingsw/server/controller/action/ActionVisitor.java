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
     * Visitor for {@link PlayAssistantAction }
     * @param assistantId chosen assistant card id
     */
    void playAssistantCard(int assistantId);

    /**
     * Visitor for{@link MoveMotherNatureAction}
     * @param isleIndex chosen isle id
     */
    void moveMotherNature(int isleIndex);

    /**
     * Visitor for {@link SetBanOnIsleAction}
     * @param isleIndex chosen isle id
     */
    void setBanOnIsland(int isleIndex);

    /**
     *Visitor for {@link ChooseCloudAction }
     * @param cloudIndex chosen cloud id
     */
    void emptyCloud(int cloudIndex);

    /**
     *Visitor for {@link CalculateInfluenceAction}
     * @param isleIndex chosen isle id
     * @param excludedColour chosen colour to exclude
     */
    void calculateInfluence(int isleIndex, Optional<PawnColour> excludedColour);

    /**
     *Visitor for {@link PlayCharacterAction}
     * @param characterId chosen character card id
     */
    void activateCharacter(int characterId);

    /**
     * Method to return current player
     * @return current player
     */
    Player getCurrentPlayer();

    /**
     * Method to notify via broadcast
     */
    void notifyBoardListeners();

    /**
     * Method to get Game Board
     * @return gamemboard
     */
    GameBoard getGameBoard();

    /**
     *Method to get chosen character card
     * @param characterId chosen card id
     * @return character card
     */
    CharacterCard getCharacter(int characterId);

    /**
     *Method to add students to current player's hall
     * @param movedStudents map of the students to move
     */
    void addStudentsToCurrentPlayerHall(Map<PawnColour, Integer> movedStudents);


    Map<String, Player> getPlayers();
}
