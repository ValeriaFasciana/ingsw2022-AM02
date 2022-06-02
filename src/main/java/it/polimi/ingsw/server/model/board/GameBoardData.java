package it.polimi.ingsw.server.model.board;

import com.fasterxml.jackson.annotation.*;
import it.polimi.ingsw.server.model.BoardData;
import it.polimi.ingsw.server.model.board.CloudData;
import it.polimi.ingsw.server.model.board.IsleCircle;

import java.util.ArrayList;
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id", scope = GameBoardData.class)
@JsonPropertyOrder({"isleCircle", "clouds", "motherNaturePosition"})
public class GameBoardData{
    IsleCircleData isleCircle;
    ArrayList<CloudData> clouds;
    int motherNaturePosition;

    @JsonCreator
    public GameBoardData(@JsonProperty("isleCircle")IsleCircleData isleCircle,
                         @JsonProperty("clouds")ArrayList<CloudData> clouds,
                         @JsonProperty("motherNaturePosition")int motherNaturePosition) {
        this.isleCircle = isleCircle;
        this.clouds = clouds;
        this.motherNaturePosition = motherNaturePosition;
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
}
