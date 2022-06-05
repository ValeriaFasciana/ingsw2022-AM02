package it.polimi.ingsw.network.data;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.shared.enums.TowerColour;
import it.polimi.ingsw.shared.enums.PawnColour;

import java.util.Map;

public class IsleData{
    private Map<PawnColour,Integer> studentMap;
    private int banCounter;
    private TowerColour towerColour;
    private int size;

    @JsonCreator
    public IsleData(@JsonProperty("studentMap")Map<PawnColour,Integer> studentMap,
                    @JsonProperty("banCounter") int banCounter,
                    @JsonProperty("towerColour")TowerColour towerColour,
                    @JsonProperty("size")int size) {
        this.studentMap = studentMap;
        this.banCounter = banCounter;
        this.towerColour = towerColour;
        this.size = size;
    }

    @JsonGetter
    public Map<PawnColour, Integer> getStudentMap() {
        return studentMap;
    }

    @JsonGetter
    public int getBanCounter() {
        return banCounter;
    }

    @JsonGetter
    public TowerColour getTowerColour() {
        return towerColour;
    }
}