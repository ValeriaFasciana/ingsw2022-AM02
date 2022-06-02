package it.polimi.ingsw.server.model.board;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class IsleCircleData{
    List<IsleData> isles;

    @JsonCreator
    public IsleCircleData(@JsonProperty("isles")List<IsleData> isles) {
        this.isles = isles;
    }

    @JsonGetter
    public List<IsleData> getIsles() {
        return isles;
    }
}
