package it.polimi.ingsw.server.model.board;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.shared.enums.PawnColour;

import java.util.Map;

public class IsleData{
    private Map<PawnColour,Integer> studentMap;
    private int banCounter;

    @JsonCreator
    public IsleData(@JsonProperty("studentMap")Map<PawnColour, Integer> studentMap,@JsonProperty("banCounter") int banCounter) {
        this.studentMap = studentMap;
        this.banCounter = banCounter;
    }

    @JsonGetter
    public Map<PawnColour, Integer> getStudentMap() {
        return studentMap;
    }

    @JsonGetter
    public int getBanCounter() {
        return banCounter;
    }
}