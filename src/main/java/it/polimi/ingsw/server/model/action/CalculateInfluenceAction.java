package it.polimi.ingsw.server.model.action;

import it.polimi.ingsw.server.model.action.Action;
import it.polimi.ingsw.server.model.action.ActionVisitor;

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
