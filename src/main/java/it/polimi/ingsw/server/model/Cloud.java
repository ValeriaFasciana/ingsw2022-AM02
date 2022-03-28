package it.polimi.ingsw.server.model;

public class Cloud extends StudentContainer{
    private final Boolean side;

    public Cloud(Integer capacity, Boolean side) {
        super(capacity);
        this.side = side;
    }
}
