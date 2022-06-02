package it.polimi.ingsw.network.messages.servertoclient.events;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.shared.enums.PawnColour;
import it.polimi.ingsw.client.ClientMessageVisitor;
import it.polimi.ingsw.network.messages.MessageFromServerToClient;
import it.polimi.ingsw.network.messages.Type;

import java.util.Map;

public class MoveStudentFromEntranceRequest  extends MessageFromServerToClient {
    private final Map<PawnColour,Boolean>  hallColourAvailability;

    @JsonCreator
    public MoveStudentFromEntranceRequest(@JsonProperty("username")String username,@JsonProperty("type") Type type,@JsonProperty("hallColourAvailability") Map<PawnColour,Boolean>  hallColourAvailability) {
        super(username, type);
        this.hallColourAvailability = hallColourAvailability;
    }

    @Override
    public void callVisitor(ClientMessageVisitor visitor) {
        visitor.askMoveStudentFromEntrance(this);
    }

    @JsonGetter
    public Map<PawnColour, Boolean> getHallColourAvailability() {
        return hallColourAvailability;
    }
}
