package it.polimi.ingsw.network.messages.servertoclient.events;

import it.polimi.ingsw.shared.enums.PawnColour;
import it.polimi.ingsw.client.ClientMessageVisitor;
import it.polimi.ingsw.network.messages.MessageFromServerToClient;
import it.polimi.ingsw.network.messages.Type;

import java.util.Map;

public class MoveStudentFromEntranceRequest  extends MessageFromServerToClient {
    private final Map<PawnColour,Boolean>  hallColourAvailability;

    public MoveStudentFromEntranceRequest(String username, Type type,Map<PawnColour,Boolean>  hallColourAvailability) {
        super(username, type);
        this.hallColourAvailability = hallColourAvailability;
    }

    @Override
    public void callVisitor(ClientMessageVisitor visitor) {

    }
}
