package it.polimi.ingsw.server.model.action;

public interface Action {
    void accept(ActionVisitor visitor);
}
