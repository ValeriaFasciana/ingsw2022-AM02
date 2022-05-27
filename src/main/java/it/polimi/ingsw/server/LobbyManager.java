package it.polimi.ingsw.server;

import java.util.*;

public class LobbyManager {
    Map<String,GameLobby> userToLobbyMap;
    Random rand = new Random();

    public LobbyManager() {
        userToLobbyMap = new HashMap<>();
    }
    public void createLobby(ClientHandler client){
        GameLobby newLobby = new GameLobby(client);
        Thread lobbyThread = new Thread(newLobby,"lobby_"+rand.nextInt(1000));
        lobbyThread.start();
    }

    public void handleNewClient(ClientHandler client) {
        Optional<List<GameLobby>> availableLobbies = getAvailableLobbies();
        if(availableLobbies.isPresent()){
            GameLobby toJoinLobby = availableLobbies.get().get(0);
            toJoinLobby.addUser(client);
            if(toJoinLobby.isFull()){
                toJoinLobby.startGame();
            }
        }
        else{
            createLobby(client);
        }
    }




    public Optional<List<GameLobby>> getAvailableLobbies(){
        if (userToLobbyMap.isEmpty()) return Optional.empty();
        return Optional.ofNullable(userToLobbyMap.values().stream().filter(gameLobby -> (!(gameLobby.isFull())&& gameLobby.isActive())).toList());
    }
}
