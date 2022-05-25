package it.polimi.ingsw.server.model.action;

import it.polimi.ingsw.shared.enums.PawnColour;

public class MoveStudentToHallAction implements Action{
    PawnColour studentColour;
    @Override
    public void accept(ActionVisitor visitor) {
        visitor.moveStudentToCurrentPlayerHall(studentColour);
    }
}
