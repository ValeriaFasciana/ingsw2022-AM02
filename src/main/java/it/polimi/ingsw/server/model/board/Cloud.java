package it.polimi.ingsw.server.model.board;

import it.polimi.ingsw.server.model.StudentContainer;

public class Cloud extends StudentContainer {
    private final int index;
    private final Boolean side;


    public Cloud(Integer capacity, Boolean side,int index) {
        super(capacity);
        this.side = side;
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public Boolean getSide() {
        return side;
    }
}
