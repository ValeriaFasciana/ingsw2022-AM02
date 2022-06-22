package it.polimi.ingsw.network.messages.clienttoserver.events;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.network.messages.MessageFromClientToServer;
import it.polimi.ingsw.network.messages.Type;
import it.polimi.ingsw.server.ServerMessageVisitor;

public class CreateLobbyResponse extends MessageFromClientToServer {
    private String playerName;
    private int numberOfPlayers;
    private boolean expertVariant;


    @JsonCreator
    public CreateLobbyResponse(@JsonProperty("username")String username,
                               @JsonProperty("playerName")String playerName,
                               @JsonProperty("numberOfPlayers")int numberOfPlayers,
                               @JsonProperty("expertVariant")boolean expertVariant) {
        super(username, Type.CLIENT_RESPONSE);
        this.numberOfPlayers = numberOfPlayers;
        this.expertVariant = expertVariant;
        this.playerName = playerName;
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
        visitor.createLobby(this);
    }

}
