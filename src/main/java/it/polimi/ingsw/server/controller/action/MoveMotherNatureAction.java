package it.polimi.ingsw.server.controller.action;

public class MoveMotherNatureAction implements Action{
    int targetIslandIndex;

    public MoveMotherNatureAction(int targetIslandIndex) {
        this.targetIslandIndex = targetIslandIndex;
    }

    @Override
    public void accept(ActionVisitor visitor) {
        visitor.moveMotherNature(targetIslandIndex);
    }
}
