package it.polimi.ingsw.server.controller.action;

import it.polimi.ingsw.shared.enums.PawnColour;

import java.util.EnumMap;
import java.util.Map;

public class MoveStudentToIsleAction implements Action {
    PawnColour studentColour;
    int isleIndex;
    public MoveStudentToIsleAction(PawnColour studentColour,int isleIndex) {
        this.studentColour = studentColour;
        this.isleIndex = isleIndex;
    }

    @Override
    public void accept(ActionVisitor visitor) {
        visitor.getCurrentPlayer().removeStudentsFromEntrance(new EnumMap<>(Map.of(studentColour, 1)));
        visitor.getGameBoard().addStudentToIsle(isleIndex,new EnumMap<>(Map.of(studentColour, 1)));
        visitor.notifyBoardListeners();
    }
}
