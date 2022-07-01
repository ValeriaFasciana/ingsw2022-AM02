package it.polimi.ingsw.network.messages.clienttoserver.events;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.server.ServerMessageVisitor;
import it.polimi.ingsw.shared.enums.PawnColour;
import it.polimi.ingsw.network.messages.MessageFromClientToServer;
import it.polimi.ingsw.network.messages.Type;

public class MoveStudentToIsleResponse extends MessageFromClientToServer {
    PawnColour studentColour;
    int isleIndex;


    @JsonCreator
    public MoveStudentToIsleResponse(@JsonProperty("username") String username,@JsonProperty("isleIndex")int isleIndex,@JsonProperty("studentColour")PawnColour studentColour) {
        super(username, Type.CLIENT_RESPONSE);
        this.studentColour = studentColour;
        this.isleIndex = isleIndex;
    }

    @JsonGetter
    public PawnColour getStudentColour() {
        return studentColour;
    }

    @JsonGetter
    public int getIsleIndex() {
        return isleIndex;
    }

    /**
     * Method to handle Move Student To Isle response
     * @param visitor server message visitor
     */
    @Override
    public void callVisitor(ServerMessageVisitor visitor) {
        visitor.moveStudentToIsle(this);
    }
}
