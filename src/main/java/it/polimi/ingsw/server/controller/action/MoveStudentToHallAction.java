package it.polimi.ingsw.server.controller.action;

import it.polimi.ingsw.shared.enums.PawnColour;

import java.util.EnumMap;
import java.util.Map;

public class MoveStudentToHallAction implements Action{
    PawnColour studentColour;

    public MoveStudentToHallAction(PawnColour studentColour) {
        this.studentColour = studentColour;
    }

    @Override
    public void accept(ActionVisitor visitor) {
        Map<PawnColour,Integer> studentMap = new EnumMap<>(PawnColour.class);
        studentMap.put(studentColour,1);
        visitor.addStudentsToCurrentPlayerHall(studentMap);
        visitor.getCurrentPlayer().removeStudentsFromEntrance(studentMap);
        visitor.notifyBoardListeners();
    }
}
