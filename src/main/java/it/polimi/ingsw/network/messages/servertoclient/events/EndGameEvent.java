package it.polimi.ingsw.network.messages.servertoclient.events;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import it.polimi.ingsw.client.ClientMessageVisitor;
import it.polimi.ingsw.network.ReservedRecipients;
import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.messages.MessageFromServerToClient;
import it.polimi.ingsw.network.messages.Type;

@JsonTypeName("EndGameEvent")
public class EndGameEvent extends MessageFromServerToClient {
    String causingUser;
    String cause;
    boolean isGameWon;

    @JsonCreator
    public EndGameEvent(@JsonProperty("winnerPlayer") String causingUser,
                        @JsonProperty("cause")String cause,
                        @JsonProperty("won")boolean isGameWon) {
        super(ReservedRecipients.BROADCAST.toString(),Type.NOTIFY);
        this.causingUser = causingUser;
        this.cause = cause;
        this.isGameWon = isGameWon;
    }

    @Override
    public void callVisitor(ClientMessageVisitor visitor) {
        visitor.endGame(this);
    }

    @JsonGetter
    public String getCausingUser() {
        return causingUser;
    }

    @JsonGetter
    public String getCause() {
        return cause;
    }

    public boolean isGameWon() {
        return isGameWon;
    }
}
