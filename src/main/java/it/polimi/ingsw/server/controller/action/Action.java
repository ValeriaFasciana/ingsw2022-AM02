package it.polimi.ingsw.server.controller.action;

public interface Action {

    /**
     *Method to handle the actions of the visitor
     * @param visitor visitor of the overridden method
     */
    void accept(ActionVisitor visitor);
}
