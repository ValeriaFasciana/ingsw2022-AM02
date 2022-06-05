package it.polimi.ingsw.server.model.board;

import it.polimi.ingsw.network.data.IsleData;
import it.polimi.ingsw.server.model.StudentContainer;
import it.polimi.ingsw.shared.enums.TowerColour;

public class IsleGroup extends StudentContainer {
    private int size;
    private int banCounter;
    private TowerColour tower;
    private IsleGroup next;
    private IsleGroup previous;



    public IsleGroup() {
        super(130);
        this.size = 1;
        this.banCounter = 0;
        this.tower = null;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Boolean isBanned() {
        return banCounter >0;
    }

    public void addBan() {
        this.banCounter++;
    }

    public void removeBan(){
        if(banCounter>0)this.banCounter--;
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

    public void increaseSize() {
        this.size++;
    }

    public IsleData getData(){
        return new IsleData(this.getStudentCountMap(),banCounter,tower,size);
    }
}
