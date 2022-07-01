package it.polimi.ingsw.server.controller.action;

public class ChooseCloudAction implements Action{
    int cloudIndex;

    /**
     * Action handled by the Game Controller
     * @param cloudIndex chosen cloud id
     */
    public ChooseCloudAction(int cloudIndex) {
        this.cloudIndex = cloudIndex;
    }

    /**
     * Method to handle the actions of the visitor
     * @param visitor visitor of the overridden method
     */
    @Override
    public void accept(ActionVisitor visitor) {
        visitor.emptyCloud(cloudIndex);
    }
}
