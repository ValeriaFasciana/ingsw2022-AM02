package it.polimi.ingsw.server.model.board;

import it.polimi.ingsw.server.model.StudentContainer;

public class Cloud extends StudentContainer {
    private final Boolean side;

    public Cloud(Integer capacity, Boolean side) {
        super(capacity);
        this.side = side;
    }

    public Boolean getSide() {
        return side;
    }
}
