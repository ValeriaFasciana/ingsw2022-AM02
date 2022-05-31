package it.polimi.ingsw.client.view;

import it.polimi.ingsw.client.ServerHandler;

public interface ViewInterface {

    // *********************************************************************  //
    //                               Login                                 //
    // *********************************************************************  //
    void askConnectionParameters();
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
}
