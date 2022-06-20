package it.polimi.ingsw.server;

import it.polimi.ingsw.network.ReservedRecipients;
import it.polimi.ingsw.network.messages.Type;
import it.polimi.ingsw.network.messages.servertoclient.events.*;
import it.polimi.ingsw.server.controller.GameController;
import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.shared.Constants;
import org.apache.commons.compress.archivers.dump.DumpArchiveEntry;

import java.util.*;

public class GameLobby implements Runnable{

    private final GameController controller;
    private final ServerMessageVisitor messageHandler;
    private Map<String,User> userMap;
    private List<String> orderedUsers;
    private final int numberOfPlayers;
    private int connectedClients;
    private boolean expertVariant;
    private boolean hasStartedGame;
    private Timer timer = new Timer();

    public GameLobby(ServerMessageVisitor messageHandler, boolean expertVariant, int numberOfPlayers) {
        this.messageHandler = messageHandler;
        this.messageHandler.setLobby(this);
        this.expertVariant = expertVariant;
        this.numberOfPlayers = numberOfPlayers;
        controller = new GameController(this.messageHandler);
        this.messageHandler.setController(controller);
        orderedUsers = new ArrayList<>();
        this.userMap = new HashMap<>();
        hasStartedGame = false;
    }

    public Map<String, User> getUserMap() {
        return userMap;
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
        }else if(recipientUser != null && recipientUser.isActive() ){
            recipientUser.notify(message);
        }
    }

    private void broadcastMessage(Message message) {
        userMap.values().stream().filter(User::isActive).forEach(user -> user.notify(message));
    }

    public void parseMessageFromServerToClient(Message message) {
        messageHandler.parseMessageFromServerToClient(message);
    }

    public void addUser(User user) {
      addUser(user,true);
    }

    public void addUser(User user,boolean joinedLobby){
        userMap.put(user.getUsername(),user);
        user.getClient().getMessageHandler().setLobby(this);
        user.getClient().getMessageHandler().setController(controller);
        connectedClients++;
        orderedUsers.add(user.getUsername());
        user.setActive(true);
        if(joinedLobby){
            broadcastMessage(new JoinedLobbyResponse(user.getUsername(), Type.JOINED_LOBBY));
        }
        checkGameStarting();
    }

    @Override
    public void run() {
        broadcastMessage(new LobbyCreatedResponse(ReservedRecipients.BROADCAST.toString(), Type.NEW_LOBBY));
    }


//    public void setInfo(String playerName, int numberOfPlayers, boolean expertVariant) {
//        if(!validNickname(playerName))return;
//        setUsername(playerName);
//        this.numberOfPlayers = numberOfPlayers;
//        this.expertVariant = expertVariant;
//        isActive = true;
//    }

    private boolean validNickname(String playerName) {
        if(userMap.containsKey(playerName)){
            sendMessage(Constants.tempUsername,new InvalidUsernameResponse(Constants.tempUsername));
            return false;
        }
        return true;
    }


    public void setUsername(String userName){
        if(!validNickname(userName))return;
//        User user = userMap.get(Constants.tempUsername);
//        user.setUserName(userName);
//        user.setActive(true);
//        userMap.remove(Constants.tempUsername);
//        userMap.put(userName,user);

        checkGameStarting();
    }

    private void checkGameStarting() {
        if(canStartGame()){
            createGame();
            hasStartedGame = true;
        }
    }



    private boolean canStartGame() {
        boolean canStart = isFull();
        for(User user : userMap.values()){
            canStart = canStart && user.isActive();
        }
        return canStart;
    }


    public boolean isJoinable(){
        return !isFull() && !hasStartedGame;
    }

    public boolean isRejoinable(){
        return !isFull() && hasStartedGame && numberOfPlayers == userMap.size();
    }


    public void handleClientDisconnection(ClientHandler client) {
        userMap.get(client.getNickname()).setActive(false);
        controller.deactivatePlayer(client.getNickname());
        connectedClients--;
        parseMessageFromServerToClient(new PlayerDisconnectedEvent(client.getNickname()));
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                controller.manageDisconnection();
            }
        }, 10000);
    }



    public void rejoinUser(User user) {
        timer.cancel();
        User originalUser = userMap.get(user.getUsername());
        originalUser.setClient(user.getClient());
        originalUser.setActive(true);
        controller.activatePlayer(user.getUsername());
        broadcastMessage(new JoinedLobbyResponse(user.getUsername(), Type.NOTIFY));
    }

}
