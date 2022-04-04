package it.polimi.ingsw.server.model.board;

import it.polimi.ingsw.server.model.Bag;

import java.util.ArrayList;
import java.util.Random;

public class GameBoard {
    private ArrayList<Cloud> clouds;
    private ArrayList<IsleGroup> isles;
    private Bag bag;
    private MotherNature motherNature;

    public GameBoard(int numberOfClouds, int numberOfIsles, int studentsInClouds) {
        this.bag = new Bag();
        this.clouds = initializeClouds(numberOfClouds,studentsInClouds);
        this.isles = initializeIslands(numberOfIsles);
        this.motherNature = new MotherNature(this.isles.get(getRandomIndex(numberOfIsles)));
    }

    public IsleGroup getMotherNaturePosition(){
        return motherNature.getPosition();
    }

    public void moveMotherNatureTo(int isleIndex){
        motherNature.setPosition(isles.get(isleIndex));
    }

    public Integer getMotherNatureOppositeIsland(){
        Integer motherNaturePosition = motherNature.getPosition().getIndex();
        return getOppositeOf(motherNaturePosition);
    }

    public Integer getOppositeOf(Integer index){
        if(isles.size()%2!=0)return null;
        return addToIndex(index,isles.size()/2);
    }

    public Integer addToIndex(Integer index1,Integer index2){
        Integer numberOfIsles = isles.size();
        Integer indexSum = index1 + index2;
        while (indexSum > numberOfIsles){
            indexSum = indexSum - numberOfIsles;
        }
        return indexSum;
    }

    private ArrayList<IsleGroup> initializeIslands(int numberOfIslands){
        ArrayList<IsleGroup> newIslands = new ArrayList<IsleGroup>();
        for(int i=0;i<numberOfIslands;i++){
            IsleGroup isle = new IsleGroup(i);
            isle.addStudents(this.bag.pick(1));
            newIslands.add(isle);
        }
        return newIslands;
    }

    private ArrayList<Cloud> initializeClouds(int numberOfClouds,int studentsInClouds) {
        ArrayList<Cloud> newClouds = new ArrayList<Cloud>();
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







}
