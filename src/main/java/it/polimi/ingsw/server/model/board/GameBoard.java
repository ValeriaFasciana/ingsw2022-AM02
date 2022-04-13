package it.polimi.ingsw.server.model.board;

import java.util.ArrayList;
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
        IsleGroup motherNatureisle = this.isleCircle.get(motherNatureIndex);
        this.motherNature = new MotherNature(motherNatureisle);
        this.isleCircle.initialPopulation(this.motherNature.getPosition(),this.bag);
    }

    public IsleCircle getIsleCircle() {
        return isleCircle;
    }

    public IsleGroup getMotherNaturePosition(){
        return motherNature.getPosition();
    }

    public void moveMotherNatureTo(int isleIndex){
        motherNature.setPosition(isleCircle.get(isleIndex));
    }

    public IsleGroup getMotherNatureOppositeIsland(){
        IsleGroup motherNaturePosition = motherNature.getPosition();
        return isleCircle.getOppositeOfIsle(motherNaturePosition);
    }





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

}
