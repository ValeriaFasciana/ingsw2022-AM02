package it.polimi.ingsw.server.model;

public class AssistantCard {
    private int value;
    private int movement;

    public AssistantCard(int value, int movement) {
        this.value = value;
        this.movement = movement;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public Integer getMovement() {
        return movement;
    }

    public void setMovement(Integer movement) {
        this.movement = movement;
    }
}
