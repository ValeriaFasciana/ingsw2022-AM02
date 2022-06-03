package it.polimi.ingsw.network.messages.servertoclient.events;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.shared.enums.PawnColour;
import it.polimi.ingsw.client.ClientMessageVisitor;
import it.polimi.ingsw.network.messages.MessageFromServerToClient;
import it.polimi.ingsw.network.messages.Type;

import java.util.Map;

public class MoveStudentFromEntranceRequest  extends MessageFromServerToClient {
    private final Map<PawnColour,Boolean>  hallColourAvailability;

    public MoveStudentFromEntranceRequest(@JsonProperty("username") String username, @JsonProperty("hallColourAvailability") Map<PawnColour,Boolean>  hallColourAvailability) {
        super(username, Type.SERVER_REQUEST);
        this.hallColourAvailability = hallColourAvailability;
    }

    @Override
    public void callVisitor(ClientMessageVisitor visitor) {visitor.moveStudent(this);

    }

    public Map<PawnColour, Boolean> getHallColourAvailability() {
        return hallColourAvailability;
    }
}
