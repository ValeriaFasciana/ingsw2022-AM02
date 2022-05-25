package it.polimi.ingsw.server.model.cards.characters;

import it.polimi.ingsw.server.model.*;
import it.polimi.ingsw.server.model.action.Action;
import it.polimi.ingsw.server.model.board.GameBoard;
import it.polimi.ingsw.server.model.board.IsleGroup;
import it.polimi.ingsw.server.model.player.Player;

import java.util.*;

public interface RuleSet {

    /**
     * contains the condition for assigning a professor
     * this method will be overridden by characters effects
     * @param studentCount number of students of a given colour contained in the player entrance
     * @param professorCount number of students associated to the professor of the given colour
     */

    boolean isToAssignProfessor(Integer studentCount, Integer professorCount);

    int getAdditionalMotherNatureMoves();

    Integer getAdditionalInfluence();

    boolean excludeTowers();


//    public boolean isToAssignProfessor(Integer studentCount, Integer professorCount){}
//
//    int getAdditionalMotherNatureMoves(){}
//
//
//    Integer getAdditionalInfluence(){}
//
//
//    Set<PawnColour> getInfluentialColours(IsleGroup isle);
//
//    boolean excludeTowers();
//
//    void assignProfessorsToPlayer(Player player, EnumMap<PawnColour,Professor> professorMap);

//    void getAvailableCards(String playerName);
//    void planningPhase();
//    void calculateActionOrder();
//
//    void assignProfessor();
//
//    void getMotherNatureAvailableMoves();
//
//    void moveMotherNature();
//    void mergeIsle();
//    void calculateInfluence();
//    void checkWinningConditions();
//    void declareWinner();
//    void checkAvailableClouds();
//    void checkStudentsEntrance();
//    void playerHasNoTowers();
//
//    void playerHasNoCards();
//    void lessThanThreeIsleGroups();
//    void bagIsEmpty();
//    void generateCharacter();

}
