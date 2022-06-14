package it.polimi.ingsw.network.messages.clienttoserver.events;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import it.polimi.ingsw.network.messages.MessageFromClientToServer;
import it.polimi.ingsw.network.messages.Type;
import it.polimi.ingsw.server.ServerMessageVisitor;
import it.polimi.ingsw.shared.enums.MovementDestination;
import it.polimi.ingsw.shared.enums.PawnColour;

import java.util.Map;
import java.util.Optional;

@JsonTypeName("MoveStudentFromCardResponse")
public class MoveStudentFromCardResponse extends MessageFromClientToServer {

    private int characterId;
    private MovementDestination destination;
    private int isleIndex;
    private Map<PawnColour,Integer> movedStudents;

    @JsonCreator
    public MoveStudentFromCardResponse(@JsonProperty("username") String username,
                                       @JsonProperty("characterId") int characterId,
                                       @JsonProperty("isleIndex") int isleIndex,
                                       @JsonProperty("destination") MovementDestination destination,
                                       @JsonProperty("movedStudents") Map<PawnColour,Integer> movedStudents) {
        super(username, Type.CLIENT_RESPONSE);
        this.characterId = characterId;
        this.isleIndex = isleIndex;
        this.destination = destination;
        this.movedStudents = movedStudents;
    }

    @Override
    public void callVisitor(ServerMessageVisitor visitor) {
        visitor.moveStudentsFromCard(this);
    }

    public int getCharacterId() {
        return characterId;
    }

    public MovementDestination getDestination() {
        return destination;
    }

    public int getIsleIndex() {
        return isleIndex;
    }

    public Map<PawnColour, Integer> getMovedStudents() {
        return movedStudents;
    }
}
