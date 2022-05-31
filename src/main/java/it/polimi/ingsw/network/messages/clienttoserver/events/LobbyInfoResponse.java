package it.polimi.ingsw.network.messages.clienttoserver.events;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.network.messages.MessageFromClientToServer;
import it.polimi.ingsw.network.messages.Type;
import it.polimi.ingsw.server.ServerMessageVisitor;

public class LobbyInfoResponse extends MessageFromClientToServer {
    String playerName;
    int numberOfPlayers;
    boolean expertVariant;

    @JsonCreator
    public LobbyInfoResponse(@JsonProperty("username") String username,@JsonProperty("playerName")String playerName,@JsonProperty("numberOfPlayers")int numberOfPlayers, @JsonProperty("expertVariant")boolean expertVariant) {
        super(username, Type.CLIENT_REQUEST);
        this.playerName = playerName;
        this.numberOfPlayers = numberOfPlayers;
        this.expertVariant = expertVariant;

    }

    @JsonGetter
    public String getPlayerName() {
        return playerName;
    }

    @JsonGetter
    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    @JsonGetter
    public boolean getExpertVariant() {
        return expertVariant;
    }

    @Override
    public void callVisitor(ServerMessageVisitor visitor) {
        visitor.setLobbyInfo(this);
    }
}
