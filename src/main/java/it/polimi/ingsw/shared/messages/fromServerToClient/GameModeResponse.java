package it.polimi.ingsw.shared.messages.fromServerToClient;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.shared.ClientMessageManager;
import it.polimi.ingsw.shared.messages.MessageFromServerToClient;
import it.polimi.ingsw.shared.messages.Type;

public class GameModeResponse extends MessageFromServerToClient {
    private boolean gamemode;

    @JsonCreator
    public GameModeResponse(@JsonProperty("username") String username, @JsonProperty("type") Type type,
                            @JsonProperty("gamemode") boolean gamemode) {
        super(username, type);
        this.gamemode = gamemode;
    }

    @JsonGetter
    public boolean getGameMode() {return gamemode;}

    @Override
    public void callVisitor(ClientMessageManager visitor) {
        visitor.GameMode(this);
    }

}
