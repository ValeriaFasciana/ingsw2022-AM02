package it.polimi.ingsw.client.view;

import it.polimi.ingsw.client.ServerHandler;
import it.polimi.ingsw.network.data.BoardData;
import it.polimi.ingsw.shared.enums.PawnColour;

import java.util.Map;
import java.util.Set;

import java.util.ArrayList;


public interface ViewInterface {

    // *********************************************************************  //
    //                               Login                                 //
    // *********************************************************************  //

    void waiting();


    // *********************************************************************  //
    //                               ACTIONS                                  //
    // *********************************************************************  //




//    void selectStudentToMove();
//
//    void selectStudentDestination();

    void displayMessage(String message);

    void askLobbyInfo();

    void askAssistantCard(Set<Integer> availableAssistantIds);

    void setServerHandler(ServerHandler serverHandler);


    void askUserInfo(boolean invalidName);
    void askUserInfo();


    void askAssistant(Set<Integer> availableAssistantIds);

    void askMoveStudentFromEntrance(Map<PawnColour, Boolean> hallColourAvailability);

    void printBoard(BoardData boardData);

    void moveStudent(Map<PawnColour, Boolean> hallColourAvailability);

    void moveMotherNature(ArrayList<Integer> availableIsleIndexes);


    void askCloud(Set<Integer> availableCloudIndexes);

    void setBoard(BoardData boardData);
}
