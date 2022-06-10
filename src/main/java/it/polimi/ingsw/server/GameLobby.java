package it.polimi.ingsw.server;

import it.polimi.ingsw.network.ReservedRecipients;
import it.polimi.ingsw.network.messages.Type;
import it.polimi.ingsw.network.messages.servertoclient.events.InvalidUsernameResponse;
import it.polimi.ingsw.network.messages.servertoclient.events.JoinedLobbyResponse;
import it.polimi.ingsw.network.messages.servertoclient.events.LobbyCreatedResponse;
import it.polimi.ingsw.server.controller.GameController;
import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.shared.Constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameLobby implements Runnable{

    private final GameController controller;
    private final ServerMessageVisitor messageHandler;
    private Map<String,User> userMap;
    private List<String> orderedUsers;
    private int numberOfPlayers;
    private int connectedClients;
    private boolean expertVariant;
    private boolean isActive;

    public GameLobby() {
        messageHandler = new ServerMessageHandler(this);
        controller = new GameController(messageHandler);
        messageHandler.setController(controller);
        orderedUsers = new ArrayList<>();
        this.isActive = false;
        this.userMap = new HashMap<>();
    }

    public boolean isFull() {
        return (connectedClients == numberOfPlayers);
    }

    public void createGame(){

        controller.createGame(orderedUsers,numberOfPlayers,expertVariant);
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

    public void addUser(User user) {
      addUser(user,true);
    }
    public void addUser(User user,boolean joinedLobby){
        user.getClient().setMessageHandler(this.messageHandler);
        userMap.put(user.getUsername(),user);
        connectedClients++;
        if(joinedLobby){
            user.notify(new JoinedLobbyResponse(user.getUsername(), Type.JOINED_LOBBY));
        }
    }

    @Override
    public void run() {
        broadcastMessage(new LobbyCreatedResponse(ReservedRecipients.BROADCAST.toString(), Type.NEW_LOBBY));
    }


    public void setInfo(String playerName, int numberOfPlayers, boolean expertVariant) {
        if(!validNickname(playerName))return;
        setUsername(playerName);
        this.numberOfPlayers = numberOfPlayers;
        this.expertVariant = expertVariant;
        isActive = true;
    }

    private boolean validNickname(String playerName) {
        if(userMap.containsKey(playerName)){
            sendMessage(Constants.tempUsername,new InvalidUsernameResponse(Constants.tempUsername));
            return false;
        }
        return true;
    }


    public void setUsername(String userName){
        if(!validNickname(userName))return;
        User user = userMap.get(Constants.tempUsername);
        user.setUserName(userName);
        user.setActive(true);
        userMap.remove(Constants.tempUsername);
        userMap.put(userName,user);
        orderedUsers.add(userName);
        checkGameStarting();
    }

    private void checkGameStarting() {
        if(canStartGame()){
            createGame();
        }
    }

    private boolean canStartGame() {
        boolean canStart = isFull() && isActive;
        for(User user : userMap.values()){
            canStart = canStart && user.isActive();
        }
        return canStart;
    }

    public boolean isActive() {
        return isActive;
    }
}
