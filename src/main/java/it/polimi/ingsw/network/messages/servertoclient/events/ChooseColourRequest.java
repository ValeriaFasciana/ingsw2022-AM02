package it.polimi.ingsw.network.messages.servertoclient.events;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import it.polimi.ingsw.client.ClientMessageVisitor;
import it.polimi.ingsw.network.messages.CharacterRequest;

@JsonTypeName("ChooseColourRequest")
public class ChooseColourRequest extends CharacterRequest {
    private final boolean toExclude;
    private final boolean toDiscard;

    @JsonCreator
    public ChooseColourRequest(@JsonProperty("toExclude") boolean toExclude,
                               @JsonProperty("toDiscard") boolean toDiscard) {
        super();
        this.toDiscard = toDiscard;
        this.toExclude = toExclude;
    }

    @Override
    public void callVisitor(ClientMessageVisitor visitor) {
        visitor.chooseColour(this);
    }

    @JsonGetter
    public boolean isToExclude() {
        return toExclude;
    }

    @JsonGetter
    public boolean isToDiscard() {
        return toDiscard;
    }

}
