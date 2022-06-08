package it.polimi.ingsw.server.model.board;

import it.polimi.ingsw.network.data.IsleCircleData;
import it.polimi.ingsw.network.data.IsleData;
import it.polimi.ingsw.shared.enums.PawnColour;
import it.polimi.ingsw.shared.enums.TowerColour;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class IsleCircle {
    IsleGroup head = null;
    int size;



    public IsleCircle(int numberOfIslands){
        addIsles(numberOfIslands);
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
            size =1;
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
        size++;
    }

    public IsleGroup get(int index){
        return getIsle(index);
    }

    /**
     * returns an array of the next n(nextIslands) IsleGroups starting from a given isleGroup(fromIsland)
     *
     * @param fromIsland starting IsleGroup
     * @param nextIslands length of the returned array of IsleGroups
     * @return
     */
    public List<IsleGroup> getNextIslands(IsleGroup fromIsland,int nextIslands){
        ArrayList<IsleGroup> isleArray = new ArrayList<>();
        IsleGroup tempIsle = fromIsland;
        isleArray.add(tempIsle);
        for(int i = 0; i< nextIslands; i++){
            tempIsle = tempIsle.getNext();
            isleArray.add(tempIsle);
        }
        return isleArray;
    }


    public void removeIsle(IsleGroup isle){

        // Base case
        if (head == null || isle == null) {
            return;
        }

        // If node to be deleted is head node
        if (head == isle) {
            head = isle.getNext();
        }

        // Change next only if node to be deleted
        // is NOT the last node
        if (isle.getNext() != null) {
            isle.getNext().setPrevious(isle.getPrevious());
        }

        // Change prev only if node to be deleted
        // is NOT the first node
        if (isle.getPrevious() != null) {
            isle.getPrevious().setNext(isle.getNext());
        }
        size--;

    }


    private IsleGroup getIsle(int index) {
        IsleGroup isle = head;
        for (int k = 0; k < index; k++) {
            isle = isle.getNext();
        }
        return isle;
    }

    public IsleGroup getOppositeOfIsle(int isleIndex) {
        if (size % 2 != 0) return null;
        IsleGroup oppositeIsle = get(isleIndex);
        for(int i = 0; i<size/2; i++){
            oppositeIsle = oppositeIsle.getNext();
        }
        return oppositeIsle;
    }

    public void initialPopulation(int motherNaturePosition, Bag bag){
        IsleGroup motherNatureOppositeIsle = getOppositeOfIsle(motherNaturePosition);
        for(int i = 0; i < this.size; i++){
            if(i != motherNaturePosition && get(i) != motherNatureOppositeIsle) {
                addStudentsToIsle(i, bag.pick(1));
            }
        }
    }

    public void addStudentsToIsle(int index, Map<PawnColour,Integer> studentMap){
        get(index).addStudents(studentMap);
    }

    public List<IsleGroup> toList(){
        IsleGroup isle = head.getNext();
        ArrayList <IsleGroup> isleArray = new ArrayList<>();
        for(int i = 0; i < size; i++){
            isleArray.add(isle);
            isle = isle.getNext();
        }
        return isleArray;
    }

    public void manageIsleMerge(int isleIndex,MotherNature motherNature) {
        TowerColour isleTowerColour = get(isleIndex).getTower();
        if (isleTowerColour == null)return;
        TowerColour nextIsleTowerColour = get(isleIndex).getNext().getTower();
        if(isleTowerColour.equals(nextIsleTowerColour)){
            mergeNext(get(isleIndex));
            if(motherNature.getPosition() > isleIndex){
                motherNature.setPosition(getPreviousIndexOf(motherNature.getPosition()));
            }
        }
        TowerColour previousIsleTowerColour = get(isleIndex).getPrevious().getTower();
        if(isleTowerColour.equals((previousIsleTowerColour))){
            mergePrevious(get(isleIndex));
            if(motherNature.getPosition() >= isleIndex){
                motherNature.setPosition(getPreviousIndexOf(motherNature.getPosition()));
            }
        }
    }

    public int getNextIndexOf(int isleIndex) {
        return isleIndex == size - 1 ? 0 : isleIndex + 1;
    }

    public int getPreviousIndexOf(int isleIndex) {
        return isleIndex == 0 ? size - 1 : isleIndex - 1;
    }

    private void mergeNext(IsleGroup isle) {
        isle.addStudents(isle.getNext().getStudentCountMap());
        isle.increaseSize(isle.getNext().getSize());
        removeIsle(isle.getNext());
    }

    private void mergePrevious(IsleGroup isle) {
        isle.addStudents(isle.getPrevious().getStudentCountMap());
        isle.increaseSize(isle.getPrevious().getSize());
        removeIsle(isle.getPrevious());
    }

    public void printList() {
        IsleGroup temp =this.head;

        // If linked list is not empty
        if (temp != null)
        {

            // Keep printing nodes till we reach the first node
            // again
            int i = 0;
            do
            {
                System.out.print("Isle "+i+"\n SIZE: " +temp.getSize() + "\n TOWER COLOUR : "+  temp.getTower()+ "\n\n");
                System.out.print("Students: \n"+temp.getStudentCountMap()+"\n\n");

                temp = temp.getNext();
                i++;
            } while (temp != head);
        }
    }

    public IsleCircleData getData(){
        IsleGroup isle = head;
        List<IsleData> data = new ArrayList<>();
        if(isle == null)return null;
        do
        {
            data.add(isle.getData());
            isle = isle.getNext();

        } while (isle != head);

        return new IsleCircleData(data);
    }
    public ArrayList<Integer> getIndexArrayFromStartIndex(int startIndex, int nextIndexes){
        ArrayList<Integer> indexArray = new ArrayList<>();
        for(int i = startIndex; i < startIndex + nextIndexes; i++){
            indexArray.add(i < size ? i : i - size);
        }
        return indexArray;
    }
}

