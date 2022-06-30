package it.polimi.ingsw.server.controller.action;

public class CalculateInfluenceAction implements Action {
    int isleIndex;

    /**
     *
     * @param isleIndex
     */
    public CalculateInfluenceAction(int isleIndex) {
        this.isleIndex = isleIndex;
    }

    /**
     *
     * @param visitor
     */
    @Override
    public void accept(ActionVisitor visitor) {
        visitor.calculateInfluence(isleIndex, java.util.Optional.empty());
    }
}
