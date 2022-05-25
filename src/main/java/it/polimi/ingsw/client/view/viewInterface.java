package it.polimi.ingsw.client.view;

public interface viewInterface {

    // *********************************************************************  //
    //                               Login                                 //
    // *********************************************************************  //
    void askConnectionParameters();
    void nicknameRequest();
    void gameModeRequest();
    void numberOfPlayersRequest();
    void waiting();


    // *********************************************************************  //
    //                               ACTIONS                                  //
    // *********************************************************************  //
    void selectMotherNatureDestination();

    void selectCard();

    void selectStudentToMove();

    void selectStudentDestination();

    void displayMessage(String message);

}
