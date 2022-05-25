package it.polimi.ingsw.server.model.game;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GameSettings {

    private int numberOfPlayers;
    private int numberOfIslands;
    private int numberOfClouds;
    private int numberOfTowersForPlayer;
    private int studentsInEntrance;
    private int studentsInClouds;
    private int numberOfStudentsToMove;


    public GameSettings(@JsonProperty("number_of_players") int numberOfPlayers,
                        @JsonProperty("number_of_islands") int numberOfIslands,
                        @JsonProperty("number_of_clouds") int numberOfClouds,
                        @JsonProperty("number_of_towers_for_player") int numberOfTowersForPlayer,
                        @JsonProperty("students_in_entrance")int studentsInEntrance,
                        @JsonProperty("students_in_clouds") int studentsInClouds,
                        @JsonProperty("number_of_students_to_move") int numberOfStudentsToMove) {
        this.numberOfPlayers = numberOfPlayers;
        this.numberOfIslands = numberOfIslands;
        this.numberOfClouds = numberOfClouds;
        this.numberOfTowersForPlayer = numberOfTowersForPlayer;
        this.studentsInEntrance = studentsInEntrance;
        this.studentsInClouds = studentsInClouds;
        this.numberOfStudentsToMove = numberOfStudentsToMove;
    }

    public int getNumberOfPlayers() {
        return numberOfPlayers;
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
