package it.polimi.ingsw.server.model;

public class GameSettings {
    private int numberOfIslands;
    private int numberOfClouds;
    private int numberOfTowersForPlayer;
    private int studentsInEntrance;
    private int studentsInClouds;
    private int numberOfStudentsToMove;

    public GameSettings(int numberOfIslands,int numberOfClouds, int numberOfTowersForPlayer, int studentsInEntrance, int studentsInClouds, int numberOfStudentsToMove) {
        this.numberOfIslands = numberOfIslands;
        this.numberOfClouds = numberOfClouds;
        this.numberOfTowersForPlayer = numberOfTowersForPlayer;
        this.studentsInEntrance = studentsInEntrance;
        this.studentsInClouds = studentsInClouds;
        this.numberOfStudentsToMove = numberOfStudentsToMove;
    }

    public int getNumberOfIslands() {
        return numberOfIslands;
    }

    public int getNumberOfClouds() {
        return numberOfClouds;
    }

    public int getNumberOfTowersForPlayer() {
        return numberOfTowersForPlayer;
    }

    public int getStudentsInEntrance() {
        return studentsInEntrance;
    }

    public int getStudentsInClouds() {
        return studentsInClouds;
    }

    public int getNumberOfStudentsToMove() {
        return numberOfStudentsToMove;
    }
}
