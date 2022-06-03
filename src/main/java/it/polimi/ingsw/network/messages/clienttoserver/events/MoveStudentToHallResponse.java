package it.polimi.ingsw.network.messages.clienttoserver.events;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.server.ServerMessageVisitor;
import it.polimi.ingsw.shared.enums.PawnColour;
import it.polimi.ingsw.network.messages.MessageFromClientToServer;
import it.polimi.ingsw.network.messages.Type;

public class MoveStudentToHallResponse extends MessageFromClientToServer {
    private final PawnColour studentColour;

    public MoveStudentToHallResponse(@JsonProperty("playerNickName") String username, @JsonProperty("studentColour") PawnColour studentColour) {
        super(username,Type.CLIENT_RESPONSE);
        this.studentColour = studentColour;
    }

    @Override
    public void callVisitor(ServerMessageVisitor visitor) {
        visitor.moveStudentToHall(this);

    }

    public PawnColour getStudentColour() {
        return studentColour;
    }
}
