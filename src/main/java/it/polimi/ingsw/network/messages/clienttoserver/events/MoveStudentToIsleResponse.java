package it.polimi.ingsw.network.messages.clienttoserver.events;

import it.polimi.ingsw.server.ServerMessageVisitor;
import it.polimi.ingsw.shared.enums.PawnColour;
import it.polimi.ingsw.network.messages.MessageFromClientToServer;
import it.polimi.ingsw.network.messages.Type;

public class MoveStudentToIsleResponse extends MessageFromClientToServer {
    private final PawnColour studentColour;
    private final int isleIndex;

    public MoveStudentToIsleResponse(String username, Type type,int isleIndex,PawnColour studentColour) {
        super(username, type);
        this.studentColour = studentColour;
        this.isleIndex = isleIndex;
    }

    @Override
    public void callVisitor(ServerMessageVisitor visitor) {

    }
}
