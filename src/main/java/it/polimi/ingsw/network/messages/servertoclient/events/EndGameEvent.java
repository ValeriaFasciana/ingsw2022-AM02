package it.polimi.ingsw.network.messages.servertoclient.events;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.client.ClientMessageVisitor;
import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.messages.MessageFromServerToClient;
import it.polimi.ingsw.network.messages.Type;

public class EndGameEvent extends MessageFromServerToClient {
    String causingUser;
    String cause;

    @JsonCreator
    public EndGameEvent(@JsonProperty("username") String username,
                        @JsonProperty("winnerPlayer") String causingUser,
                        @JsonProperty("cause")String cause) {
        super(username,Type.NOTIFY);
        this.causingUser = causingUser;
        this.cause = cause;
    }

    @Override
    public void callVisitor(ClientMessageVisitor visitor) {
        visitor.endGame(this);
    }

    @JsonGetter
    public String getWinnerPlayer() {
        return causingUser;
    }
}
