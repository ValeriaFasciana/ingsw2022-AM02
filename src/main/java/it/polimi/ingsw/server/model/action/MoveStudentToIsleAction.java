package it.polimi.ingsw.server.model.action;

import it.polimi.ingsw.shared.enums.PawnColour;

public class MoveStudentToIsleAction implements Action {
    PawnColour studentColour;
    int isleIndex;
    public MoveStudentToIsleAction(PawnColour studentColour,int isleIndex) {
        this.studentColour = studentColour;
        this.isleIndex = isleIndex;
    }

    @Override
    public void accept(ActionVisitor visitor) {
        visitor.moveStudentToIsle(studentColour, isleIndex);
    }
}
