package it.polimi.ingsw.client.view.gui;

import it.polimi.ingsw.client.NetworkHandler;
import it.polimi.ingsw.client.view.ViewInterface;
import it.polimi.ingsw.shared.enums.PawnColour;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

import java.util.ArrayList;
import it.polimi.ingsw.network.data.BoardData;
import it.polimi.ingsw.shared.enums.MovementDestination;
import it.polimi.ingsw.shared.enums.PawnColour;


import java.util.Map;
import java.util.Set;


public class GUI implements ViewInterface {



    @Override
    public void waiting() {

    }


    @Override
    public void displayMessage(String message) {

    }

    @Override
    public void askLobbyInfo() {

    }



    @Override
    public void askUserInfo(boolean invalidName) {

    }

    @Override
    public void askUserInfo() {

    }

    @Override
    public void askAssistant(Set<Integer> availableAssistantIds) {

    }

    @Override
    public void askMoveStudentFromEntrance(Map<PawnColour, Boolean> hallColourAvailability) {


    }



    @Override
    public void moveMotherNature(ArrayList<Integer> availableIsleIndexes) {

    }

    @Override
    public void askCloud(Set<Integer> availableCloudIndexes) {

    }

    @Override
    public void setBoard(BoardData boardData) {

    }

    @Override
    public void endGame(String winnerPlayer) {

    }

    @Override
    public void askChooseIsland(boolean setBan, boolean calculateInfluence) {

    }

    @Override
    public void askChooseColour(int toDiscard, boolean toExclude) {

    }

    @Override
    public void askMoveStudentsFromCard(int characterId, MovementDestination destination, int studentsToMove, boolean canMoveLess) {

    }

    @Override
    public void askExchangeStudents(int characterId, int numberOfStudents, MovementDestination from, MovementDestination to) {

    }

    @Override
    public void initBoard(BoardData boardData, boolean expertMode) {

    }

}
