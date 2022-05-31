package it.polimi.ingsw.shared;

public interface ViewInterface {

    /**
     * Method to display the CLI view
     */
    void displayStandardView();

    // *********************************************************************  //
    //                               Login                                 //
    // *********************************************************************  //

    void setLobbyInfo();

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
