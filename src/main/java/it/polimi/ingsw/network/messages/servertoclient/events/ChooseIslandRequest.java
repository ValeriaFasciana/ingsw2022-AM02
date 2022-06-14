package it.polimi.ingsw.network.messages.servertoclient.events;

import com.fasterxml.jackson.annotation.*;
import it.polimi.ingsw.client.ClientMessageVisitor;
import it.polimi.ingsw.network.messages.CharacterRequest;

@JsonTypeName("ChooseIslandRequest")
public class ChooseIslandRequest extends CharacterRequest {
    private boolean setBan;
    private boolean calculateInfluence;

    @JsonCreator
    public ChooseIslandRequest(@JsonProperty("setBan") boolean setBan,
                               @JsonProperty("calculateInfluence") boolean calculateInfluence
                               ) {
        super();
        this.setBan = setBan;
        this.calculateInfluence = calculateInfluence;
    }

    @Override
    public void callVisitor(ClientMessageVisitor visitor) {
        visitor.chooseIsland(this);
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
