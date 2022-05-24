package it.polimi.ingsw.network.messages.clienttoserver;

public class PingMessageFromClient extends ClientToServerMessage{


   private  final String message;

    public PingMessageFromClient(String message) {
        this.message = message;
    }

    public String getPingMessage(){
        return message;
    }


}
