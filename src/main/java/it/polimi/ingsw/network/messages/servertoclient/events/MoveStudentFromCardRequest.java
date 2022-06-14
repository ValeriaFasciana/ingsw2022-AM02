package it.polimi.ingsw.network.messages.servertoclient.events;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import it.polimi.ingsw.client.ClientMessageVisitor;
import it.polimi.ingsw.network.messages.CharacterRequest;
import it.polimi.ingsw.shared.enums.MovementDestination;

@JsonTypeName("MoveStudentFromCardRequest")
public class MoveStudentFromCardRequest extends CharacterRequest {
    private MovementDestination destination;
    private int studentsToMove;
    private boolean canMoveLess;

    @JsonCreator
    public MoveStudentFromCardRequest(@JsonProperty("destination") MovementDestination destination,
                                      @JsonProperty("studentsToMove") int studentsToMove,
                                      @JsonProperty("canMoveLess") boolean canMoveLess) {
        super();
        this.destination = destination;
        this.studentsToMove = studentsToMove;
        this.canMoveLess = canMoveLess;
    }

    @Override
    public void callVisitor(ClientMessageVisitor visitor) {
        visitor.moveStudentsFromCard(this);
    }

    @JsonGetter
    public MovementDestination getDestination() {
        return destination;
    }

    @JsonGetter
    public int getStudentsToMove() {
        return studentsToMove;
    }

    @JsonGetter
    public boolean isCanMoveLess() {
        return canMoveLess;
    }



}
