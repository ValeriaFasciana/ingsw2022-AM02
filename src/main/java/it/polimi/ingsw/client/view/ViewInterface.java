package it.polimi.ingsw.client.view;

import it.polimi.ingsw.client.ServerHandler;
import it.polimi.ingsw.server.model.BoardData;
import it.polimi.ingsw.shared.enums.PawnColour;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;


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



    void askAssistant(Set<Integer> availableAssistantIds);

    void askMoveStudentFromEntrance(Map<PawnColour, Boolean> hallColourAvailability);

    void printBoard(BoardData boardData);

    void askUserInfo();

    void moveStudent(Map<PawnColour, Boolean> hallColourAvailability);

    void moveMotherNature(ArrayList<Integer> availableIsleIndexes);


    void askCloud(Set<Integer> availableCloudIndexes);
}
