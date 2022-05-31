package it.polimi.ingsw.server;

import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.server.model.TowerColour;

public class User {
    String username;
    ClientHandler client;

    TowerColour towerColour;

    public User(String username, ClientHandler client) {
        this.username = username;
        this.client = client;
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
    }
}
