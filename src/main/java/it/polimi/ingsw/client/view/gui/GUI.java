package it.polimi.ingsw.client.view.gui;

import it.polimi.ingsw.client.ServerHandler;
import it.polimi.ingsw.client.view.ViewInterface;
import it.polimi.ingsw.server.model.BoardData;
import it.polimi.ingsw.shared.enums.PawnColour;
import java.util.ArrayList;

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
    public void askAssistantCard(Set<Integer> availableAssistantIds) {

    }

    @Override
    public void setServerHandler(ServerHandler serverHandler) {

    }

    @Override
    public void askUserInfo() {

    }

    @Override

    public void printBoard(BoardData boardData) {

    }

    @Override
    public void askAssistant(Set<Integer> availableAssistantIds) {

    }

    @Override
    public void askMoveStudentFromEntrance(Map<PawnColour, Boolean> hallColourAvailability) {


    }

    @Override
    public void moveStudent(Map<PawnColour, Boolean> hallColourAvailability) {

    }

    @Override
    public void moveMotherNature(ArrayList<Integer> availableIsleIndexes) {

    }

    @Override
    public void askCloud(ArrayList<Integer> availableCloudIndexes) {

    }
}
