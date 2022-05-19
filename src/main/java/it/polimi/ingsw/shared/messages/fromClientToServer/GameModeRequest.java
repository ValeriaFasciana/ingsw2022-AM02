package it.polimi.ingsw.shared.messages.fromClientToServer;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.shared.ServerMessageManager;
import it.polimi.ingsw.shared.messages.MessageFromClientToServer;
import it.polimi.ingsw.shared.messages.Type;

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
    public void callVisitor(ServerMessageManager visitor) {
        visitor.gameMode(this);
    }
}
