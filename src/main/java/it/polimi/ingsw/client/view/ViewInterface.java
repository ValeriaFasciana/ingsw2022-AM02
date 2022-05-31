package it.polimi.ingsw.client.view;

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
}
