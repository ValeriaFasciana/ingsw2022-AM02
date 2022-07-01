package it.polimi.ingsw.server.lobby;

import it.polimi.ingsw.network.messages.servertoclient.events.AskLoginInfoRequest;
import it.polimi.ingsw.network.messages.servertoclient.events.InvalidUsernameResponse;
import it.polimi.ingsw.server.ServerMessageHandler;
import it.polimi.ingsw.server.VirtualClient;
import it.polimi.ingsw.shared.Constants;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

public class LobbyManager {
    List<GameLobby> lobbyList = new ArrayList<>();
    private static AtomicLong idCounter = new AtomicLong();
    Map<String, User> queueUserMap = new HashMap<>();
    private Timer timer = new Timer();

    public LobbyManager() {

    }

    /**
     * Method to handle create lobby
     * @param username username to add to lobby
     * @param playerName set username to player
     * @param expertVariant if true, game mode is expert
     * @param numberOfPlayers chosen number of players
     */
    public void createLobby(String username, String playerName, boolean expertVariant, int numberOfPlayers){
        User creatingUser = queueUserMap.get(username);
        creatingUser.setUserName(playerName);
        GameLobby newLobby = new GameLobby(creatingUser.getClient().getMessageHandler(),expertVariant,numberOfPlayers);
        newLobby.addUser(creatingUser,false);
        Thread lobbyThread = new Thread(newLobby,"lobby_"+createID());
        lobbyThread.start();
        lobbyList.add(newLobby);
    }

    /**
     * Method to handle new client
     * @param client client to handle
     */
    public void handleNewClient(VirtualClient client) {
        Optional<List<GameLobby>> joinableLobbies = getJoinableLobbies();
        Optional<List<GameLobby>> rejoinableLobbies = getReJoinableLobbies();
        String tempUsername = Constants.tempUsername + createID();
        User newUser = new User(Constants.tempUsername,client);
        queueUserMap.put(tempUsername,newUser);
        client.setMessageHandler(new ServerMessageHandler(this));
        newUser.startClientHandler();
        newUser.notify(new AskLoginInfoRequest(tempUsername,joinableLobbies.isPresent() && !joinableLobbies.get().isEmpty(),rejoinableLobbies.isPresent() && !rejoinableLobbies.get().isEmpty()));

    }

    /**
     *Method to get available lobbies in a server
     * @return available lobbies
     */
    public Optional<List<GameLobby>> getAvailableLobbies(){
        if (lobbyList.isEmpty()) return Optional.empty();
        return Optional.ofNullable(lobbyList.stream().filter(gameLobby -> ((!gameLobby.isFull()))).toList());
    }

    public static String createID() {
        return String.valueOf(idCounter.getAndIncrement());
    }

    /**
     * Method to add player to lobby
     * @param username client's username
     * @param playerNickName player's nickname
     * @param isRejoin if true, the client can rejoin the lobby
     */
    public void addPlayerToLobby(String username, String playerNickName, boolean isRejoin) {
        User toAddUser = queueUserMap.get(username);
        toAddUser.setUserName(playerNickName);
        if(isRejoin && getReJoinableLobbies().isPresent()){
            List<GameLobby> matchingNameLobbies = getReJoinableLobbies().get().stream().filter(lobby -> lobby.getUserMap().containsKey(playerNickName)).toList();
            if(matchingNameLobbies.isEmpty()){
                toAddUser.notify(new  InvalidUsernameResponse(playerNickName,true));
                return;
            }
            matchingNameLobbies.get(0).rejoinUser(toAddUser);
        }
        else{
            List<GameLobby> joinableLobbies = getJoinableLobbies().get();
            joinableLobbies.get(0).addUser(toAddUser);
        }

//        if(matchingNameLobbies.isEmpty()){
//            Optional<List<GameLobby>> availableLobbies = getAvailableLobbies();
//            if(availableLobbies.isPresent() && !availableLobbies.get().isEmpty()){
//                availableLobbies.get().get(0).addUser(toAddUser);
//            }
//        }else{
//
//        }
    }

    /**
     * Method to get joinable lobbies in a server
     * @return list of joinable lobbies
     */
    private Optional<List<GameLobby>> getJoinableLobbies(){
        if (lobbyList.isEmpty()) return Optional.empty();
        return Optional.ofNullable(lobbyList.stream().filter(GameLobby::isJoinable).toList());
    }

    /**
     * Method to get rejoinable lobbies in a server
     * @return list of rejoinable lobbies
     */
    private Optional<List<GameLobby>> getReJoinableLobbies(){
        if (lobbyList.isEmpty()) return Optional.empty();
        return Optional.ofNullable(lobbyList.stream().filter(GameLobby::isRejoinable).toList());
    }

    /**
     * Method to handle client disconnection
     * @param client client to handle
     */
    public void handleClientDisconnection(VirtualClient client) {
        List<GameLobby> userLobby = lobbyList.stream().filter(lobby -> lobby.getUserMap().containsKey(client.getNickname())).toList();
       if(userLobby.isEmpty())return;
       userLobby.get(0).handleClientDisconnection(client);
       if(userLobby.get(0).getConnectedClients() < 2 && userLobby.get(0).isRejoinable()){
           timer.schedule(new TimerTask() {
               @Override
               public void run() {
                   if(userLobby.get(0).getConnectedClients() < 2 && userLobby.get(0).isRejoinable()) {
                       userLobby.get(0).notifyTimeoutGameEnd();
                       userLobby.get(0).terminate();
                   }
               }
           }, 120000);
       }

    }

    /**
     * Method to end lobby
     * @param lobby lobby to handle
     */
    public void endLobby(GameLobby lobby) {
        lobby.terminate();
        lobbyList.remove(lobby);
    }
}
