package it.polimi.ingsw.server.model.action;

import it.polimi.ingsw.shared.enums.MovementDestination;
import it.polimi.ingsw.shared.enums.PawnColour;

import java.util.Map;
import java.util.Optional;

public class MoveStudentsFromCardAction implements Action {
    int characterId;
    MovementDestination destination;
    Map<PawnColour, Integer> movedStudents;
    Optional<Integer> isleIndex;
    public MoveStudentsFromCardAction(int characterId, MovementDestination destination, Map<PawnColour, Integer> movedStudents,Optional<Integer> isleIndex) {
        this.characterId = characterId;
        this.destination = destination;
        this.movedStudents = movedStudents;
        this.isleIndex = isleIndex;
    }

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
