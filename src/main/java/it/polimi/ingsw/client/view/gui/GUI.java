package it.polimi.ingsw.client.view.gui;

import it.polimi.ingsw.client.ServerHandler;
import it.polimi.ingsw.client.view.ViewInterface;

import java.util.ArrayList;
import it.polimi.ingsw.server.model.BoardData;
import it.polimi.ingsw.shared.enums.PawnColour;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import it.polimi.ingsw.server.model.BoardData;
import it.polimi.ingsw.shared.enums.PawnColour;

import java.util.Map;
import java.util.Set;

public class GUI implements ViewInterface {



    @Override
    public void waiting() {

    }

    @Override
    public void selectMotherNatureDestination() {

    }

    @Override
    public void selectCard() {

    }

    @Override
    public void selectStudentToMove() {

    }

    @Override
    public void selectStudentDestination() {

    }

    @Override
    public void displayMessage(String message) {

    }

    @Override
    public void askLobbyInfo() {

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
    public void moveMotherNature(ArrayList<Integer> availableIsleIndexes) {

    }
}
