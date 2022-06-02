package it.polimi.ingsw.server;

import it.polimi.ingsw.shared.Constants;

import java.util.*;

public class LobbyManager {
    List<GameLobby> lobbyList;
    Random rand = new Random();

    public LobbyManager() {
        lobbyList = new ArrayList<>();
    }

    public void createLobby(User firstUser){
        GameLobby newLobby = new GameLobby();
        newLobby.addUser(firstUser,false);
        Thread lobbyThread = new Thread(newLobby,"lobby_"+rand.nextInt(1000));
        lobbyThread.start();
        lobbyList.add(newLobby);
    }

    public void handleNewClient(ClientHandler client) {
        Optional<List<GameLobby>> availableLobbies = getAvailableLobbies();
        User newUser = new User(Constants.tempUsername,client);
        newUser.startClientHandler();
        if(availableLobbies.isPresent() && !availableLobbies.get().isEmpty()){
             availableLobbies.get().get(0).addUser(newUser);
        }
        else{
            createLobby(newUser);
        }
    }




    public Optional<List<GameLobby>> getAvailableLobbies(){
        if (lobbyList.isEmpty()) return Optional.empty();
        return Optional.ofNullable(lobbyList.stream().filter(gameLobby -> ((!gameLobby.isFull())&& gameLobby.isActive())).toList());
    }
}
