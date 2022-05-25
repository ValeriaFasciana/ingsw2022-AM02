package it.polimi.ingsw.network.messages.clienttoserver.events;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.server.ServerMessageVisitor;
import it.polimi.ingsw.network.messages.MessageFromClientToServer;
import it.polimi.ingsw.network.messages.Type;

//message to ask the game mode
public class GameModeRequest extends MessageFromClientToServer {
    private final String gameMode;
    public GameModeRequest(@JsonProperty("username") String username,
                           @JsonProperty("game mode") String gamemode)
    {
        super(username, Type.CLIENT_REQUEST);
        this.gameMode = gamemode;
    }

    public String getGameMode() {
        return gameMode;
    }

    @Override
    public void callVisitor(ServerMessageVisitor visitor) {
        visitor.gameMode(this);
    }
}
