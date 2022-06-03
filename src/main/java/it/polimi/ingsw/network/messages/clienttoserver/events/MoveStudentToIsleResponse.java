package it.polimi.ingsw.network.messages.clienttoserver.events;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.server.ServerMessageVisitor;
import it.polimi.ingsw.shared.enums.PawnColour;
import it.polimi.ingsw.network.messages.MessageFromClientToServer;
import it.polimi.ingsw.network.messages.Type;

public class MoveStudentToIsleResponse extends MessageFromClientToServer {
    private final PawnColour studentColour;
    private final int isleIndex;

    public MoveStudentToIsleResponse(@JsonProperty("playerNickName") String username, int isleIndex, PawnColour studentColour) {
        super(username, Type.CLIENT_RESPONSE);
        this.studentColour = studentColour;
        this.isleIndex = isleIndex;
    }

    public PawnColour getStudentColour() {
        return studentColour;
    }

    public int getIsleIndex() {
        return isleIndex;
    }

    @Override
    public void callVisitor(ServerMessageVisitor visitor) {
        visitor.moveStudentToIsle(this);
    }
}
