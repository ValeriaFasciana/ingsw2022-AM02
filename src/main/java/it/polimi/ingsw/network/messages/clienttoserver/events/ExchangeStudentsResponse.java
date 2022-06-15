package it.polimi.ingsw.network.messages.clienttoserver.events;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import it.polimi.ingsw.network.messages.MessageFromClientToServer;
import it.polimi.ingsw.network.messages.Type;
import it.polimi.ingsw.server.ServerMessageVisitor;
import it.polimi.ingsw.shared.enums.MovementDestination;
import it.polimi.ingsw.shared.enums.PawnColour;

import java.util.Map;

@JsonTypeName("ExchangeStudentsResponse")
public class ExchangeStudentsResponse extends MessageFromClientToServer {

    int characterId;
    MovementDestination from;
    MovementDestination to;
    Map<PawnColour, Integer> fromMap;
    Map<PawnColour, Integer> toMap;

    @JsonCreator
    public ExchangeStudentsResponse(@JsonProperty("username") String username,
                                    @JsonProperty("characterId") int characterId,
                                    @JsonProperty("from") MovementDestination from,
                                    @JsonProperty("to") MovementDestination to,
                                    @JsonProperty("fromMap") Map<PawnColour, Integer> fromMap,
                                    @JsonProperty("toMap") Map<PawnColour, Integer> toMap) {
        super(username, Type.CLIENT_RESPONSE);
        this.characterId = characterId;
        this.from = from;
        this.to = to;
        this.fromMap = fromMap;
        this.toMap = toMap;

    }

    @JsonGetter
    public int getCharacterId() {
        return characterId;
    }

    @JsonGetter
    public MovementDestination getFrom() {
        return from;
    }

    @JsonGetter
    public MovementDestination getTo() {
        return to;
    }

    @JsonGetter
    public Map<PawnColour, Integer> getFromMap() {
        return fromMap;
    }

    @JsonGetter
    public Map<PawnColour, Integer> getToMap() {
        return toMap;
    }

    @Override
    public void callVisitor(ServerMessageVisitor visitor) {
        visitor.handleStudentExchange(this);
    }
}
