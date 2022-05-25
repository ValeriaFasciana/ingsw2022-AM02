package it.polimi.ingsw.network.messages.clienttoserver.events;

import it.polimi.ingsw.server.ServerMessageVisitor;
import it.polimi.ingsw.shared.enums.PawnColour;
import it.polimi.ingsw.network.messages.MessageFromClientToServer;
import it.polimi.ingsw.network.messages.Type;

public class MoveStudentToHallResponse extends MessageFromClientToServer {
    private final PawnColour studentColour;

    public MoveStudentToHallResponse(String username, Type type, PawnColour studentColour) {
        super(username, type);
        this.studentColour = studentColour;
    }

    @Override
    public void callVisitor(ServerMessageVisitor visitor) {

    }
}
