package it.polimi.ingsw.server.model.action;

public class ChooseAction extends Action{
    boolean isForAllPlayers;

    public ChooseAction(ActionSubject subject) {
        this(subject,false);
    }
    public ChooseAction(ActionSubject subject,boolean isForAllPlayers) {
        super(ActionType.CHOOSE, subject);
        this.isForAllPlayers = isForAllPlayers;
    }
}
