package it.polimi.ingsw.server.model.board;

import it.polimi.ingsw.server.model.board.CloudData;
import it.polimi.ingsw.server.model.board.IsleCircle;

import java.util.ArrayList;

public class GameBoardData{
    IsleCircle.IsleCircleData isleCircle;
    ArrayList<CloudData> clouds;
    int motherNaturePosition;

    public GameBoardData(IsleCircle.IsleCircleData isleCircle,  ArrayList<CloudData> clouds, int motherNaturePosition) {
        this.isleCircle = isleCircle;
        this.clouds = clouds;
        this.motherNaturePosition = motherNaturePosition;
    }
}
