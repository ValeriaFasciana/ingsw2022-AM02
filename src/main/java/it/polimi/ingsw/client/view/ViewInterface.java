package it.polimi.ingsw.client.view;

import it.polimi.ingsw.network.data.BoardData;
import it.polimi.ingsw.shared.enums.MovementDestination;
import it.polimi.ingsw.shared.enums.PawnColour;

import java.util.Map;
import java.util.Set;

import java.util.ArrayList;


public interface ViewInterface {

    /**
     * Method to display the waiting scene
     */
    void waiting();

    /**
     * Method to display a message
     * @param message the message to display
     */
    void displayMessage(String message);

    /**
     * Method to request the user info
     * @param isRejoin if it's true, the player can rejoin after disconnecting
     */
    void askUserInfo(boolean isRejoin);

    /**
     * Method to handle the choice of the assistant card
     * @param availableAssistantIds available indexes of the assistant cards
     */
    void askAssistant(Set<Integer> availableAssistantIds);

    /**
     * Method to handle the choice of the student to move from the entrance
     * @param hallColourAvailability map of the colour of the students and their amount
     */
    void askMoveStudentFromEntrance(Map<PawnColour, Boolean> hallColourAvailability);

    /**
     * Method to handle the selection of the island where to put mother nature on
     * @param availableIsleIndexes available indexes the player can choose from
     */
    void moveMotherNature(Set<Integer> availableIsleIndexes);

    /**
     * Method to handle the selection of the cloud
     * @param availableCloudIndexes available indexes the player can choose from
     */
    void askCloud(Set<Integer> availableCloudIndexes);

    /**
     * Method that updates the board
     * @param boardData the board data
     */
    void setBoard(BoardData boardData);

    void endGame(String causingPlayer,String cause);

    /**
     * Method to handle the client's island choice. If setBan is true, it
     * put the ban on. If calculateInfluence is true, it calculates the influence
     * @param setBan the island ban
     * @param calculateInfluence the island influence
     */
    void askChooseIsland(boolean setBan, boolean calculateInfluence);

    /**
     * Method to handle the colour choice. If discard > 0 it discard the colour
     * choice. If toExclude is true it exclude the colour choice when calculating influence
     * @param toDiscard value of the colour to discard
     * @param toExclude if it's true it discard the colour
     */
    void askChooseColour(int toDiscard, boolean toExclude);

    /**
     * Method to handle the choice of the student on the Character Card
     * @param characterId character's cards id
     * @param destination destination where to move the student to
     * @param studentsToMove value of the colour of the student to move
     * @param canMoveLess if it's true the player can decide to move less students, instead than all of them
     */
    void askMoveStudentsFromCard(int characterId, MovementDestination destination, int studentsToMove, boolean canMoveLess);

    /**
     * Method to handle the exchange of students
     * @param characterId character's cards id
     * @param numberOfStudents max number that the player can exchange
     * @param from starting location of the students to exchange
     * @param to destination of the students
     */
    void askExchangeStudents(int characterId, int numberOfStudents, MovementDestination from, MovementDestination to);

    /**
     * Method to initialize the board
     * @param boardData board data
     * @param expertMode if it's true the game mode is expert
     */
    void initBoard(BoardData boardData, boolean expertMode);

    /**
     * Method to handle the request for lobby info
     * @param username the player's chosen username
     * @param canJoinLobby if it's true, the player can join a lobby
     * @param canRejoinLobby if it's true, the player can rejoin a lobby after disconnecting
     */
    void askLoginInfo(String username, boolean canJoinLobby, boolean canRejoinLobby);

    /**
     * Method to notify the disconnection of a player
     * @param disconnectedPlayerName username of the disconnected player
     */
    void notifyDisconnection(String disconnectedPlayerName);

    /**
     * Method to notify that a player has joined
     * @param joiningPlayer username of the player that has joined the lobby
     */
    void notifyPlayerHasJoined(String joiningPlayer);
}
