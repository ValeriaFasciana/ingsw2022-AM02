package it.polimi.ingsw.server.controller.action;

public class SetBanOnIsleAction implements Action{
    int isleIndex;

    /**
     * Action handled by the Game Controller
     * @param isleIndex id of the isle where to put the ban on
     */
    public SetBanOnIsleAction(int isleIndex) {
        this.isleIndex = isleIndex;
    }

    /**
     * Method to handle the actions of the visitor
     * @param visitor visitor of the overridden method
     */
    @Override
    public void accept(ActionVisitor visitor) {
        visitor.setBanOnIsland(isleIndex);
    }
}
