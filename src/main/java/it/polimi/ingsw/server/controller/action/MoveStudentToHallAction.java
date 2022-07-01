package it.polimi.ingsw.server.controller.action;

import it.polimi.ingsw.shared.enums.PawnColour;

import java.util.EnumMap;
import java.util.Map;

public class MoveStudentToHallAction implements Action{
    PawnColour studentColour;

    /**
     * Action handled by the Game Controller
     * @param studentColour colour of the student to move in the hall
     */
    public MoveStudentToHallAction(PawnColour studentColour) {
        this.studentColour = studentColour;
    }

    /**
     * Method to handle the actions of the visitor
     * @param visitor visitor of the overridden method
     */
    @Override
    public void accept(ActionVisitor visitor) {
        Map<PawnColour,Integer> studentMap = new EnumMap<>(PawnColour.class);
        studentMap.put(studentColour,1);
        visitor.addStudentsToCurrentPlayerHall(studentMap);
        visitor.getCurrentPlayer().removeStudentsFromEntrance(studentMap);
        visitor.notifyBoardListeners();
    }
}
