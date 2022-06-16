package it.polimi.ingsw.network.messages.clienttoserver.events;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import it.polimi.ingsw.network.messages.MessageFromClientToServer;
import it.polimi.ingsw.network.messages.Type;
import it.polimi.ingsw.server.ServerMessageVisitor;
import it.polimi.ingsw.shared.enums.PawnColour;


@JsonTypeName("ChooseColourResponse")
public class ChooseColourResponse extends MessageFromClientToServer {

    private PawnColour chosenColour;
    private boolean toExclude;
    private int toDiscard;

    @JsonCreator
    public ChooseColourResponse(@JsonProperty("username") String username,
                                @JsonProperty("chosenColour") PawnColour chosenColour,
                                @JsonProperty("toExclude") boolean toExclude,
                                @JsonProperty("toDiscard") int toDiscard) {
        super(username, Type.CLIENT_RESPONSE);
        this.chosenColour = chosenColour;
        this.toDiscard = toDiscard;
        this.toExclude = toExclude;
    }

    @Override
    public void callVisitor(ServerMessageVisitor visitor) {
        visitor.handleColourChoosing(this);
    }

    @JsonGetter
    public PawnColour getChosenColour() {
        return chosenColour;
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
