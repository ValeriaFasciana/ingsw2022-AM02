package it.polimi.ingsw.server.model.action;

public class MovementAction extends Action{
    enum MovementStart{
        ENTRANCE,
        ISLE,
        CHARACTER
    }
    enum MovementEnd{
        HALL,
        ISLE,
    }

    MovementStart from;
    MovementEnd[] to;
    int quantity;

    public MovementAction(ActionSubject subject,int quantity,MovementStart from, MovementEnd[] to) {
        super(ActionType.MOVE,subject);
        this.from = from;
        this.to = to;
    }


}
