package it.polimi.ingsw.network.messages.servertoclient.events;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.client.ClientMessageVisitor;
import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.messages.MessageFromServerToClient;
import it.polimi.ingsw.server.model.BoardData;
import it.polimi.ingsw.network.messages.Type;

public class BoardUpdateResponse extends MessageFromServerToClient {

    private final BoardData boardData;


    /**
     * Message constructor
     *
     * @param username the sender's username
     * @param type     the message type
     */
    @JsonCreator
    public BoardUpdateResponse(@JsonProperty("username")String username,@JsonProperty("type") Type type,@JsonProperty("boardData") BoardData boardData) {
        super(username, type);
        this.boardData = boardData;
    }

    @JsonGetter
    public BoardData getBoardData() {
        return boardData;
    }

    @Override
    public void callVisitor(ClientMessageVisitor visitor) {
        visitor.boardUpdate(this);
    }
}
