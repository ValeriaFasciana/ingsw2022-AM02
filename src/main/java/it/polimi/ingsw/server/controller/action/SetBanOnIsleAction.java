package it.polimi.ingsw.server.controller.action;

public class SetBanOnIsleAction implements Action{
    int isleIndex;

    public SetBanOnIsleAction(int isleIndex) {
        this.isleIndex = isleIndex;
    }

    /**
     *
     * @param visitor
     */
    @Override
    public void accept(ActionVisitor visitor) {
        visitor.setBanOnIsland(isleIndex);
    }
}
