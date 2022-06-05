package it.polimi.ingsw.server;

import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.shared.enums.TowerColour;

public class User {
    private String username;
    private ClientHandler client;
    private TowerColour towerColour;
    boolean isActive;

    public User(String username, ClientHandler client) {
        this.username = username;
        this.client = client;
        this.isActive = false;
        client.setNickname(username);
    }

    public ClientHandler getClient() {
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

    public void startClientHandler() {
        Thread thread = new Thread(client, "virtualClient_" + client.getClientAddress());
        thread.start();
    }

    public void setUserName(String userName) {
        this.username = userName;
        client.setNickname(userName);
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean isActive){
        this.isActive = isActive;
    }
}
