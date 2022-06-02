package it.polimi.ingsw.network.messages.servertoclient.events;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.client.ClientMessageVisitor;
import it.polimi.ingsw.network.messages.MessageFromServerToClient;
import it.polimi.ingsw.network.messages.Type;
import it.polimi.ingsw.server.model.BoardData;

public class GameCreatedEvent extends MessageFromServerToClient {

    BoardData boardData;
    @JsonCreator
    public GameCreatedEvent(@JsonProperty("username") String username, @JsonProperty("type") Type type,
                            @JsonProperty("boardData") BoardData boardData) {
        super(username, type);
        this.boardData = boardData;
    }
    @JsonGetter
    public BoardData getBoardData() {
        return boardData;
    }

    @Override
    public void callVisitor(ClientMessageVisitor visitor) {
        visitor.gameCreated(this);
    }
}
