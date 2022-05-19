package it.polimi.ingsw.shared.messages.fromServerToClient;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.shared.ClientMessageManager;
import it.polimi.ingsw.shared.messages.MessageFromServerToClient;
import it.polimi.ingsw.shared.messages.Type;

import java.util.List;
import java.util.Map;

public class LoginResponse extends MessageFromServerToClient {
        private final Map<String, List<String>> lobbies;

        @JsonCreator
        public LoginResponse(@JsonProperty("type") Type type,
                             @JsonProperty("username") String username,
                             @JsonProperty("lobbies") Map<String, List<String>> lobbies) {
            super(username, type);
            this.lobbies = lobbies;
        }

        public Map<String, List<String>> getLobbies() {
            return lobbies;
        }

        @Override
        public void callVisitor(ClientMessageManager visitor) {
            visitor.Logged(this);
        }

}
