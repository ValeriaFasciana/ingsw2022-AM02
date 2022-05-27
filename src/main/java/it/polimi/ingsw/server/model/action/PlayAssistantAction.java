package it.polimi.ingsw.server.model.action;

import it.polimi.ingsw.server.model.action.Action;

public class PlayAssistantAction implements Action {
    int assistantId;

    public PlayAssistantAction(int assistantId) {
        this.assistantId = assistantId;
    }

    @Override
    public void accept(ActionVisitor visitor) {
        visitor.playAssistantCard(assistantId);
    }
}
