package it.polimi.ingsw.network.messages.clienttoserver.events;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.network.messages.MessageFromClientToServer;
import it.polimi.ingsw.network.messages.Type;
import it.polimi.ingsw.server.ServerMessageVisitor;

public class LobbyInfoResponse extends MessageFromClientToServer {
    String playerName;
    int numberOfPlayers;
    boolean expertVariant;

    public LobbyInfoResponse(@JsonProperty("username") String username,@JsonProperty("playerName")String playerName,@JsonProperty("numberOfPlayers")int numberOfPlayers, @JsonProperty("expertVariant")boolean expertVariant) {
        super(username, Type.CLIENT_REQUEST);
        this.playerName = playerName;
        this.numberOfPlayers = numberOfPlayers;
        this.expertVariant = expertVariant;

    }

    public String getPlayerName() {
        return playerName;
    }

    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public boolean isExpertVariant() {
        return expertVariant;
    }

    @Override
    public void callVisitor(ServerMessageVisitor visitor) {
        visitor.setLobbyInfo(this);
    }
}
