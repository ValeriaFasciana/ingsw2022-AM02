package it.polimi.ingsw.network.messages.servertoclient.events;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.client.ClientMessageVisitor;
import it.polimi.ingsw.network.messages.MessageFromServerToClient;
import it.polimi.ingsw.network.messages.Type;

import java.util.List;
import java.util.Map;

public class GameCreatedEvent extends MessageFromServerToClient {

    private final Map<String, List<String>> lobbies;

    @JsonCreator
    public GameCreatedEvent(@JsonProperty("username") String username, @JsonProperty("type") Type type,
                            @JsonProperty("lobbies") Map<String, List<String>> lobbies) {
        super(username, type);
        this.lobbies = lobbies;
    }

    public Map<String, List<String>> getLobbies() {
        return lobbies;
    }
    @Override
    public void callVisitor(ClientMessageVisitor visitor) {
        visitor.GameCreated(this);
    }
}
