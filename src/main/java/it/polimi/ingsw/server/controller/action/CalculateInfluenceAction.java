package it.polimi.ingsw.server.controller.action;

public class CalculateInfluenceAction implements Action {
    int isleIndex;

    /**
     * Action handled by the Game Controller
     * @param isleIndex chosen isle id to calculate the influence
     */
    public CalculateInfluenceAction(int isleIndex) {
        this.isleIndex = isleIndex;
    }

    /**
     * Method to handle the actions of the visitor
     * @param visitor visitor of the overridden method
     */
    @Override
    public void accept(ActionVisitor visitor) {
        visitor.calculateInfluence(isleIndex, java.util.Optional.empty());
        visitor.notifyBoardListeners();
    }
}
