package it.polimi.ingsw.server.controller.action;

import it.polimi.ingsw.shared.enums.PawnColour;

import java.util.EnumMap;
import java.util.Map;

public class MoveStudentToIsleAction implements Action {
    PawnColour studentColour;
    int isleIndex;

    /**
     * Action handled by the Game Controller
     * @param studentColour colour of the student to move
     * @param isleIndex id of the isle where to put the student
     */
    public MoveStudentToIsleAction(PawnColour studentColour,int isleIndex) {
        this.studentColour = studentColour;
        this.isleIndex = isleIndex;
    }

    /**
     * Method to handle the actions of the visitor
     * @param visitor visitor of the overridden method
     */
    @Override
    public void accept(ActionVisitor visitor) {
        visitor.getCurrentPlayer().removeStudentsFromEntrance(new EnumMap<>(Map.of(studentColour, 1)));
        visitor.getGameBoard().addStudentToIsle(isleIndex,new EnumMap<>(Map.of(studentColour, 1)));
        visitor.notifyBoardListeners();
    }
}
