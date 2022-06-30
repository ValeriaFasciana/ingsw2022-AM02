package it.polimi.ingsw.server.controller.action;

public class CalculateInfluenceAction implements Action {
    int isleIndex;

    public CalculateInfluenceAction(int isleIndex) {
        this.isleIndex = isleIndex;
    }

    @Override
    public void accept(ActionVisitor visitor) {
        visitor.calculateInfluence(isleIndex, java.util.Optional.empty());
    }
}
