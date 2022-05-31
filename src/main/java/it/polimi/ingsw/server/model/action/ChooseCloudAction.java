package it.polimi.ingsw.server.model.action;

public class ChooseCloudAction implements Action{
    int cloudIndex;

    public ChooseCloudAction(int cloudIndex) {
        this.cloudIndex = cloudIndex;
    }

    @Override
    public void accept(ActionVisitor visitor) {
        visitor.emptyCloud(cloudIndex);
    }
}
