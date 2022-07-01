package it.polimi.ingsw.server.controller.action;

import it.polimi.ingsw.shared.enums.MovementDestination;
import it.polimi.ingsw.shared.enums.PawnColour;

import java.util.Map;
import java.util.Optional;

public class MoveStudentsFromCardAction implements Action {
    int characterId;
    MovementDestination destination;
    Map<PawnColour, Integer> movedStudents;
    Optional<Integer> isleIndex;

    /**
     * Action handled by the Game Controller
     * @param characterId chosen character card id
     * @param destination where to put the students
     * @param movedStudents map on the students to move
     * @param isleIndex if destination is Isle, move students on the isle with this index
     */
    public MoveStudentsFromCardAction(int characterId, MovementDestination destination, Map<PawnColour, Integer> movedStudents,Optional<Integer> isleIndex) {
        this.characterId = characterId;
        this.destination = destination;
        this.movedStudents = movedStudents;
        this.isleIndex = isleIndex;
    }

    /**
     * Method to handle the actions of the visitor
     * @param game visitor of the overridden method
     */
    @Override
    public void accept(ActionVisitor game) {
        switch(destination){
            case HALL -> game.addStudentsToCurrentPlayerHall(movedStudents);

            case ISLE -> game.getGameBoard().getIsleCircle().get(isleIndex.get()).addStudents(movedStudents);
        }
        game.getCharacter(characterId).removeStudents(movedStudents);
        game.notifyBoardListeners();
    }
}
