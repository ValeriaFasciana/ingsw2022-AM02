package it.polimi.ingsw.server.model;

import it.polimi.ingsw.shared.enums.PawnColour;

import java.util.*;
import java.util.stream.Collectors;

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

    public Integer getStudentsByColour(PawnColour colour){
        return this.studentCountMap.get(colour);
    }


    public void addStudentsForEachColour(Integer number){
        EnumMap<PawnColour,Integer> toAddMap = new EnumMap<PawnColour, Integer>(PawnColour.class);
        for(PawnColour colour : studentCountMap.keySet()){
            toAddMap.put(colour,number);
        }
        addStudents(toAddMap);
    }

    public void addStudents(Map<PawnColour,Integer> studentMap){
        for(PawnColour colour : studentMap.keySet()){
            int summedStudents = this.studentCountMap.get(colour) + studentMap.get(colour);
            this.studentCountMap.put(colour,summedStudents);
        }
    }


    public void removeStudents(Map<PawnColour,Integer> studentMap) {
        for(PawnColour colour : studentMap.keySet()){
            if(studentMap.get(colour) > this.studentCountMap.get(colour))this.studentCountMap.put(colour,0);
            else this.studentCountMap.put(colour, this.studentCountMap.get(colour) - studentMap.get(colour));
        }
    }

    public boolean isEmpty(){
        boolean isEmpty =true;
        for(PawnColour colour : PawnColour.values()){
            isEmpty = isEmpty && studentCountMap.get(colour).equals(0);
        }
        return isEmpty;
    }

    public void empty(){
        this.removeStudents(getStudentCountMap());
    }

    public EnumMap<PawnColour,Integer> getAvailableColours(){
        EnumMap<PawnColour,Integer> availableColourMap = new EnumMap<>(PawnColour.class);
        for(Map.Entry<PawnColour, Integer> colour : studentCountMap.entrySet()){
             if(studentCountMap.get(colour.getKey()) > 0){
                 availableColourMap.put(colour.getKey(),colour.getValue());
             }
         }
        return availableColourMap;
    }
    public int getNumberOfStudents() {
        int sum = 0;
        for (PawnColour colour : PawnColour.values()) {
            sum = sum + this.studentCountMap.get(colour);
        }
        return sum;
    }

}
