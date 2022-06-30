package it.polimi.ingsw.server.controller.action;

public class MoveMotherNatureAction implements Action{
    int targetIslandIndex;

    /**
     *
     * @param targetIslandIndex
     */
    public MoveMotherNatureAction(int targetIslandIndex) {
        this.targetIslandIndex = targetIslandIndex;
    }

    /**
     *
     * @param visitor
     */
    @Override
    public void accept(ActionVisitor visitor) {
        visitor.moveMotherNature(targetIslandIndex);
    }
}
