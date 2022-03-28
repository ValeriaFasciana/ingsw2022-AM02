package it.polimi.ingsw.server.model;

import java.util.EnumMap;
import java.util.Map;

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
        for(PawnColour colour : studentCountMap.keySet()){
            addStudents(colour,number);
        }
    }

    public void addStudents(PawnColour colour, Integer number){
        this.studentCountMap.put(colour, this.studentCountMap.get(colour) +number);
    }


    public void removeStudents(PawnColour colour,Integer number) {
        if (number > this.studentCountMap.get(colour)) this.studentCountMap.put(colour, 0);
        else this.studentCountMap.put(colour, this.studentCountMap.get(colour) - number);
    }

    public boolean isEmpty(){
        return this.studentCountMap.isEmpty();
    }


}
