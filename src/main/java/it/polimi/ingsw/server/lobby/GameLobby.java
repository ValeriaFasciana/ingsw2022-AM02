package it.polimi.ingsw.server.lobby;

import it.polimi.ingsw.network.ReservedRecipients;
import it.polimi.ingsw.network.messages.Type;
import it.polimi.ingsw.network.messages.servertoclient.events.*;
import it.polimi.ingsw.server.ServerMessageVisitor;
import it.polimi.ingsw.server.VirtualClient;
import it.polimi.ingsw.server.controller.GameController;
import it.polimi.ingsw.network.messages.Message;

import java.util.*;

public class GameLobby implements Runnable{

    private final GameController controller;
    private final ServerMessageVisitor messageHandler;
    private Map<String, User> userMap;
    private List<String> orderedUsers;
    private final int numberOfPlayers;
    private int connectedClients;
    private boolean expertVariant;
    private boolean hasStartedGame;
    private Timer timer = new Timer();

    /**
     *
     * @param messageHandler
     * @param expertVariant
     * @param numberOfPlayers
     */
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

    /**
     *
     */
    public void createGame(){
        controller.createGame(orderedUsers,numberOfPlayers,expertVariant);
    }

    /**
     *
     * @param recipientName
     * @param message
     */
    public void sendMessage(String recipientName, Message message){
        User recipientUser = userMap.get(recipientName);
        if (recipientName.equals(ReservedRecipients.BROADCAST.toString())){
            broadcastMessage(message);
        }else if(recipientUser != null && recipientUser.isActive() ){
            recipientUser.notify(message);
        }
    }

    /**
     *
     * @param message
     */
    private void broadcastMessage(Message message) {
        userMap.values().stream().filter(User::isActive).forEach(user -> user.notify(message));
    }

    /**
     *
     * @param message
     */
    public void parseMessageFromServerToClient(Message message) {
        messageHandler.parseMessageFromServerToClient(message);
    }

    /**
     *
     * @param user
     */
    public void addUser(User user) {
      addUser(user,true);
    }

    /**
     *
     * @param user
     * @param joinedLobby
     */
    public void addUser(User user,boolean joinedLobby){
        if(!validNickname(user)) return;
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

    /**
     *
     * @param user
     * @return
     */
    private boolean validNickname(User user) {
        if(userMap.containsKey(user.getUsername())){
            user.notify(new InvalidUsernameResponse(user.getUsername(),false));
            return false;
        }
        return true;
    }

    /**
     *
     */
    private void checkGameStarting() {
        if(canStartGame()){
            createGame();
            hasStartedGame = true;
        }
    }

    /**
     *
     * @return
     */
    private boolean canStartGame() {
        boolean canStart = isFull();
        for(User user : userMap.values()){
            canStart = canStart && user.isActive();
        }
        return canStart;
    }

    /**
     *
     * @return
     */
    public boolean isJoinable(){
        return !isFull() && !hasStartedGame;
    }

    /**
     *
     * @return
     */
    public boolean isRejoinable(){
        return !isFull() && hasStartedGame && numberOfPlayers == userMap.size();
    }

    /**
     *
     * @param client
     */
    public void handleClientDisconnection(VirtualClient client) {
        String username = client.getNickname();
        userMap.get(username).setActive(false);
        controller.deactivatePlayer(username);
        connectedClients--;
        parseMessageFromServerToClient(new PlayerDisconnectedEvent(username));
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                controller.manageDisconnection(username);
            }
        }, 10000);
    }

    /**
     *
     * @param user
     */
    public void rejoinUser(User user) {
        timer.cancel();
        User originalUser = userMap.get(user.getUsername());
        originalUser.setClient(user.getClient());
        originalUser.setActive(true);
        user.getClient().getMessageHandler().setLobby(this);
        user.getClient().getMessageHandler().setController(controller);
        controller.activatePlayer(user.getUsername());
        controller.handleRejoin(user.getUsername());
        broadcastMessage(new JoinedLobbyResponse(user.getUsername(), Type.NOTIFY));
    }

    /**
     *
     * @return
     */
    public int getConnectedClients() {
        return connectedClients;
    }

    /**
     *
     */
    public void notifyTimeoutGameEnd() {
        broadcastMessage(new EndGameEvent("","Game ended because nobody reconnected on time",false));
    }

    /**
     *
     */
    public void terminate() {
        Thread.currentThread().interrupt();
    }
}
