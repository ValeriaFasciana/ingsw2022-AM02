package it.polimi.ingsw.server.model;

public class Hall extends StudentContainer{
    public Hall(){
        super(50);
    }

    public boolean isLineFull(PawnColour colour){
        return !(super.getCount(colour) < 10);
    }
}
