package it.polimi.ingsw.network.messages.clienttoserver.events;

import com.fasterxml.jackson.annotation.*;
import it.polimi.ingsw.network.messages.MessageFromClientToServer;
import it.polimi.ingsw.network.messages.Type;
import it.polimi.ingsw.server.ServerMessageVisitor;

@JsonTypeName("ChooseIslandResponse")
public class ChooseIslandResponse extends MessageFromClientToServer {
    private int isleIndex;
    private boolean setBan;
    private boolean calculateInfluence;

    @JsonCreator
    public ChooseIslandResponse(@JsonProperty("username") String username,
                                @JsonProperty("isleIndex") int isleIndex,
                                @JsonProperty("setBan") boolean setBan,
                                @JsonProperty("calculateInfluence") boolean calculateInfluence) {
        super(username, Type.CLIENT_RESPONSE);
        this.isleIndex = isleIndex;
        this.setBan = setBan;
        this.calculateInfluence = calculateInfluence;
    }

    @Override
    public void callVisitor(ServerMessageVisitor visitor) {
        visitor.handleIsleChoosing(this);
    }

    @JsonGetter
    public int getIsleIndex() {
        return isleIndex;
    }

    @JsonGetter
    public boolean isSetBan() {
        return setBan;
    }

    @JsonGetter
    public boolean isCalculateInfluence() {
        return calculateInfluence;
    }
}
