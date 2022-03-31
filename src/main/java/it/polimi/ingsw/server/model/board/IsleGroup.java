package it.polimi.ingsw.server.model.board;

import it.polimi.ingsw.server.model.StudentContainer;

public class IsleGroup extends StudentContainer {

    private int size;
    private Boolean isBanned;

    public IsleGroup() {
        super(130);
        this.size = 1;
        this.isBanned = false;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Boolean getBanned() {
        return isBanned;
    }

    public void setBanned(Boolean banned) {
        isBanned = banned;
    }

}
