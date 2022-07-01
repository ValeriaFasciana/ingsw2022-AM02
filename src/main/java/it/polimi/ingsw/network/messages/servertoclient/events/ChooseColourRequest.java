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
    private final int toDiscard;

    @JsonCreator
    public ChooseColourRequest(@JsonProperty("toExclude") boolean toExclude,
                               @JsonProperty("toDiscard") int toDiscard) {
        super();
        this.toDiscard = toDiscard;
        this.toExclude = toExclude;
    }

    /**
     * Method to handle the choose colour request
     * @param visitor info of the chosen colour
     */
    @Override
    public void callVisitor(ClientMessageVisitor visitor) {
        visitor.chooseColour(this);
    }

    @JsonGetter
    public boolean isToExclude() {
        return toExclude;
    }

    @JsonGetter
    public int getToDiscard() {
        return toDiscard;
    }

}
