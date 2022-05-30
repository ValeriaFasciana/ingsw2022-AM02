package it.polimi.ingsw.client;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Stack;

//This class represent a light version of the player and the playerboard of the model
public class LightClient {
    private String nickname;
    private Map<Integer, Boolean> ownedAssistantCards;
    public String getNickname() {
        return nickname;
    }



    private Stack<Integer>[] assistantCardSlots;

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    /**
     * Method to add an assistant card to a player
     * @param ID the ID of the card
     * @param active true if the card is active
     */
    public void addAssistantCard(Integer ID, boolean active) {
        ownedAssistantCards.put(ID, active);
    }

    public List<Integer> getOwnedAssistantCards() {
        return new ArrayList<Integer>(ownedAssistantCards.keySet());
    }

    public void setAssistantCardSlots(Stack[] assistantCardSlots){
        this.assistantCardSlots = assistantCardSlots;
    }

    public Stack<Integer>[] getAssistantCardSlots() {
        return assistantCardSlots;
    }
}
