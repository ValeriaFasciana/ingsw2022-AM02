package it.polimi.ingsw.network.messages.servertoclient.events;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.client.ClientMessageVisitor;
import it.polimi.ingsw.network.ReservedRecipients;
import it.polimi.ingsw.network.messages.MessageFromServerToClient;
import it.polimi.ingsw.network.messages.Type;

public class PlayerDisconnectedEvent extends MessageFromServerToClient {
    String disconnectedPlayerName;

    public PlayerDisconnectedEvent(@JsonProperty("disconnectedPlayerName") String disconnectedPlayerName ) {
        super(ReservedRecipients.BROADCAST.toString(), Type.NOTIFY);
        this.disconnectedPlayerName = disconnectedPlayerName;
    }

    public String getDisconnectedPlayerName() {
        return disconnectedPlayerName;
    }

    /**
     * Method to handle the notification on the player disconnection
     * @param visitor message of the disconnection
     */
    @Override
    public void callVisitor(ClientMessageVisitor visitor) {
        visitor.notifyPlayerDisconnection(this);
    }
}
