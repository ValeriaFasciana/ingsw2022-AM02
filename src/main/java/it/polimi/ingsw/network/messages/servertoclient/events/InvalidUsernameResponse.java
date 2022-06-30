package it.polimi.ingsw.network.messages.servertoclient.events;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.client.ClientMessageVisitor;
import it.polimi.ingsw.network.messages.MessageFromServerToClient;

import static it.polimi.ingsw.network.messages.Type.INVALID_NAME;

public class InvalidUsernameResponse extends MessageFromServerToClient {
    /**
     * Message constructor
     *
     * @param username the sender's username
     */
    private final boolean isRejoin;
    @JsonCreator
    public InvalidUsernameResponse(@JsonProperty("username") String username,
                                   @JsonProperty("rejoin")boolean isRejoin) {
        super(username, INVALID_NAME);
        this.isRejoin = isRejoin;
    }

    /**
     * Method to handle the ask nickname request when the first choice is not valid
     * @param visitor invalid username response message
     */
    @Override
    public void callVisitor(ClientMessageVisitor visitor) {
        visitor.askNickname(this);
    }

    @JsonGetter
    public boolean isRejoin() {
        return isRejoin;
    }
}
