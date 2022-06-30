package it.polimi.ingsw.server.model.player.playerBoard;

import it.polimi.ingsw.shared.enums.PawnColour;

import java.util.Map;

/**
 * Class for the playerboar
 */

public class PlayerBoard {
    private Entrance entrance;
    private Hall hall;


    public PlayerBoard(int studentsInEntrance) {
        this.entrance = new Entrance(studentsInEntrance);
        this.hall = new Hall();
    }

    /**
     *
     * @param colour
     */
    public void addStudentToHall(PawnColour colour){
        this.hall.addStudent(colour);
    }

    public Map<PawnColour, Integer> getStudentsInHall(){
        return hall.getStudentCountMap();
    }
    public Map<PawnColour, Integer> getStudentsInEntrance(){
        return entrance.getStudentCountMap();
    }

    public int getStudentsInTable(PawnColour colour){
        return this.hall.getStudentsByColour(colour);
    }

    public Hall getHall() {return hall;}

    /**
     *
     * @param studentMap
     */
    public void removeStudentsFromEntrance(Map<PawnColour, Integer> studentMap) {
        this.entrance.removeStudents(studentMap);
    }

    /**
     *
     * @param studentMap
     */
    public void addStudentsToEntrance(Map<PawnColour, Integer> studentMap) {
        this.entrance.addStudents(studentMap);
    }
}
