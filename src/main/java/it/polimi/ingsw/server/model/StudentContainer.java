package it.polimi.ingsw.server.model;

import java.util.*;

public abstract class StudentContainer {
    private EnumMap<PawnColour,Integer> studentCountMap;
    private Integer capacity;




    public StudentContainer(Integer capacity) {
        studentCountMap = new EnumMap<PawnColour, Integer>(PawnColour.class);
        for(PawnColour colour : PawnColour.values()){
            this.studentCountMap.put(colour,0);
        }
        this.capacity = capacity;
    }

    public EnumMap<PawnColour, Integer> getStudentCountMap() {
        return studentCountMap;
    }

    public Integer getCount(PawnColour colour){
        return this.studentCountMap.get(colour);
    }

    public void addStudentsForEachColour(Integer number){
        EnumMap<PawnColour,Integer> toAddMap = new EnumMap<PawnColour, Integer>(PawnColour.class);
        for(PawnColour colour : studentCountMap.keySet()){
            toAddMap.put(colour,number);
        }
        addStudents(toAddMap);
    }

    public void addStudents(EnumMap<PawnColour,Integer> studentMap){
        for(PawnColour colour : studentMap.keySet()){
            int summedStudents = this.studentCountMap.get(colour) + studentMap.get(colour);
            this.studentCountMap.put(colour,summedStudents);
        }
    }


    public void removeStudents(PawnColour colour,Integer number) {
        if (number > this.studentCountMap.get(colour)) this.studentCountMap.put(colour, 0);
        else this.studentCountMap.put(colour, this.studentCountMap.get(colour) - number);
    }

    public boolean isEmpty(){
        return this.studentCountMap.isEmpty();
    }

    public EnumSet<PawnColour> getAvailableColours(){
        EnumSet<PawnColour> availableColours = EnumSet.allOf(PawnColour.class);
        for(PawnColour colour : PawnColour.values()){
            if(this.studentCountMap.get(colour)==0 || !this.studentCountMap.containsKey(colour))availableColours.remove(colour);
        }
        return availableColours;
    }
}
