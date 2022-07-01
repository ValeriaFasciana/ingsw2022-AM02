package it.polimi.ingsw.server.controller.action;

public class PlayAssistantAction implements Action {
    int assistantId;

    /**
     * Action handled by the Game Controller
     * @param assistantId chosen assistant card id
     */
    public PlayAssistantAction(int assistantId) {
        this.assistantId = assistantId;
    }

    /**
     * Method to handle the actions of the visitor
     * @param visitor visitor of the overridden method
     */
    @Override
    public void accept(ActionVisitor visitor) {
        visitor.playAssistantCard(assistantId);
    }
}
