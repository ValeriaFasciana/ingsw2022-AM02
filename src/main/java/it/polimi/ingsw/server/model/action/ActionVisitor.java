package it.polimi.ingsw.server.model.action;

import it.polimi.ingsw.server.model.StudentContainer;
import it.polimi.ingsw.server.model.board.GameBoard;
import it.polimi.ingsw.server.model.cards.characters.CharacterCard;
import it.polimi.ingsw.server.model.player.Player;
import it.polimi.ingsw.shared.enums.PawnColour;

import java.util.Map;
import java.util.Optional;

public interface ActionVisitor {

    void playAssistantCard(int assistantId);


    void moveMotherNature(int isleIndex);

    void setBanOnIsland(int isleIndex);

    void emptyCloud(int cloudIndex);

    void moveStudentToIsle(PawnColour studentColour, int isleIndex);

    void moveStudentToIsle(PawnColour studentColour, int isleIndex, boolean fromCharacter);

    void calculateInfluence(int isleIndex, Optional<PawnColour> excludedColour);

    void activateCharacter(int characterId);

    Player getCurrentPlayer();

    void notifyBoardListeners();

    GameBoard getGameBoard();

    CharacterCard getCharacter(int characterId);

    void addStudentsToCurrentPlayerHall(Map<PawnColour, Integer> movedStudents);
}
