package it.polimi.ingsw.network.messages.servertoclient.events;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.client.ClientMessageVisitor;
import it.polimi.ingsw.network.messages.CharacterRequest;
import it.polimi.ingsw.network.messages.MessageFromServerToClient;
import it.polimi.ingsw.network.messages.Type;
import it.polimi.ingsw.shared.enums.MovementDestination;

public class ExchangeStudentsRequest extends CharacterRequest {
    private int numberOfStudents;
    private MovementDestination from;
    private MovementDestination to;

    @JsonCreator
    public ExchangeStudentsRequest(@JsonProperty("numberOfStudents") int numberOfStudents,
                                   @JsonProperty("from") MovementDestination from,
                                   @JsonProperty("to") MovementDestination to) {
        super();
        this.numberOfStudents = numberOfStudents;
        this.from = from;
        this.to = to;
    }

    @Override
    public void callVisitor(ClientMessageVisitor visitor) {
        visitor.exchangeStudents(this);
    }

    @JsonGetter
    public int getNumberOfStudents() {
        return numberOfStudents;
    }

    @JsonGetter
    public MovementDestination getFrom() {
        return from;
    }

    @JsonGetter
    public MovementDestination getTo() {
        return to;
    }
}
