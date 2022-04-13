package it.polimi.ingsw.server.model.characters;

import it.polimi.ingsw.server.model.PawnColour;
import it.polimi.ingsw.server.model.Professor;
import it.polimi.ingsw.server.model.board.IsleGroup;
import it.polimi.ingsw.server.model.player.Player;

import java.util.EnumMap;
import java.util.Set;

public class RulesAffector extends RuleSetDecorator{

    boolean excludeTowers;
    int addedInfluencePoints;
    int motherNatureAdditionalMovements;
    boolean affectProfessorAssignment;


    public RulesAffector(RuleSet ruleSet, boolean excludeTowers, int addedInfluencePoints,int motherNatureAdditionalMovements,boolean affectProfessorAssignment) {
        this.ruleSet = ruleSet;
        this.excludeTowers = excludeTowers;
        this.addedInfluencePoints = addedInfluencePoints;
        this.motherNatureAdditionalMovements = motherNatureAdditionalMovements;
        this.affectProfessorAssignment = affectProfessorAssignment;
    }


    public int getAdditionalMotherNatureMoves(){
        return (this.ruleSet.getAdditionalMotherNatureMoves() + motherNatureAdditionalMovements);
    }

    public Integer getAdditionalInfluence(){
        return (this.ruleSet.getAdditionalInfluence() + addedInfluencePoints);
    }

    public boolean isToAssignProfessor(Integer studentCount, Integer professorCount){
        if(this.affectProfessorAssignment)return (this.ruleSet.isToAssignProfessor(studentCount,professorCount) || (studentCount.equals(professorCount)));
        return this.ruleSet.isToAssignProfessor(studentCount,professorCount);
    }

    public boolean excludeTowers(){
        return this.excludeTowers;
    }


}
