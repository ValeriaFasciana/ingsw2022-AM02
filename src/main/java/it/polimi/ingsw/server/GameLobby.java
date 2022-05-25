package it.polimi.ingsw.server;

import it.polimi.ingsw.network.ReservedRecipients;
import it.polimi.ingsw.server.controller.GameController;
import it.polimi.ingsw.network.messages.Message;

import java.util.Map;

public class GameLobby {

    private GameController controller;
    private ServerMessageHandler messageHandler;
    private Map<String,User> userMap;

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

}
