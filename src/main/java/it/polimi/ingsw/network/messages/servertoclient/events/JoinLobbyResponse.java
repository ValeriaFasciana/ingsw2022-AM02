package it.polimi.ingsw.network.messages.servertoclient.events;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.client.ClientMessageVisitor;
import it.polimi.ingsw.network.messages.MessageFromServerToClient;
import it.polimi.ingsw.network.messages.Type;

import java.util.List;
import java.util.Map;

public class JoinLobbyResponse extends MessageFromServerToClient {
    private final Map<String, List<String>> availableLobbies;
    private final int lobbySize;
    private final String joinedUser;

    @JsonCreator
    public JoinLobbyResponse(@JsonProperty("username") String username, @JsonProperty("type") Type type,
                             @JsonProperty("availableLobbies") Map<String, List<String>> availableLobbies,
                             @JsonProperty("lobbySize") int lobbySize,
                             @JsonProperty("joinedUser") String joinedUser) {
        super(username, type);
        this.availableLobbies = availableLobbies;
        this.lobbySize = lobbySize;
        this.joinedUser = joinedUser;
    }

    @JsonGetter
    public String getJoinedUser() {
        return joinedUser;
    }

    @JsonGetter
    public int getLobbySize() {
        return lobbySize;
    }

    @Override
    public void callVisitor(ClientMessageVisitor visitor) {
        visitor.JoinedLobby(this);
    }
}
