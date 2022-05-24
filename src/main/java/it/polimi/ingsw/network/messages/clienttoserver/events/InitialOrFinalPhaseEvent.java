package it.polimi.ingsw.network.messages.clienttoserver.events;




import java.util.UUID;

public class InitialOrFinalPhaseEvent extends Event {

    protected int choice = 0;


    protected UUID leaderId;

    public InitialOrFinalPhaseEvent(int choice){
        this.choice = choice;
    }

    public InitialOrFinalPhaseEvent(int choice, UUID leaderId){
        this.choice = choice;
        this.leaderId = leaderId;
    }

    public InitialOrFinalPhaseEvent(){}

}
