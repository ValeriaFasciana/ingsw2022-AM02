package it.polimi.ingsw.network.messages.clienttoserver.events;

public abstract class Event {

    protected String playerNickname = "";

    public void setPlayerNickname(String playerNickname){this.playerNickname = playerNickname;}

    public String getPlayerNickname(){
        return playerNickname;
    }
}
