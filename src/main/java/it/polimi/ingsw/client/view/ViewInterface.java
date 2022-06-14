package it.polimi.ingsw.client.view;

import it.polimi.ingsw.client.NetworkHandler;
import it.polimi.ingsw.network.data.BoardData;
import it.polimi.ingsw.shared.enums.MovementDestination;
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




    void displayMessage(String message);

    void askLobbyInfo();


    void askUserInfo(boolean invalidName);
    void askUserInfo();

    void askAssistant(Set<Integer> availableAssistantIds);

    void askMoveStudentFromEntrance(Map<PawnColour, Boolean> hallColourAvailability);

    void moveMotherNature(ArrayList<Integer> availableIsleIndexes);


    void askCloud(Set<Integer> availableCloudIndexes);

    void setBoard(BoardData boardData);

    void endGame(String winnerPlayer);

    void askChooseIsland(boolean setBan, boolean calculateInfluence);

    void askChooseColour(boolean toDiscard, boolean toExclude);

    void askMoveStudentsFromCard(int characterId, MovementDestination destination, int studentsToMove, boolean canMoveLess);

    void askExchangeStudents(int characterId, int numberOfStudents, MovementDestination from, MovementDestination to);

    void initBoard(BoardData boardData, boolean expertMode);
}
