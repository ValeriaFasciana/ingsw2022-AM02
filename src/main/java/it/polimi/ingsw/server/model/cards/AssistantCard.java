package it.polimi.ingsw.server.model.cards;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AssistantCard {
    private int id;
    private int value;
    private int movement;

    public AssistantCard(@JsonProperty("id") int id,
                         @JsonProperty("value") int value,
                         @JsonProperty("movement") int movement) {
        this.id = id;
        this.value = value;
        this.movement = movement;
    }

    public int getId() {
        return id;
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
