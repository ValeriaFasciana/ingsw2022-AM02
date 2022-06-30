package it.polimi.ingsw.server.controller.action;

public class PlayAssistantAction implements Action {
    int assistantId;

    public PlayAssistantAction(int assistantId) {
        this.assistantId = assistantId;
    }

    /**
     *
     * @param visitor
     */
    @Override
    public void accept(ActionVisitor visitor) {
        visitor.playAssistantCard(assistantId);
    }
}
