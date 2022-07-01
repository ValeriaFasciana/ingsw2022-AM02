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
     *Default constructor
     * @param messageHandler server message handler
     * @param expertVariant if true, game mode is expert
     * @param numberOfPlayers number of players
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
     * Method to handle creation of game
     */
    public void createGame(){
        controller.createGame(orderedUsers,numberOfPlayers,expertVariant);
    }

    /**
     * Send message to client
     * @param recipientName player to send the message to
     * @param message message to send
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
     * Send message via broadcast
     * @param message message to broadcast
     */
    private void broadcastMessage(Message message) {
        userMap.values().stream().filter(User::isActive).forEach(user -> user.notify(message));
    }

    /**
     * Message parser from server to client
     * @param message message to parse
     */
    public void parseMessageFromServerToClient(Message message) {
        messageHandler.parseMessageFromServerToClient(message);
    }

    /**
     * Method to add user to game
     * @param user player to add
     */
    public void addUser(User user) {
      addUser(user,true);
    }

    /**
     * Method to add user, when it's possible to join game
     * @param user player to add
     * @param joinedLobby if true, it broadcasts a message that player has joined
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

    /**
     * Broadcasts message that lobby is created
     */
    @Override
    public void run() {
        broadcastMessage(new LobbyCreatedResponse(ReservedRecipients.BROADCAST.toString(), Type.NEW_LOBBY));
    }

    /**
     * Method to handle input of nickname
     * @param user client that inputs nickname
     * @return true if valid, false if not valid
     */
    private boolean validNickname(User user) {
        if(userMap.containsKey(user.getUsername())){
            user.notify(new InvalidUsernameResponse(user.getUsername(),false));
            return false;
        }
        return true;
    }

    /**
     * Method to check if game can start
     */
    private void checkGameStarting() {
        if(canStartGame()){
            createGame();
            hasStartedGame = true;
        }
    }

    /**
     * Method to check conditions to start the game
     * @return true if game is full
     */
    private boolean canStartGame() {
        boolean canStart = isFull();
        for(User user : userMap.values()){
            canStart = canStart && user.isActive();
        }
        return canStart;
    }

    /**
     * Method to handle if a player can join a game
     * @return true if game is joinable
     */
    public boolean isJoinable(){
        return !isFull() && !hasStartedGame;
    }

    /**
     * Method o handle if a player can rejoin a game
     * @return true if game is rejoinable
     */
    public boolean isRejoinable(){
        return !isFull() && hasStartedGame && numberOfPlayers == userMap.size();
    }

    /**
     * Method to handle disconnection of a client
     * @param client client to handle
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
     * Method to handle rejoin of a client
     * @param user client to handle
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
     * Method to return connected clients
     * @return connected clients
     */
    public int getConnectedClients() {
        return connectedClients;
    }

    /**
     * Method to notify the game ended because of time out
     */
    public void notifyTimeoutGameEnd() {
        broadcastMessage(new EndGameEvent("","Game ended because nobody reconnected on time",false));
    }

    /**
     * Method to terminate current thread
     */
    public void terminate() {
        Thread.currentThread().interrupt();
    }
}
