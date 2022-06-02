package it.polimi.ingsw.server.model.board;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.shared.enums.PawnColour;

import java.util.EnumMap;

public class CloudData {
    EnumMap<PawnColour,Integer> studentMap;
    boolean side;

    @JsonCreator
    public CloudData(@JsonProperty("studentMap") EnumMap<PawnColour, Integer> studentMap, @JsonProperty("side")boolean side) {
        this.studentMap = studentMap;
        this.side = side;
    }

    @JsonGetter
    public EnumMap<PawnColour, Integer> getStudentMap() {
        return studentMap;
    }

    @JsonGetter
    public boolean getSide() {
        return side;
    }
}
