package it.polimi.ingsw.server.controller.action;

public interface Action {
    /**
     *
     * @param visitor
     */
    void accept(ActionVisitor visitor);
}
