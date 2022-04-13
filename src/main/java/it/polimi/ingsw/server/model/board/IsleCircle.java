package it.polimi.ingsw.server.model.board;

import it.polimi.ingsw.server.model.PawnColour;

import java.nio.channels.IllegalSelectorException;
import java.util.ArrayList;
import java.util.EnumMap;

public class IsleCircle {
    IsleGroup head = null;
    int size;

    public IsleCircle(int numberOfIslands){
        addIsles(numberOfIslands);
        size = numberOfIslands;
    }

    public void addIsles(int numberOfIslands){
        for (int i=0;i< numberOfIslands; i++) {
            addIsle();
        }
    }

    public int getSize(){
        return size;
    }


    public void addIsle() {
        // Create a new isle
        // List is empty so create a single isle first
        if (head == null) {
            IsleGroup newIsle = new IsleGroup();
            newIsle.setNext(newIsle);
            newIsle.setPrevious(newIsle);
            head = newIsle;
            return;
        }

        // find last isle in the list if list is not empty
        //previous of head is the last isle
        IsleGroup last = (head).getPrevious();

        // create a new isle
        IsleGroup newIsle = new IsleGroup();

        // next of newIsle will point to head since list is circular
        newIsle.setNext(head);

        // similarly, previous of head will be newIsle
        (head).setPrevious(newIsle);

        // change newIsle=>prev to last
        newIsle.setPrevious(last);

        // Make new isle next of old last
        last.setNext(newIsle);
    }

    public IsleGroup get(int index){
        IsleGroup isle = getIsle(index);
        return  isle;

    }

    /**
     * returns an array of the next n(nextIslands) IsleGroups starting from a given isleGroup(fromIsland)
     *
     * @param fromIsland starting IsleGroup
     * @param nextIslands length of the returned array of IsleGroups
     * @return
     */
    public ArrayList<IsleGroup> getNextIslands(IsleGroup fromIsland,int nextIslands){
        ArrayList<IsleGroup> isleArray = new ArrayList<>();
        IsleGroup tempIsle = fromIsland;
        isleArray.add(tempIsle);
        for(int i = 0; i< nextIslands; i++){
            tempIsle = tempIsle.getNext();
            isleArray.add(tempIsle);
        }
        return isleArray;
    }

    public void remove(int index){
        IsleGroup isle = getIsle(index);
        isle.getPrevious().setNext(isle.getNext());
        isle.getNext().setPrevious(isle.getPrevious());
        size--;
    }


    private IsleGroup getIsle(int index) {
        IsleGroup isle = head.getNext();
        for (int k = 0; k < index; k++) {
            isle = isle.getNext();
        }
        return isle;
    }

    public IsleGroup getOppositeOfIsle(IsleGroup isle) {
        if (size % 2 != 0) return null;
        IsleGroup oppositeIsle = isle;
        for(int i = 0; i<size/2; i++){
            oppositeIsle = oppositeIsle.getNext();
        }
        return oppositeIsle;
    }

    public void initialPopulation(IsleGroup motherNaturePosition, Bag bag){
        IsleGroup motherNatureOppositeIsle = getOppositeOfIsle(motherNaturePosition);
        for(int i = 0; i < this.size; i++){
            if(get(i) != motherNaturePosition && get(i) != motherNatureOppositeIsle) {
                addStudentsToIsle(i, bag.pick(1));
            }
        }
    }

    public void addStudentsToIsle(int index, EnumMap<PawnColour,Integer> studentMap){
        get(index).addStudents(studentMap);
    }

    public ArrayList<IsleGroup> toArray(){
        IsleGroup isle = head.getNext();
        ArrayList <IsleGroup> isleArray = new ArrayList<>();
        for(int i = 0; i < size; i++){
            isleArray.add(isle);
            isle = isle.getNext();
        }
        return isleArray;
    }

}

