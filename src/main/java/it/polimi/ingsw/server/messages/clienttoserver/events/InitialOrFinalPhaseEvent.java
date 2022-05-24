package it.polimi.ingsw.server.messages.clienttoserver.events;


import java.util.UUID;


public class InitialOrFinalPhaseEvent extends it.polimi.ingsw.network.messages.clienttoserver.events.InitialOrFinalPhaseEvent {



    public int getChoice(){
        return choice;
    }

    public UUID getLeaderId(){
        return leaderId;
    }




}
