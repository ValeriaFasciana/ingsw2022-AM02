package it.polimi.ingsw.network.messages.servertoclient.events;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.client.ClientMessageVisitor;
import it.polimi.ingsw.network.ReservedRecipients;
import it.polimi.ingsw.network.messages.MessageFromServerToClient;
import it.polimi.ingsw.network.messages.Type;
import it.polimi.ingsw.network.data.BoardData;

public class GameCreatedEvent extends MessageFromServerToClient {

    private boolean expertMode;
    private BoardData boardData;
    @JsonCreator
    public GameCreatedEvent(@JsonProperty("expertMode") boolean expertMode,
                            @JsonProperty("boardData") BoardData boardData) {
        super(ReservedRecipients.BROADCAST.toString(), Type.SERVER_RESPONSE);
        this.boardData = boardData;
        this.expertMode = expertMode;
    }

    @JsonGetter
    public BoardData getBoardData() {
        return boardData;
    }

    @JsonGetter
    public boolean isExpertMode() {
        return expertMode;
    }

    @Override
    public void callVisitor(ClientMessageVisitor visitor) {
        visitor.gameCreated(this);
    }

}
