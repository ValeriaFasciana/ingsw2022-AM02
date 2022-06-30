package it.polimi.ingsw.server.controller.action;

public class ChooseCloudAction implements Action{
    int cloudIndex;

    /**
     *
     * @param cloudIndex
     */
    public ChooseCloudAction(int cloudIndex) {
        this.cloudIndex = cloudIndex;
    }

    /**
     *
     * @param visitor
     */
    @Override
    public void accept(ActionVisitor visitor) {
        visitor.emptyCloud(cloudIndex);
    }
}
