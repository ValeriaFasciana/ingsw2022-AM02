package it.polimi.ingsw.network.data;

import com.fasterxml.jackson.annotation.*;

import java.util.ArrayList;
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id", scope = GameBoardData.class)
@JsonPropertyOrder({"isleCircle", "clouds", "motherNaturePosition"})
public class GameBoardData{
    private final IsleCircleData isleCircle;
    private final ArrayList<CloudData> clouds;
    private final int motherNaturePosition;
    private final int studentsInBag;

    @JsonCreator
    public GameBoardData(@JsonProperty("isleCircle")IsleCircleData isleCircle,
                         @JsonProperty("clouds")ArrayList<CloudData> clouds,
                         @JsonProperty("motherNaturePosition")int motherNaturePosition,
                         @JsonProperty("studentsInBag")int studentsInBag) {
        this.isleCircle = isleCircle;
        this.clouds = clouds;
        this.motherNaturePosition = motherNaturePosition;
        this.studentsInBag = studentsInBag;
    }

    @JsonGetter
    public IsleCircleData getIsleCircle() {
        return isleCircle;
    }

    @JsonGetter
    public ArrayList<CloudData> getClouds() {
        return clouds;
    }

    @JsonGetter
    public int getMotherNaturePosition() {
        return motherNaturePosition;
    }

    @JsonGetter
    public int getStudentsInBag() {
        return studentsInBag;
    }
}
