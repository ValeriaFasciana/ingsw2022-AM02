package it.polimi.ingsw.server.model.board;

import it.polimi.ingsw.network.data.CloudData;
import it.polimi.ingsw.network.data.GameBoardData;
import it.polimi.ingsw.shared.enums.PawnColour;
import it.polimi.ingsw.shared.enums.TowerColour;

import java.util.*;
import java.util.stream.Collectors;

public class GameBoard {
    private ArrayList<Cloud> clouds;
    private IsleCircle isleCircle;
    private Bag bag;
    private MotherNature motherNature;

    private Random rand = new Random();


    public GameBoard(int numberOfClouds, int numberOfIsles, int studentsInClouds) {
        this.bag = new Bag();
        this.clouds = initializeClouds(numberOfClouds,studentsInClouds);
        this.isleCircle = new IsleCircle(numberOfIsles);
        int motherNatureIndex = getRandomIndex(numberOfIsles);
        this.motherNature = new MotherNature(motherNatureIndex);
        this.isleCircle.initialPopulation(this.motherNature.getPosition(),this.bag);
        this.bag.addStudentsForEachColour(28);
        addStudentsToClouds(studentsInClouds);
    }

    public IsleCircle getIsleCircle() {
        return isleCircle;
    }

    public int getMotherNaturePosition(){
        return motherNature.getPosition();
    }

    public void moveMotherNatureTo(int isleIndex){
        motherNature.setPosition(isleIndex);
        isleCircle.get(isleIndex).removeBan();
    }

//    public IsleGroup getMotherNatureOppositeIsland(){
//        IsleGroup motherNaturePosition = motherNature.getPosition();
//        return isleCircle.getOppositeOfIsle(motherNaturePosition);
//    }


    public Bag getBag() {
        return bag;
    }

    private ArrayList<Cloud> initializeClouds(int numberOfClouds, int studentsInClouds) {
        ArrayList<Cloud> newClouds = new ArrayList<>();
        boolean side = numberOfClouds ==3;
        for(int i=0;i<numberOfClouds;i++){
            Cloud cloud = new Cloud(studentsInClouds,side,i);
            newClouds.add(cloud);
        }
        return newClouds;
    }

    private int getRandomIndex(int limSup){
        return rand.nextInt(0,limSup);
    }


    public void addStudentsToClouds(int studentsInClouds) {
        if(bag.isEmpty())return;
        for(Cloud cloud : clouds){
            cloud.addStudents(bag.pick(studentsInClouds));
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
        return isleCircle.get(isleIndex).isBanned();
    }

    public Map<PawnColour, Integer> getStudentsOnIsle(int isleIndex) {
        return isleCircle.get(isleIndex).getStudentCountMap();
    }

    public TowerColour getIsleTowerColour(int isleIndex) {
        return isleCircle.get(isleIndex).getTower();
    }

    public int getIsleSize(int isleIndex) {
        return isleCircle.get(isleIndex).getSize();
    }

    public void placeTowerOnIsle(int isleIndex, TowerColour towerToPlace) {
        isleCircle.get(isleIndex).setTower((towerToPlace));
    }

    public void addStudentToIsle(int isleIndex, Map<PawnColour,Integer> studentMap) {
        isleCircle.addStudentsToIsle(isleIndex,studentMap);
    }

    public void manageIsleMerge(int isleIndex) {
        isleCircle.manageIsleMerge(isleIndex);
    }

    public void emptyCloud(int cloudIndex) {
        clouds.get(cloudIndex).empty();
    }

    public Map<PawnColour, Integer> getStudentsOnCloud(int cloudIndex) {
        return this.clouds.get(cloudIndex).getStudentCountMap();
    }

    public void setBanOnIsle(int isleIndex) {
        this.isleCircle.get(isleIndex).addBan();
    }

    public GameBoardData getData(){
        ArrayList<CloudData> cloudsData = new ArrayList<>();
        for(Cloud cloud : this.clouds){
            cloudsData.add(new CloudData(cloud.getStudentCountMap(),cloud.getSide()));
        }
        return new GameBoardData(this.isleCircle.getData(), cloudsData,motherNature.getPosition());
    }

    public List<Integer> getMotherNatureNextIslands(int playedAssistantValue) {
        int motherNaturePosition = motherNature.getPosition();
        int startIndex = motherNaturePosition + 1;
        return isleCircle.getIndexArrayFromStartIndex(startIndex, playedAssistantValue);
    }

    public Set<Integer> getAvailableClouds() {
        return clouds.stream().filter(cloud -> !(cloud.isEmpty())).map(Cloud::getIndex).collect(Collectors.toSet());
    }
}

