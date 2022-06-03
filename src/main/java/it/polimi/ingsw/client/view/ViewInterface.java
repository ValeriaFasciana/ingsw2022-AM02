package it.polimi.ingsw.client.view;

import it.polimi.ingsw.client.ServerHandler;
import it.polimi.ingsw.server.model.BoardData;

public interface ViewInterface {

    // *********************************************************************  //
    //                               Login                                 //
    // *********************************************************************  //

    void waiting();


    // *********************************************************************  //
    //                               ACTIONS                                  //
    // *********************************************************************  //
    void selectMotherNatureDestination();

    void selectCard();

    void selectStudentToMove();

    void selectStudentDestination();

    void displayMessage(String message);

    void askLobbyInfo();

    void setServerHandler(ServerHandler serverHandler);

    void printBoard(BoardData boardData);

    void askUserInfo();
}
