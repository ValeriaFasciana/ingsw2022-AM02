package it.polimi.ingsw.server.model.action;

public abstract class Action {
    ActionType type;
    ActionSubject subject;

    public Action(ActionType type, ActionSubject subject) {
        this.type = type;
        this.subject = subject;
    }
}
