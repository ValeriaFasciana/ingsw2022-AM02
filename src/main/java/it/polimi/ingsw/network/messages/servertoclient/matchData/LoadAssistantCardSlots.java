package it.polimi.ingsw.network.messages.servertoclient.matchData;

import it.polimi.ingsw.network.messages.Type;

import java.util.Stack;

/**
 * Message to update owned leader cards client-side
 */

public class LoadAssistantCardSlots extends MatchDataMessage{
    private Stack[] slots;

    public LoadAssistantCardSlots(String nickname, Type type, Stack[] slots) {
        super(nickname, type);
        this.slots = slots;
    }

    public Stack[] getSlots() {
        return slots;
    }
}