package it.polimi.ingsw.server.model.action;

public class MoveMotherNatureAction implements Action{
    int targetIslandIndex;

    @Override
    public void accept(ActionVisitor visitor) {
        visitor.moveMotherNature(targetIslandIndex);
    }
}
