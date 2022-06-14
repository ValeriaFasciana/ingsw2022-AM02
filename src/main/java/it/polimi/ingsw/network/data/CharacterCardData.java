package it.polimi.ingsw.network.data;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.shared.enums.PawnColour;

import java.util.Map;

public class CharacterCardData {
    private int id;
    private int price;
    private Map<PawnColour,Integer> students;
    @JsonCreator
    public CharacterCardData(@JsonProperty("id") int id,
                             @JsonProperty("price") int price,
                             @JsonProperty("students") Map<PawnColour,Integer> students){
        this.id = id;
        this.price = price;
        this.students = students;
    }

    @JsonGetter
    public int getId() {
        return id;
    }

    @JsonGetter
    public int getPrice() {
        return price;
    }

    @JsonGetter
    public Map<PawnColour, Integer> getStudents() {
        return this.students;
    }
}
