package it.polimi.ingsw.server.model;

import it.polimi.ingsw.shared.enums.PawnColour;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class StudentContainer {
    private EnumMap<PawnColour,Integer> studentCountMap;
    private Integer capacity;

    /**
     * Default constructor
     * @param capacity capacity of container
     */
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

    /**
     * Method to add amount of students for each colour
     * @param number
     */
    public void addStudentsForEachColour(Integer number){
        EnumMap<PawnColour,Integer> toAddMap = new EnumMap<PawnColour, Integer>(PawnColour.class);
        for(PawnColour colour : studentCountMap.keySet()){
            toAddMap.put(colour,number);
        }
        addStudents(toAddMap);
    }

    /**
     * Method to add a map of students
     * @param studentMap student map
     */
    public void addStudents(Map<PawnColour,Integer> studentMap){
        if(getNumberOfStudents(studentMap) + getNumberOfStudents() > capacity){
            return;
        }
        studentMap.forEach((key, value) -> studentCountMap.put(key, studentCountMap.getOrDefault(key, 0) + value));

    }

    /**
     * Method to remove a map of students
     * @param studentMap student map
     */
    public void removeStudents(Map<PawnColour,Integer> studentMap) {
        for(PawnColour colour : studentMap.keySet()){
            if(studentMap.get(colour) > this.studentCountMap.get(colour))this.studentCountMap.put(colour,0);
            else this.studentCountMap.put(colour, this.studentCountMap.get(colour) - studentMap.get(colour));
        }
    }

    /**
     * Method to check if student map is empty
     * @return
     */
    public boolean isEmpty(){
        boolean isEmpty =true;
        for(PawnColour colour : PawnColour.values()){
            isEmpty = isEmpty && studentCountMap.get(colour).equals(0);
        }
        return isEmpty;
    }

    /**
     * Method to remove students from map
     */
    public void empty(){
        this.removeStudents(getStudentCountMap());
    }

    /**
     * Method to get available colours in a map
     * @return map of available colours
     */
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
        return getNumberOfStudents(studentCountMap);
    }


    private int getNumberOfStudents(Map<PawnColour,Integer> studentMap) {
        AtomicInteger sum = new AtomicInteger();
        studentMap.forEach((key, value) -> sum.addAndGet(value));
        return sum.get();
    }

    /**
     * Method to check if student container is full
     * @return true if student container is full
     */
    public boolean isFull(){
        return getNumberOfStudents() == capacity;
    }
}
