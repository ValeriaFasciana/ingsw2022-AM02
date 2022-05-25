package it.polimi.ingsw.server.model.action;

public class SetBanOnIsleAction implements Action{
    int isleIndex;

    @Override
    public void accept(ActionVisitor visitor) {
        visitor.setBanOnIsland(isleIndex);
    }
}
