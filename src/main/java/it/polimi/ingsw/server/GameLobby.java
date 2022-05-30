package it.polimi.ingsw.server;

import it.polimi.ingsw.network.ReservedRecipients;
import it.polimi.ingsw.network.messages.Type;
import it.polimi.ingsw.network.messages.servertoclient.events.BoardUpdateResponse;
import it.polimi.ingsw.network.messages.servertoclient.events.JoinLobbyResponse;
import it.polimi.ingsw.network.messages.servertoclient.events.JoinedLobbyResponse;
import it.polimi.ingsw.network.messages.servertoclient.events.LobbyCreatedResponse;
import it.polimi.ingsw.server.controller.GameController;
import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.server.model.TowerColour;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class GameLobby implements Runnable{

    private GameController controller;
    private ServerMessageHandler messageHandler;
    private Map<String,User> userMap;
    private int numberOfPlayers;
    private int connectedClients;
    private boolean expertVariant;
    private boolean isFull;
    private boolean isActive;
    private ClientHandler firstClient;

    public GameLobby(ClientHandler firstClient) {
        this.firstClient = firstClient;
        this.isActive = false;
    }

    public boolean isFull() {
        return (connectedClients == numberOfPlayers);
    }

    public void startGame() throws IOException {
        Map<String,TowerColour> playerMap = new HashMap<>();
        for ( Map.Entry<String, User> user : userMap.entrySet()){
            playerMap.put(user.getKey(),user.getValue().getTowerColour());
        }
        controller.startGame(playerMap,numberOfPlayers,expertVariant);
    }

    public void sendMessage(String recipientName, Message message){
        User recipientUser = userMap.get(recipientName);
        if (recipientName.equals(ReservedRecipients.BROADCAST.toString())){
            broadcastMessage(message);
        }else if(recipientUser != null){
            recipientUser.notify(message);
        }
    }

    private void broadcastMessage(Message message) {
        userMap.values().forEach(user -> user.notify(message));
    }

    public void parseMessageFromServerToClient(Message message) {
        messageHandler.parseMessageFromServerToClient(message);
    }

    public void addUser(ClientHandler clientHandler) {
      addUser(clientHandler,true);
    }
    public void addUser(ClientHandler clientHandler,boolean joinedLobby){
        User newUser = new User("tempNickname",clientHandler);
        newUser.startClientHandler();
        userMap.put(newUser.getUsername(),newUser);
        connectedClients++;
        if(joinedLobby){
            newUser.notify(new JoinedLobbyResponse(newUser.username, Type.JOINED_LOBBY));
        }
    }

    @Override
    public void run() {
        addUser(firstClient,false);
        broadcastMessage(new LobbyCreatedResponse(ReservedRecipients.BROADCAST.toString(), Type.NEW_LOBBY));
    }


    public void setInfo(String playerName, int numberOfPlayers, boolean expertVariant) {
        setFirstUserName(playerName);
        this.numberOfPlayers = numberOfPlayers;
        this.expertVariant = expertVariant;
        this.isActive = true;
    }


    private void setFirstUserName(String userName){
        User firstUser = userMap.get("tempNickname");
        firstUser.setUserName(userName);
        userMap.replace(userName,firstUser);
    }

    public boolean isActive() {
        return isActive;
    }
}
