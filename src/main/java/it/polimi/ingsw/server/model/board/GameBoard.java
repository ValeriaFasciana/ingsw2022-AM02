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

    /**
     * Default constructor
     * @param numberOfClouds number of clouds
     * @param numberOfIsles number of isles
     * @param studentsInClouds number of students per cloud
     */
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

    /**
     * Method to handle mother nature move
     * @param isleIndex chosen isle id to move mother nature to
     */
    public void moveMotherNatureTo(int isleIndex){
        motherNature.setPosition(isleIndex);
        isleCircle.get(isleIndex).removeBan();
    }

    public Bag getBag() {
        return bag;
    }

    /**
     * Method to initialize clouds
     * @param numberOfClouds number of clouds
     * @param studentsInClouds students per cloud
     * @return array of cloud
     */
    private ArrayList<Cloud> initializeClouds(int numberOfClouds, int studentsInClouds) {
        ArrayList<Cloud> newClouds = new ArrayList<>();
        boolean side = numberOfClouds == 3;
        for(int i=0;i<numberOfClouds;i++){
            Cloud cloud = new Cloud(studentsInClouds,side,i);
            newClouds.add(cloud);
        }
        return newClouds;
    }

    private int getRandomIndex(int limSup){
        return rand.nextInt(0,limSup);
    }

    /**
     * Method to add students to cloud
     * @param studentsToAdd amount of students to add
     */
    public void addStudentsToClouds(int studentsToAdd) {
        if(bag.isEmpty())return;
        for(Cloud cloud : clouds){
            if(cloud.isFull())continue;
            cloud.addStudents(bag.pick(studentsToAdd));
        }
    }

    public boolean isIsleBanned(int isleIndex) {
        return isleCircle.get(isleIndex).isBanned();
    }

    public TowerColour getIsleTowerColour(int isleIndex) {
        return isleCircle.get(isleIndex).getTower();
    }

    public int getIsleSize(int isleIndex) {
        return isleCircle.get(isleIndex).getSize();
    }

    /**
     * Method to place a tower on isle
     * @param isleIndex id of isle to place the tower on
     * @param towerToPlace colour of tower to place
     */
    public void placeTowerOnIsle(int isleIndex, TowerColour towerToPlace) {
        isleCircle.get(isleIndex).setTower((towerToPlace));
    }

    /**
     * Method to add student to isle
     * @param isleIndex id of isle to add students to
     * @param studentMap map of students to add
     */
    public void addStudentToIsle(int isleIndex, Map<PawnColour,Integer> studentMap) {
        isleCircle.addStudentsToIsle(isleIndex,studentMap);
    }

    /**
     * Method to handle merging of isles
     * @param isleIndex isle to merge
     */
    public void manageIsleMerge(int isleIndex) {
        isleCircle.manageIsleMerge(isleIndex,motherNature);
    }

    /**
     * Method to empty a cloud
     * @param cloudIndex id of cloud to empty
     */
    public void emptyCloud(int cloudIndex) {
        clouds.get(cloudIndex).empty();
    }

    /**
     * Method to get students from a cloud
     * @param cloudIndex chosen cloud id
     * @return map of students chosen from a cloud
     */
    public Map<PawnColour, Integer> getStudentsOnCloud(int cloudIndex) {
        return this.clouds.get(cloudIndex).getStudentCountMap();
    }

    /**
     * Method to set ban on isle
     * @param isleIndex id of isle to set the ban on
     */
    public void setBanOnIsle(int isleIndex) {
        this.isleCircle.get(isleIndex).addBan();
    }

    /**
     * Method to get game board data
     * @return game board data
     */
    public GameBoardData getData(){
        ArrayList<CloudData> cloudsData = new ArrayList<>();
        for(Cloud cloud : this.clouds){
            cloudsData.add(new CloudData(cloud.getStudentCountMap(),cloud.getSide()));
        }
        return new GameBoardData(this.isleCircle.getData(), cloudsData,motherNature.getPosition(),bag.getNumberOfPawns());
    }

    /**
     * Method to get available isles to move mother nature to
     * @param playedAssistantValue value of played assistant card
     * @return set of available isles
     */
    public Set<Integer> getMotherNatureNextIslands(int playedAssistantValue) {
        int motherNaturePosition = motherNature.getPosition();
        int startIndex = motherNaturePosition + 1;
        return isleCircle.getIndexArrayFromStartIndex(startIndex, playedAssistantValue);
    }

    /**
     * Method to get available clouds
     * @return set of available clouds
     */
    public Set<Integer> getAvailableClouds() {
        return clouds.stream().filter(cloud -> !(cloud.isEmpty())).map(Cloud::getIndex).collect(Collectors.toSet());
    }
    public int getMotherNaturePosition(){
        return motherNature.getPosition();
    }


    public ArrayList<Cloud> getClouds() {
        return clouds;
    }
}

