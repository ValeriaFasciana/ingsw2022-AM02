package it.polimi.ingsw.server.model.cards.characters;

public class RulesAffector extends RuleSetDecorator{

    private boolean excludeTowersFromInfluence;
    private int addedInfluencePoints;
    private int motherNatureAdditionalMovements;
    private boolean affectProfessorAssignment;

    /**
     *
     * @param ruleSet
     * @param excludeTowers
     * @param addedInfluencePoints
     * @param motherNatureAdditionalMovements
     * @param affectProfessorAssignment
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
