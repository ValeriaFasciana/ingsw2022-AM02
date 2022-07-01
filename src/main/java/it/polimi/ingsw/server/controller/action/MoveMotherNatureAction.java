package it.polimi.ingsw.server.controller.action;

public class MoveMotherNatureAction implements Action{
    int targetIslandIndex;

    /**
     * Action handled by the Game Controller
     * @param targetIslandIndex isle id where to move mother nature to
     */
    public MoveMotherNatureAction(int targetIslandIndex) {
        this.targetIslandIndex = targetIslandIndex;
    }

    /**
     * Method to handle the actions of the visitor
     * @param visitor visitor of the overridden method
     */
    @Override
    public void accept(ActionVisitor visitor) {
        visitor.moveMotherNature(targetIslandIndex);
    }
}
