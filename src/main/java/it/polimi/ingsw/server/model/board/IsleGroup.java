package it.polimi.ingsw.server.model.board;

import it.polimi.ingsw.server.model.StudentContainer;
import it.polimi.ingsw.server.model.TowerColour;

public class IsleGroup extends StudentContainer {
    private int size;
    private Boolean isBanned;
    private TowerColour tower;
    private IsleGroup next;
    private IsleGroup previous;

    public IsleGroup() {
        super(130);
        this.size = 1;
        this.isBanned = false;
        this.tower = null;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Boolean isBanned() {
        return isBanned;
    }

    public void setBanned(Boolean banned) {
        this.isBanned = banned;
    }

    public IsleGroup getNext() {
        return next;
    }

    public IsleGroup getPrevious() {
        return previous;
    }

    public void setNext(IsleGroup next) {
        this.next = next;
    }

    public void setPrevious(IsleGroup previous) {
        this.previous = previous;
    }

    public TowerColour getTower() {
        return tower;
    }

    public void setTower(TowerColour tower) {
        this.tower = tower;
    }

}
