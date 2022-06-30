package it.polimi.ingsw.server.controller.action;

public interface Action {
    void accept(ActionVisitor visitor);
}
