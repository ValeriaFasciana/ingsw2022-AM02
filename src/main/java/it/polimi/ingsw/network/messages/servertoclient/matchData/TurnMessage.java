package it.polimi.ingsw.network.messages.servertoclient.matchData;

import it.polimi.ingsw.network.messages.Type;

/**
 * Message to notify the current turn owner
 */

public class TurnMessage extends MatchDataMessage {
    private boolean started;

    public TurnMessage(String nickname, Type type, boolean started) {
        super(nickname, type);
        this.started = started;
    }


    public boolean isStarted() {
        return started;
    }

}
