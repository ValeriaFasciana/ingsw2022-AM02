package it.polimi.ingsw.server.model.action;

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
