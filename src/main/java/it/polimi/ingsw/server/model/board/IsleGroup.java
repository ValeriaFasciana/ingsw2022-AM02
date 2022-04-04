package it.polimi.ingsw.server.model.board;

import it.polimi.ingsw.server.model.StudentContainer;
import it.polimi.ingsw.server.model.TowerColour;

import java.util.UUID;

public class IsleGroup extends StudentContainer {
    private int index;
    private int size;
    private Boolean isBanned;
    private TowerColour tower;

    public IsleGroup(int index) {
        super(130);
        this.size = 1;
        this.isBanned = false;
        this.tower = null;
        this.index= index;
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
        this.isBanned = banned;
    }

    public TowerColour getTower() {
        return tower;
    }

    public void setTower(TowerColour tower) {
        this.tower = tower;
    }

    public Integer getIndex() {
        return index;
    }

}
