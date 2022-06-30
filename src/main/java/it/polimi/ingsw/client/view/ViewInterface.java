package it.polimi.ingsw.client.view;

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


    void askUserInfo(boolean isRejoin);

    void askAssistant(Set<Integer> availableAssistantIds);

    void askMoveStudentFromEntrance(Map<PawnColour, Boolean> hallColourAvailability);

    void moveMotherNature(Set<Integer> availableIsleIndexes);


    void askCloud(Set<Integer> availableCloudIndexes);

    void setBoard(BoardData boardData);

    void endGame(String causingPlayer,String cause);

    void askChooseIsland(boolean setBan, boolean calculateInfluence);

    void askChooseColour(int toDiscard, boolean toExclude);

    void askMoveStudentsFromCard(int characterId, MovementDestination destination, int studentsToMove, boolean canMoveLess);

    void askExchangeStudents(int characterId, int numberOfStudents, MovementDestination from, MovementDestination to);

    void initBoard(BoardData boardData, boolean expertMode);

    void askLoginInfo(String username, boolean canJoinLobby, boolean canRejoinLobby);

    void notifyDisconnection(String disconnectedPlayerName);

    void notifyPlayerHasJoined(String joiningPlayer);
}
