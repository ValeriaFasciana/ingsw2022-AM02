package it.polimi.ingsw.server.lobby;

import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.server.VirtualClient;
import it.polimi.ingsw.shared.enums.TowerColour;

public class User {
    private String username;
    private VirtualClient client;
    private TowerColour towerColour;
    boolean isActive;

    /**
     * Default constructor
     * @param username username of client
     * @param client client to handle
     */
    public User(String username, VirtualClient client) {
        this.username = username;
        this.client = client;
        this.isActive = false;
        client.setNickname(username);
    }

    public VirtualClient getClient() {
        return client;
    }

    public void notify(Message message) {
        client.notify(message);
    }

    public TowerColour getTowerColour() {
        return towerColour;
    }

    public String getUsername() {
        return username;
    }

    /**
     * Method to start client Handler
     */
    public void startClientHandler() {
        Thread thread = new Thread(client, "virtualClient_" + client.getClientAddress());
        thread.start();
    }

    public void setUserName(String username) {
        this.username = username;
        client.setNickname(username);
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean isActive){
        this.isActive = isActive;
    }

    public void setClient(VirtualClient client) {
        this.client = client;
    }
}
