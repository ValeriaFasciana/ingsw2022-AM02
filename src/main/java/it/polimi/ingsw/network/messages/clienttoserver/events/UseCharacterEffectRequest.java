package it.polimi.ingsw.network.messages.clienttoserver.events;

import com.fasterxml.jackson.annotation.JsonGetter;
import it.polimi.ingsw.network.messages.MessageFromClientToServer;
import it.polimi.ingsw.network.messages.Type;
import it.polimi.ingsw.server.ServerMessageVisitor;

public class UseCharacterEffectRequest extends MessageFromClientToServer {
    private final int characterId;

    public UseCharacterEffectRequest(String username,int characterId) {
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
