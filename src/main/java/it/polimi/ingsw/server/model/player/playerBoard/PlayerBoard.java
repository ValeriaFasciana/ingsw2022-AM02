package it.polimi.ingsw.server.model.player.playerBoard;

import it.polimi.ingsw.server.model.Game;
import it.polimi.ingsw.server.model.PawnColour;

import java.util.EnumMap;

public class PlayerBoard {
    private Entrance entrance;
    private Hall hall;


    public PlayerBoard(int studentsInEntrance) {
        this.entrance = new Entrance(studentsInEntrance);
        this.hall = new Hall();
    }

    public void addStudentsToHall(EnumMap<PawnColour,Integer> studentMap){
        this.hall.addStudents(studentMap);
    }

    public EnumMap<PawnColour, Integer> getStudentsInHall(){
        return hall.getStudentCountMap();
    }

    public int getStudentsInTable(PawnColour colour){
        return this.hall.getStudentsByColour(colour);
    }

}
