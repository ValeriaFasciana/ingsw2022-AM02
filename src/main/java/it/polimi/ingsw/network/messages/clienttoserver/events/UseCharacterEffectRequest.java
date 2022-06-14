package it.polimi.ingsw.network.messages.clienttoserver.events;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.network.messages.MessageFromClientToServer;
import it.polimi.ingsw.network.messages.Type;
import it.polimi.ingsw.server.ServerMessageVisitor;

public class UseCharacterEffectRequest extends MessageFromClientToServer {
    private final int characterId;

    @JsonCreator
    public UseCharacterEffectRequest(@JsonProperty("username") String username,
                                     @JsonProperty("characterId") int characterId) {
        super(username, Type.CLIENT_REQUEST);
        this.characterId = characterId;
    }

    @Override
    public void callVisitor(ServerMessageVisitor visitor) {
        visitor.useCharacterEffect(this);
    }

    @JsonGetter
    public int getCharacterId() {
        return characterId;
    }
}
