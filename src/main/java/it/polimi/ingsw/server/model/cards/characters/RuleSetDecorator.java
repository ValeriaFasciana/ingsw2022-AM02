package it.polimi.ingsw.server.model.cards.characters;

public abstract class RuleSetDecorator implements RuleSet {
    protected RuleSet ruleSet;



    public abstract boolean isToAssignProfessor(Integer studentCount, Integer professorCount);

    public abstract int getAdditionalMotherNatureMoves();

    public abstract Integer getAdditionalInfluence();

    public abstract boolean excludeTowers();

}
