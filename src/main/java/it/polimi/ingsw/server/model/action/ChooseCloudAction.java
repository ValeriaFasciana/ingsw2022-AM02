package it.polimi.ingsw.server.model.action;

public class ChooseCloudAction implements Action{
    int cloudIndex;
    @Override
    public void accept(ActionVisitor visitor) {
        visitor.emptyCloud(cloudIndex);
    }
}
