package it.polimi.ingsw.network.messages.servertoclient.matchData;

import it.polimi.ingsw.client.ClientMessageVisitor;
import it.polimi.ingsw.network.messages.Type;
import it.polimi.ingsw.network.messages.MessageFromServerToClient;

/**
 * Common features to messages sent to update {@link it.polimi.ingsw.client.MatchData}
 */

public abstract class MatchDataMessage extends MessageFromServerToClient {
    private String nickname;
    public MatchDataMessage(String nickname, Type type) {
        super(nickname, type);
    }

    @Override
    public void callVisitor(ClientMessageVisitor visitor) {
    }

    public String getNickname(){
        return nickname;
    }

    public void setNickname(String nickname){
        this.nickname = nickname;
    }

}
