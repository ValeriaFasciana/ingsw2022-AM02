package it.polimi.ingsw.server.model.cards.characters;

public class RulesAffector extends RuleSetDecorator{

    private boolean excludeTowersFromInfluence;
    private int addedInfluencePoints;
    private int motherNatureAdditionalMovements;
    private boolean affectProfessorAssignment;

    /**
     * Default constructor
     * @param ruleSet rules of the game
     * @param excludeTowers if true, the towers are excluded from influence count
     * @param addedInfluencePoints added influence points
     * @param motherNatureAdditionalMovements mother nature additional movements
     * @param affectProfessorAssignment if true, assignation of professor is affected
     */
    public RulesAffector(RuleSet ruleSet, boolean excludeTowers, int addedInfluencePoints,int motherNatureAdditionalMovements,boolean affectProfessorAssignment) {
        this.ruleSet = ruleSet;
        this.excludeTowersFromInfluence = excludeTowers;
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
        return this.excludeTowersFromInfluence;
    }


}
