package it.polimi.ingsw.server.model.board;

import it.polimi.ingsw.server.model.PawnColour;
import it.polimi.ingsw.server.model.TowerColour;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Optional;
import java.util.Random;

public class GameBoard {
    private ArrayList<Cloud> clouds;
    private IsleCircle isleCircle;
    private Bag bag;
    private MotherNature motherNature;

    public GameBoard(int numberOfClouds, int numberOfIsles, int studentsInClouds) {
        this.bag = new Bag();
        this.clouds = initializeClouds(numberOfClouds,studentsInClouds);
        this.isleCircle = new IsleCircle(numberOfIsles);
        int motherNatureIndex = getRandomIndex(numberOfIsles);
        IsleGroup motherNatureIsle = this.isleCircle.get(motherNatureIndex);
        this.motherNature = new MotherNature(motherNatureIsle);
        this.isleCircle.initialPopulation(this.motherNature.getPosition(),this.bag);
        this.bag.addStudentsForEachColour(28);
    }

    public IsleCircle getIsleCircle() {
        return isleCircle;
    }

    public IsleGroup getMotherNaturePosition(){
        return motherNature.getPosition();
    }

    public void moveMotherNatureTo(int isleIndex){
        motherNature.setPosition(isleCircle.get(isleIndex));
        isleCircle.get(isleIndex).removeBan();
    }

//    public IsleGroup getMotherNatureOppositeIsland(){
//        IsleGroup motherNaturePosition = motherNature.getPosition();
//        return isleCircle.getOppositeOfIsle(motherNaturePosition);
//    }





    private ArrayList<Cloud> initializeClouds(int numberOfClouds,int studentsInClouds) {
        ArrayList<Cloud> newClouds = new ArrayList<>();
        boolean side = numberOfClouds ==3;
        for(int i=0;i<numberOfClouds;i++){
            Cloud cloud = new Cloud(i,side,studentsInClouds);
            newClouds.add(cloud);
        }
        return newClouds;
    }

    private int getRandomIndex(int limSup){
        return new Random().nextInt(0,limSup);
    }


    public void addStudentsToCloud(int studentsInClouds) {
        for(Cloud cloud : this.clouds){
            cloud.addStudents(this.bag.pick(studentsInClouds));
        }
    }


    /**
     * for every color, fill bag with 24 students at the start of the game
     *
     */
    private Bag initializeBag() {
        bag.addStudentsForEachColour(24);
        return bag;
    }

    public boolean isIsleBanned(int isleIndex) {
        return this.isleCircle.get(isleIndex).isBanned();
    }

    public EnumMap<PawnColour, Integer> getStudentsOnIsle(int isleIndex) {
        return this.isleCircle.get(isleIndex).getStudentCountMap();
    }

    public TowerColour getIsleTowerColour(int isleIndex) {
        return this.isleCircle.get(isleIndex).getTower();
    }

    public int getIsleSize(int isleIndex) {
        return this.isleCircle.get(isleIndex).getSize();
    }

    public void placeTowerOnIsle(int isleIndex, TowerColour towerToPlace) {
        this.isleCircle.get(isleIndex).setTower((towerToPlace));
    }
}

