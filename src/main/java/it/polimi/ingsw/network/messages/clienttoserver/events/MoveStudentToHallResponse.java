package it.polimi.ingsw.network.messages.clienttoserver.events;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.server.ServerMessageVisitor;
import it.polimi.ingsw.shared.enums.PawnColour;
import it.polimi.ingsw.network.messages.MessageFromClientToServer;
import it.polimi.ingsw.network.messages.Type;

public class MoveStudentToHallResponse extends MessageFromClientToServer {
    PawnColour studentColour;
    @JsonCreator
    public MoveStudentToHallResponse(@JsonProperty("username") String username,@JsonProperty("studentColour") PawnColour studentColour) {
        super(username, Type.CLIENT_RESPONSE);
        this.studentColour = studentColour;
    }

    /**
     * Method to handle Move Student To Hall response
     * @param visitor server message visitor
     */
    @Override
    public void callVisitor(ServerMessageVisitor visitor) {
        visitor.moveStudentToHall(this);

    }
    @JsonGetter
    public PawnColour getStudentColour() {
        return studentColour;
    }
}
