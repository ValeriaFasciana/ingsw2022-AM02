package it.polimi.ingsw.network.messages.servertoclient.events;

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
    public BoardUpdateResponse(String username, Type type,BoardData boardData) {
        super(username, type);
        this.boardData = boardData;
    }

    @Override
    public void callVisitor(ClientMessageVisitor visitor) {
        visitor.boardUpdate(boardData);
    }
}
